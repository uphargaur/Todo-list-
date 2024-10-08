package com.example.todolistbuyhatke.ui.theme.bussiness

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return withContext(Dispatchers.IO) {  // Switches to IO thread for the database operation
            taskDao.getAllTasks()
        }
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}
