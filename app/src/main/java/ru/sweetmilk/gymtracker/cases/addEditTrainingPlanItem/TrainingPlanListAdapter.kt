package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem
import ru.sweetmilk.gymtracker.databinding.AddNewTrainingPlanItemBinding
import ru.sweetmilk.gymtracker.databinding.TrainingPlanItemBinding


class TrainingPlanListAdapter(
    private val viewModel: AddEditTrainingPlanItemViewModel,
    private val inflater: LayoutInflater
) :
    ListAdapter<TrainingPlanItem, TrainingPlanItemHolder>(TrainingPlanItemDiffUtilCallback) {

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) ADD_NEW_TYPE else COMMON_TYPE
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingPlanItemHolder {
        return when (viewType) {
            COMMON_TYPE -> {
                val binding = TrainingPlanItemBinding.inflate(inflater, parent, false)
                TrainingPlanItemFormHolder(binding)
            }

            ADD_NEW_TYPE -> {
                val binding = AddNewTrainingPlanItemBinding.inflate(inflater, parent, false)
                AddNewTrainingPlanItemHolder(viewModel, binding)
            }

            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: TrainingPlanItemHolder, position: Int) {
        if (getItemViewType(position) == COMMON_TYPE) {
            val item = getItem(position)
            (holder as TrainingPlanItemFormHolder).bind(item, viewModel)
        }
    }

    companion object {
        private const val COMMON_TYPE = 1
        private const val ADD_NEW_TYPE = 2
    }
}

abstract class TrainingPlanItemHolder(view: View) : ViewHolder(view)

class TrainingPlanItemFormHolder(private val binding: TrainingPlanItemBinding) :
    TrainingPlanItemHolder(binding.root) {

    lateinit var item: TrainingPlanItem

    fun bind(trainingPlanItem: TrainingPlanItem, viewModel: AddEditTrainingPlanItemViewModel) {
        this.item = trainingPlanItem.copy()
        binding.viewModel = viewModel

        if (binding.itemObservable == null)
            binding.itemObservable = TrainingPlanItemObservable(viewModel)

        binding.itemObservable?.setTrainingPlanItem(trainingPlanItem)
    }
}

class AddNewTrainingPlanItemHolder(
    private val viewModel: AddEditTrainingPlanItemViewModel,
    binding: AddNewTrainingPlanItemBinding
) :
    TrainingPlanItemHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            viewModel.addNewTrainingPlanItem()
        }
    }
}

object TrainingPlanItemDiffUtilCallback : DiffUtil.ItemCallback<TrainingPlanItem>() {
    override fun areItemsTheSame(oldItem: TrainingPlanItem, newItem: TrainingPlanItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrainingPlanItem, newItem: TrainingPlanItem): Boolean {
        return oldItem == newItem
    }
}