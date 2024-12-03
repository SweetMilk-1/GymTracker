package ru.sweetmilk.gymtracker.cases.trainings

import android.content.Context
import android.view.View

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
        val expandedHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_expanded_height)
        val delta = expandedHeight - collapsedHeight

        val buttonContainerHeight =
            Math.round(windowsHeight - collapsedHeight - (bottomSheetSlideOffset * delta))

        return buttonContainerHeight
    }

    fun updateTrainingBtn(bottomSheetSlideOffset: Float) {
        val buttonContainerHeight = calculateTrainingBtnContainerHeight(bottomSheetSlideOffset)
        val buttonMaxSize = resources.getDimensionPixelSize(R.dimen.start_training_btn_size)

        val buttonHeight = binding.startTrainingImageBtn.height
        val labelHeight = binding.startTrainingLabel.height

        if (buttonMaxSize < buttonContainerHeight) {
            binding.startTrainingBtnContainer.layoutParams.also {
                it.height = buttonContainerHeight
                binding.startTrainingBtnContainer.layoutParams = it
            }
            binding.startTrainingLabel.visibility =
                if (buttonHeight + labelHeight < buttonContainerHeight) View.VISIBLE else View.GONE
            return
        }

        binding.startTrainingBtnContainer.layoutParams.also {
            it.height = buttonContainerHeight
            binding.startTrainingBtnContainer.layoutParams = it
        }
        binding.startTrainingImageBtn.layoutParams.also {
            it.height = buttonContainerHeight
            it.width = buttonContainerHeight
            binding.startTrainingImageBtn.layoutParams = it
        }
        binding.startTrainingLabel.visibility =
            if (buttonHeight + labelHeight < buttonContainerHeight) View.VISIBLE else View.GONE
        binding.startTrainingImageBtn.visibility =
            if (buttonContainerHeight > buttonMaxSize * MAX_COMPRESS_RATIO) View.VISIBLE else View.GONE
    }

    companion object {
        private const val MAX_COMPRESS_RATIO = 0.2f
    }
}