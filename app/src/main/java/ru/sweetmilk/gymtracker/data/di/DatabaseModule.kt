package ru.sweetmilk.gymtracker.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sweetmilk.gymtracker.data.database.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}