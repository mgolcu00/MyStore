package com.mertgolcu.mystore.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mertgolcu.mystore.data.local.database.CozyDatabase
import com.mertgolcu.mystore.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author mertgolcu
 * @since 16.10.2023
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): CozyDatabase = Room.databaseBuilder(
        application.applicationContext,
        CozyDatabase::class.java,
        "cozy_database"
    ).build()

    @Provides
    @Singleton
    fun provideTaskRepository(database: CozyDatabase): TaskRepository {
        return TaskRepository(database.taskDao())
    }

}