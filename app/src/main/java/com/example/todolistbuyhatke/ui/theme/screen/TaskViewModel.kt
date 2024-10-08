package com.example.todolistbuyhatke.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistbuyhatke.ui.theme.bussiness.Task
import com.example.todolistbuyhatke.ui.theme.bussiness.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    // A flow representing the current list of tasks
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> get() = _allTasks


    // Add a new task to the repository
    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)  // Changed method name to match repository
        }
    }

    // Delete a task from the repository
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)

        }
    }
    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    // Fetch all tasks from the repository and collect updates
     fun fetchTasks() {
        viewModelScope.launch {
            _allTasks.value = taskRepository.getAllTasks() // Fetching tasks from repository
        }
    }
    fun setTasks(tasks: List<Task>) {
        _allTasks.value = tasks
    }

    // Init block to load tasks when ViewModel is created
    init {
        fetchTasks()
    }
}
