package ru.sweetmilk.gymtracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet

@Database(
    entities = [Exercise::class, TrainingPlanSet::class],
    version = 1,
    exportSchema = true,
    autoMigrations = [
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTrainingPlanDao(): TrainingPlanDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "GYM_TRACKER"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                buildAppDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}