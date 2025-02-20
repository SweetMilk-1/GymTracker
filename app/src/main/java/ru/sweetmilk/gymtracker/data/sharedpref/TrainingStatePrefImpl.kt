package ru.sweetmilk.gymtracker.data.sharedpref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanExercise


class TrainingStatePrefImpl(context: Context) : TrainingStatePref {
    private val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override var trainingPlan: List<TrainingPlanExercise>?
        set(value) {
            Gson().apply {
                with(sharedPref.edit()) {
                    putString(KEY_TRAINING_PLAN, toJson(value))
                    apply()
                }
            }
        }
        get() {
            val songJson = sharedPref.getString(KEY_TRAINING_PLAN, null)
            val type = object : TypeToken<List<TrainingPlanExercise>?>() {}.type
            Gson().apply {
                return fromJson(songJson, type)
            }
        }

    override var currentExerciseIndex: Int?
        set(value) {
            with(sharedPref.edit()) {
                putInt(KEY_CURRENT_EXERCISE_INDEX, value ?: -1)
                apply()
            }
        }
        get() {
            val result = sharedPref.getInt(KEY_CURRENT_EXERCISE_INDEX, -1)
            return if (result == -1) null else result
        }

    override var isRelaxNow: Boolean
        get() {
            return  sharedPref.getBoolean(KEY_CURRENT_EXERCISE_INDEX, false)
        }
        set(value) {
            with(sharedPref.edit()) {
                putBoolean(KEY_IS_RELAX_NOW, value)
                apply()
            }
        }

    companion object {
        private const val SHARED_PREF_NAME = "TRAINING_PLAN_SHARED_PREF"

        private const val KEY_TRAINING_PLAN = "KEY_TRAINING_PLAN"
        private const val KEY_CURRENT_EXERCISE_INDEX = "KEY_CURRENT_EXERCISE_INDEX"
        private const val KEY_IS_RELAX_NOW = "KEY_IS_RELAX_NOW"
    }
}