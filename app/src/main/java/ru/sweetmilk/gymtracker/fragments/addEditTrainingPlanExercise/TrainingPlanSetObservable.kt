package ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet
import java.util.UUID

class TrainingPlanSetObservable(private val viewModel: AddEditTrainingPlanExerciseViewModel) :
    BaseObservable() {
    private lateinit var trainingPlanSet: TrainingPlanSet

    fun setTrainingPlanItem(trainingPlanSet: TrainingPlanSet) {
        this.trainingPlanSet = trainingPlanSet
        notifyChange()
    }

    @get:Bindable
    @set:Bindable
    var weight: String?
        get() = trainingPlanSet.weight?.toString()
        set(value) {
            if (!value.isNullOrEmpty()) {
                trainingPlanSet.weight = value.toFloat()
                viewModel.updateTrainingPlanItem(trainingPlanSet)
            }
        }

    @get:Bindable
    @set:Bindable
    var count: String?
        get() = trainingPlanSet.count?.toString()
        set(value) {
            if (!value.isNullOrEmpty()) {
                trainingPlanSet.count = value.toInt()
                viewModel.updateTrainingPlanItem(trainingPlanSet)
            }
        }

    @get:Bindable
    @set:Bindable
    var duration: String?
        get() = trainingPlanSet.duration?.toString()
        set(value) {
            if (!value.isNullOrEmpty()) {
                trainingPlanSet.duration = value.toInt()
                viewModel.updateTrainingPlanItem(trainingPlanSet)
            }
        }

    val id: UUID get() = trainingPlanSet.id
}
