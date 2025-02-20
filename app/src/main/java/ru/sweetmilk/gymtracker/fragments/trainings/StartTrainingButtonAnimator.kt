package ru.sweetmilk.gymtracker.fragments.trainings

import android.content.Context

import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.databinding.FragTrainingsBinding

class StartTrainingButtonAnimator(
    private val binding: FragTrainingsBinding,
    context: Context
) {

    val resources = context.resources

    private fun calculateTrainingBtnContainerHeight(bottomSheetSlideOffset: Float): Int {
        val windowsHeight = binding.root.height
        val collapsedHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_collapsed_height)
        val expandedHeight = binding.bottomSheet.height
        val delta = expandedHeight - collapsedHeight

        val buttonContainerHeight =
            Math.round(windowsHeight - collapsedHeight - (bottomSheetSlideOffset * delta))

        return buttonContainerHeight
    }

    fun updateTrainingBtn(bottomSheetSlideOffset: Float) {
        val buttonContainerHeight = calculateTrainingBtnContainerHeight(bottomSheetSlideOffset)

        if (bottomSheetSlideOffset < 0.5) {
            binding.startTrainingBtnContainer.layoutParams.also {
                it.height = buttonContainerHeight
                binding.startTrainingBtnContainer.layoutParams = it
            }
            binding.startTrainingButton.alpha = 1f
        }
        else {
            binding.startTrainingButton.alpha = 1f - (bottomSheetSlideOffset - 0.5f) * 2
        }
    }
}