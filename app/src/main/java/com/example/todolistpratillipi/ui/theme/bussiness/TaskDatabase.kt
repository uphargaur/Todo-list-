package com.example.todolistpratillipi.ui.theme.bussiness

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolistpratillipi.ui.theme.bussiness.Task
import com.example.todolistpratillipi.ui.theme.bussiness.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .fallbackToDestructiveMigration() // Handle migration strategy
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
