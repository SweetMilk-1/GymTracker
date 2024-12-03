package ru.sweetmilk.gymtracker.cases.trainingPlan

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise
import ru.sweetmilk.gymtracker.databinding.TrainingPlanExerciseItemBinding
import ru.sweetmilk.gymtracker.extensions.getLocaleResources


class ExerciseAndTrainingPlanItemsAdapter(
    private val inflater: LayoutInflater,
    private val viewModel: TrainingPlanViewModel
) : ListAdapter<TrainingPlanExercise, ExerciseAndTrainingPlanItemsAdapter.TrainingPlanExerciseHolder>(
    TrainingPlanExerciseDiffUtil
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainingPlanExerciseHolder {
        val binding = TrainingPlanExerciseItemBinding.inflate(inflater, parent, false)
        return TrainingPlanExerciseHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: TrainingPlanExerciseHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TrainingPlanExerciseHolder(
        private val binding: TrainingPlanExerciseItemBinding,
        private val viewModel: TrainingPlanViewModel
    ) :
        ViewHolder(binding.root), View.OnClickListener {

        private lateinit var trainingPlanExercise: TrainingPlanExercise

        init {
            itemView.setOnClickListener(this)
            setupMenu()
        }

        fun bind(
            trainingPlanExercise: TrainingPlanExercise
        ) {
            this.trainingPlanExercise = trainingPlanExercise
            val name = trainingPlanExercise.exercise.name
            val trainingPlan = getTrainingPlanDescription()

            binding.exerciseName.text = name
            binding.trainingPlanSets.text = trainingPlan
        }

        private fun setupMenu() {
            binding.trainingPlanExerciseMenu.setOnClickListener {
                val popupMenu =
                    PopupMenu(itemView.context, binding.trainingPlanExerciseMenu).apply {
                        inflate(R.menu.training_plan_exercise_menu)

                        menu.findItem(R.id.move_up_training_plan_exercise)
                            .setVisible(adapterPosition > 0)
                        menu.findItem(R.id.move_down_training_plan_exercise)
                            .setVisible(adapterPosition < currentList.size - 1)

                        setOnMenuItemClickListener {
                            when (it.itemId) {
                                R.id.delete_training_plan_exercise -> {
                                    viewModel.deleteTrainingPlanExercise(trainingPlanExercise)
                                    true
                                }
                                R.id.move_up_training_plan_exercise -> {
                                    viewModel.moveUpTrainingPlanExercise(trainingPlanExercise)
                                    true
                                }
                                R.id.move_down_training_plan_exercise -> {
                                    viewModel.moveDownTrainingPlanExercise(trainingPlanExercise)
                                    true
                                }

                                else -> true
                            }
                        }
                    }
                popupMenu.show()
            }
        }

        private fun getTrainingPlanDescription(): String {
            val res = itemView.context.getLocaleResources()

            return if (trainingPlanExercise.exercise.hasDuration) {
                if (trainingPlanExercise.trainingPlanSets.size == 1) {
                    getOnceApproachWithDurationText(res)
                } else {
                    getApproachWithDurationText(res)
                }
            } else {
                if (trainingPlanExercise.trainingPlanSets.size == 1) {
                    getOnceApproachWithoutDurationText(res)
                } else {
                    getApproachWithoutDurationText(res)
                }
            }
        }

        private fun getApproachWithDurationText(res: Resources) =
            trainingPlanExercise.trainingPlanSets.mapIndexed { index, it ->
                res.getString(
                    R.string.set_with_duration,
                    index + 1
                ) + res.getQuantityString(
                    R.plurals.seconds,
                    it.duration ?: 0,
                    it.duration ?: 0
                )
            }.joinToString("\n")

        private fun getOnceApproachWithDurationText(res: Resources) =
            trainingPlanExercise.trainingPlanSets.map {
                res.getQuantityString(
                    R.plurals.seconds,
                    it.duration ?: 0,
                    it.duration ?: 0
                )
            }.joinToString("\n")

        private fun getApproachWithoutDurationText(res: Resources) =
            trainingPlanExercise.trainingPlanSets.mapIndexed { index, it ->
                res.getString(
                    R.string.set_without_duration,
                    index + 1,
                    it.weight ?: 0f
                ) + res.getQuantityString(
                    R.plurals.count,
                    it.count ?: 0,
                    it.count ?: 0
                )
            }.joinToString("\n")

        private fun getOnceApproachWithoutDurationText(res: Resources) =
            trainingPlanExercise.trainingPlanSets.mapIndexed { index, it ->
                res.getString(
                    R.string.set_without_duration_once,
                    it.weight ?: 0f
                ) + res.getQuantityString(
                    R.plurals.count,
                    it.count ?: 0,
                    it.count ?: 0,
                )
            }.joinToString("\n")

        override fun onClick(v: View?) {
            viewModel.updateTrainingPlanExercise(trainingPlanExercise)
        }
    }

    object TrainingPlanExerciseDiffUtil : ItemCallback<TrainingPlanExercise>() {
        override fun areItemsTheSame(
            oldItem: TrainingPlanExercise,
            newItem: TrainingPlanExercise
        ): Boolean {
            return oldItem.exercise.id == newItem.exercise.id
        }

        override fun areContentsTheSame(
            oldItem: TrainingPlanExercise,
            newItem: TrainingPlanExercise
        ): Boolean {
            return oldItem == newItem
        }
    }
}


