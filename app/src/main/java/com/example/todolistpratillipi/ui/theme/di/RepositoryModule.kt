package com.example.todolistpratillipi.ui.theme.di

import android.content.Context
import androidx.room.Room
import com.example.todolistpratillipi.ui.theme.bussiness.TaskDao
import com.example.todolistpratillipi.ui.theme.bussiness.TaskDatabase
import com.example.todolistpratillipi.ui.theme.bussiness.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase) = database.taskDao()

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }
}


