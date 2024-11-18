package ru.sweetmilk.gymtracker.cases.trainingPlan

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.data.entities.ExerciseAndTrainingPlanItems
import ru.sweetmilk.gymtracker.databinding.TrainingPlanExerciseCardItemBinding
import ru.sweetmilk.gymtracker.extensions.getLocaleResources


class ExerciseAndTrainingPlanItemsAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: TrainingPlanViewModel
) : ListAdapter<ExerciseAndTrainingPlanItems, ExerciseAndTrainingPlanItemsHolder>(
    ExerciseAndTrainingPlanItemsDiffUtil
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAndTrainingPlanItemsHolder {
        val binding = TrainingPlanExerciseCardItemBinding.inflate(inflater, parent, false)
        return ExerciseAndTrainingPlanItemsHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ExerciseAndTrainingPlanItemsHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}


class ExerciseAndTrainingPlanItemsHolder(
    private val binding: TrainingPlanExerciseCardItemBinding,
    private val viewModel: TrainingPlanViewModel
) :
    ViewHolder(binding.root), View.OnClickListener {

    private lateinit var exerciseAndTrainingPlanItems: ExerciseAndTrainingPlanItems

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(exerciseAndTrainingPlanItems: ExerciseAndTrainingPlanItems) {
        this.exerciseAndTrainingPlanItems = exerciseAndTrainingPlanItems
        val name = exerciseAndTrainingPlanItems.exercise.name
        val trainingPlan = getTrainingPlanDescription()

        binding.exerciseName.text = name
        binding.exerciseTrainingPlan.text = trainingPlan
    }

    private fun getTrainingPlanDescription(): String {
        val res = itemView.context.getLocaleResources()

        return if (exerciseAndTrainingPlanItems.exercise.hasDuration) {
            if (exerciseAndTrainingPlanItems.trainingPlanItems.size == 1) {
                getOnceApproachWithDurationText(res)
            } else {
                getApproachWithDurationText(res)
            }
        } else {
            if (exerciseAndTrainingPlanItems.trainingPlanItems.size == 1) {
                getOnceApproachWithoutDurationText(res)
            } else {
                getApproachWithoutDurationText(res)
            }
        }
    }

    private fun getApproachWithDurationText(res: Resources) =
        exerciseAndTrainingPlanItems.trainingPlanItems.mapIndexed { index, it ->
            res.getString(
                R.string.approach_with_duration,
                index + 1
            ) + res.getQuantityString(
                R.plurals.seconds,
                it.duration ?: 0,
                it.duration ?: 0
            )
        }.joinToString("\n")

    private fun getOnceApproachWithDurationText(res: Resources) =
        exerciseAndTrainingPlanItems.trainingPlanItems.map {
            res.getQuantityString(
                R.plurals.seconds,
                it.duration ?: 0,
                it.duration ?: 0
            )
        }.joinToString("\n")

    private fun getApproachWithoutDurationText(res: Resources) =
        exerciseAndTrainingPlanItems.trainingPlanItems.mapIndexed { index, it ->
            res.getString(
                R.string.approach_without_duration,
                index + 1,
                it.weight ?: 0f
            ) + res.getQuantityString(
                R.plurals.count,
                it.count ?: 0,
                it.count ?: 0
            )
        }.joinToString("\n")

    private fun getOnceApproachWithoutDurationText(res: Resources) =
        exerciseAndTrainingPlanItems.trainingPlanItems.mapIndexed { index, it ->
            res.getString(
                R.string.approach_without_duration_once,
                it.weight ?: 0f
            ) + res.getQuantityString(
                R.plurals.count,
                it.count ?: 0,
                it.count ?: 0,
            )
        }.joinToString("\n")

    override fun onClick(v: View?) {
        viewModel.updateTrainingPlanItem(exerciseAndTrainingPlanItems)
    }
}

object ExerciseAndTrainingPlanItemsDiffUtil : ItemCallback<ExerciseAndTrainingPlanItems>() {
    override fun areItemsTheSame(
        oldItem: ExerciseAndTrainingPlanItems,
        newItem: ExerciseAndTrainingPlanItems
    ): Boolean {
        return oldItem.exercise.id == newItem.exercise.id
    }

    override fun areContentsTheSame(
        oldItem: ExerciseAndTrainingPlanItems,
        newItem: ExerciseAndTrainingPlanItems
    ): Boolean {
        return oldItem == newItem
    }
}