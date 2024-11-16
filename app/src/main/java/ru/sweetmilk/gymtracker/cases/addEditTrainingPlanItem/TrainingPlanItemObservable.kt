package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem
import java.util.UUID

class TrainingPlanItemObservable(private val viewModel: AddEditTrainingPlanItemViewModel) :
    BaseObservable() {
    private lateinit var trainingPlanItem: TrainingPlanItem

    fun setTrainingPlanItem(trainingPlanItem: TrainingPlanItem) {
        this.trainingPlanItem = trainingPlanItem.copy()
        notifyChange()
    }

    @get:Bindable
    @set:Bindable
    var weight: String?
        get() = trainingPlanItem.weight?.toString()
        set(value) {
            if (!value.isNullOrEmpty())
                trainingPlanItem.weight = value.toFloat()
            viewModel.updateTrainingPlanItem(trainingPlanItem)
        }

    @get:Bindable
    @set:Bindable
    var count: String?
        get() = trainingPlanItem.count?.toString()
        set(value) {
            if (!value.isNullOrEmpty())
                trainingPlanItem.count = value.toInt()
            viewModel.updateTrainingPlanItem(trainingPlanItem)
        }

    @get:Bindable
    @set:Bindable
    var duration: String?
        get() = trainingPlanItem.duration?.toString()
        set(value) {
            if (!value.isNullOrEmpty())
                trainingPlanItem.duration = value.toInt()
            viewModel.updateTrainingPlanItem(trainingPlanItem)
        }

    val id: UUID get() = trainingPlanItem.id
}
