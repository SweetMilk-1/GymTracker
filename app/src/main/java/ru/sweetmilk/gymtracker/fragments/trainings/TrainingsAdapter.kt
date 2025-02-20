package ru.sweetmilk.gymtracker.fragments.trainings

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.data.entities.Training
import ru.sweetmilk.gymtracker.databinding.TrainingItemBinding

class TrainingsAdapter(
    private val viewModel: TrainingsViewModel,
    private val inflater: LayoutInflater
) :
    ListAdapter<Training, TrainingViewHolder>(TrainingItemDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding = TrainingItemBinding.inflate(inflater, parent, false)
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = getItem(position)
        holder.bind(training)
    }
}

class TrainingViewHolder(private val binding: TrainingItemBinding) : ViewHolder(binding.root) {
    fun bind(
        training: Training,
    ) {
        val dateString =
            DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH).format(training.date);
        binding.trainingDate.text = dateString
    }
}

object TrainingItemDiffUtilCallback : DiffUtil.ItemCallback<Training>() {
    override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
        return oldItem == newItem
    }
}