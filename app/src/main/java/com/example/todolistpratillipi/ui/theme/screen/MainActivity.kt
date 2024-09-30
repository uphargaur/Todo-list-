package com.example.todolistpratillipi.ui.theme.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolistpratillipi.ui.theme.bussiness.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val taskList by viewModel.allTasks.collectAsState()  // Correctly collecting StateFlow
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        // Task list with padding from scaffold
        TaskList(taskList, viewModel, modifier = Modifier.padding(paddingValues))

        // Show add task dialog when FAB is clicked
        if (showDialog) {
            AddTaskDialog(
                onAddTask = { taskName ->
                    viewModel.addTask(Task(taskName = taskName))
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun TaskList(taskList: List<Task>, viewModel: TaskViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(taskList) { task ->
            TaskItem(task, viewModel::deleteTask)
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: (Task) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = task.taskName, style = MaterialTheme.typography.bodyLarge)
        IconButton(onClick = { onDelete(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
        }
    }
}

@Composable
fun AddTaskDialog(onAddTask: (String) -> Unit, onDismiss: () -> Unit) {
    var taskName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Task") },
        text = {
            Column {
                TextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    label = { Text(text = "Task Name") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (taskName.isNotBlank()) {
                    onAddTask(taskName)
                }
            }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskScreen() {
    TaskScreen()  // No real ViewModel; for UI preview
}
