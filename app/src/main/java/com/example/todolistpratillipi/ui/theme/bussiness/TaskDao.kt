package com.example.todolistpratillipi.ui.theme.bussiness

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.todolistpratillipi.ui.theme.bussiness.Task

@Dao
interface  TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>
}
