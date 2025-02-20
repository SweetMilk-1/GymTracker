package ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet
import ru.sweetmilk.gymtracker.databinding.AddNewTrainingPlanSetButtonBinding
import ru.sweetmilk.gymtracker.databinding.TrainingPlanSetItemBinding


class TrainingPlanSetsAdapter(
    private val viewModel: AddEditTrainingPlanExerciseViewModel,
    private val inflater: LayoutInflater
) :
    ListAdapter<TrainingPlanSet, TrainingPlanItemHolder>(TrainingPlanItemDiffUtilCallback) {

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) ADD_NEW_TYPE else COMMON_TYPE
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingPlanItemHolder {
        return when (viewType) {
            COMMON_TYPE -> {
                val binding = TrainingPlanSetItemBinding.inflate(inflater, parent, false)
                TrainingPlanSetItemHolder(binding)
            }

            ADD_NEW_TYPE -> {
                val binding = AddNewTrainingPlanSetButtonBinding.inflate(inflater, parent, false)
                AddNewTrainingPlanItemHolder(viewModel, binding)
            }

            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: TrainingPlanItemHolder, position: Int) {
        if (getItemViewType(position) == COMMON_TYPE) {
            val item = getItem(position)
            (holder as TrainingPlanSetItemHolder).bind(item, viewModel)
        }
    }

    companion object {
        private const val COMMON_TYPE = 1
        private const val ADD_NEW_TYPE = 2
    }
}

abstract class TrainingPlanItemHolder(view: View) : ViewHolder(view)

class TrainingPlanSetItemHolder(private val binding: TrainingPlanSetItemBinding) :
    TrainingPlanItemHolder(binding.root) {

    lateinit var item: TrainingPlanSet

    fun bind(trainingPlanSet: TrainingPlanSet, viewModel: AddEditTrainingPlanExerciseViewModel) {
        this.item = trainingPlanSet.copy()
        binding.viewModel = viewModel

        if (binding.itemObservable == null)
            binding.itemObservable = TrainingPlanSetObservable(viewModel)

        binding.itemObservable?.setTrainingPlanItem(trainingPlanSet)
    }
}

class AddNewTrainingPlanItemHolder(
    private val viewModel: AddEditTrainingPlanExerciseViewModel,
    binding: AddNewTrainingPlanSetButtonBinding
) :
    TrainingPlanItemHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            viewModel.addNewTrainingPlanItem()
        }
    }
}

object TrainingPlanItemDiffUtilCallback : DiffUtil.ItemCallback<TrainingPlanSet>() {
    override fun areItemsTheSame(oldItem: TrainingPlanSet, newItem: TrainingPlanSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrainingPlanSet, newItem: TrainingPlanSet): Boolean {
        return oldItem == newItem
    }
}