package ru.sweetmilk.gymtracker.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.sweetmilk.gymtracker.data.database.dao.ExerciseDao
import ru.sweetmilk.gymtracker.data.database.dao.TrainingDao
import ru.sweetmilk.gymtracker.data.database.dao.TrainingPlanDao
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.entities.Training
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanSet

@Database(
    entities = [Exercise::class, TrainingPlanSet::class, Training::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTrainingPlanDao(): TrainingPlanDao
    abstract fun getTrainingDao(): TrainingDao

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