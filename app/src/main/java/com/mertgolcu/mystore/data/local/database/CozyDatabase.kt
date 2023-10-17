package com.mertgolcu.mystore.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mertgolcu.mystore.data.local.daos.TaskDao
import com.mertgolcu.mystore.data.local.entities.Task
import com.mertgolcu.mystore.data.utils.Converters

/**
 * @author mertgolcu
 * @since 16.10.2023
 */

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CozyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: CozyDatabase? = null

        fun getDatabase(context: Context): CozyDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CozyDatabase::class.java, "cozy_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}