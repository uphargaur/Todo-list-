package com.example.todolistbuyhatke.ui.theme.bussiness

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
    val description: String = "",
    val dueDate: String = "",
    val priorityLevel: Int = 1,
    val isCompleted: Boolean = false,
)


