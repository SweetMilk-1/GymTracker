package ru.sweetmilk.gymtracker.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sweetmilk.gymtracker.data.entities.Exercise
import ru.sweetmilk.gymtracker.data.entities.TrainingPlanItem

@Database(
    entities = [Exercise::class, TrainingPlanItem::class], version = 2, exportSchema = true, autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTrainingPlanItemDao(): TrainingPlanItemDao

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