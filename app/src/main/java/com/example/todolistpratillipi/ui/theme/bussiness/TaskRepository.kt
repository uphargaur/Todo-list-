package com.example.todolistpratillipi.ui.theme.bussiness

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao // Inject the TaskDao for database operations
) {
    // Add a new task
    suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    // Delete a task
    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }

    // Get all tasks
    suspend fun getAllTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDao.getAllTasks()
        }
    }
}
