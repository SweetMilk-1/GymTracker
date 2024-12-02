package ru.sweetmilk.gymtracker.cases.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.databinding.ExerciseItemBinding

class TrainingsAdapter(
    private val viewModel: TrainingsViewModel,
    private val inflater: LayoutInflater
) :
    ListAdapter<Exercise, ExerciseViewHolder>(ExerciseItemDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ExerciseItemBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise, viewModel)
    }
}

class ExerciseViewHolder(private val binding: ExerciseItemBinding) : ViewHolder(binding.root) {
    fun bind(
        exercise: Exercise,
        viewModel: TrainingsViewModel
    ) {
    }
}

object ExerciseItemDiffUtilCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }
}