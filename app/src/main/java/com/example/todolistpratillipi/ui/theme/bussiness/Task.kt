package com.example.todolistpratillipi.ui.theme.bussiness

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
    val isCompleted: Boolean = false,
    val orderIndex: Int = 0 // for ordering tasks
)

