package com.example.todolistpratillipi.ui.theme.screen


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistpratillipi.ui.theme.bussiness.Task
import com.example.todolistpratillipi.ui.theme.bussiness.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // A flow representing the current list of tasks
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> get() = _allTasks

    // Add a new task to the repository
    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.addTask(task)
            fetchTasks()
        }
    }

    // Delete a task from the repository
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            fetchTasks()
        }
    }

    // Fetch all tasks from the repository
    private fun fetchTasks() {
        viewModelScope.launch {
            val tasks = taskRepository.getAllTasks()
            _allTasks.value = tasks
        }
    }

    // Init block to load tasks when ViewModel is created
    init {
        fetchTasks()
    }
}
