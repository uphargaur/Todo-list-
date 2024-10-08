package com.example.todolistbuyhatke.ui.theme.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.todolistbuyhatke.ui.theme.bussiness.Task
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            TaskApp(isDarkMode) { isDarkMode = !isDarkMode }
        }
    }
}

@Composable
fun TaskApp(isDarkMode: Boolean, toggleTheme: () -> Unit) {
    val colors = if (isDarkMode) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colors) {
        TaskScreen(toggleTheme)
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDelete: (Task) -> Unit,
    onEdit: (Task) -> Unit,
    viewModel: TaskViewModel
) {
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEC300))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f) // Allows the column to take available space
                ) {
                    Text(
                        text = task.taskName,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), // Bold title
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), // Shorter description font
                        color = Color.Black
                    )
                }

                // Circular button for marking the task as complete
                CircularButton(
                    checked = isChecked,
                    color = if (isChecked) Color.Green else Color.Red,
                    onClick = {
                        isChecked = !isChecked
                        // Update task status based on button click
                        viewModel.updateTask(task.copy(isCompleted = isChecked))
                    }
                )
            }

            // Buttons for Edit and Delete at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
//                Text(
//                    text = task.priorityLevel.toString(),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), // Shorter description font
//                    color = Color.Black
//                )
                IconButton(onClick = { onEdit(task) }) { // Edit button
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Task",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = {
                    onDelete(task)
                    viewModel.fetchTasks() // Refresh the task list after deletion
                }) { // Delete button
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CircularButton(checked: Boolean, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color, shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Checked", tint = Color.White)
        } else {
            Icon(Icons.Default.Warning, contentDescription = "Unchecked", tint = Color.White) // Optional: Add an unchecked icon
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(toggleTheme: () -> Unit, viewModel: TaskViewModel = hiltViewModel()) {
    val taskList by viewModel.allTasks.collectAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }

    val refreshTasks: () -> Unit = {
        isRefreshing = true
        viewModel.fetchTasks()
        isRefreshing = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Task List") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = false,
                            onCheckedChange = { toggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black,
                                uncheckedThumbColor = Color.Gray
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { refreshTasks() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            TaskList(taskList, viewModel, modifier = Modifier.padding(paddingValues),
                onEdit = { task ->
                    taskToEdit = task
                    showDialog = true
                }
            )
        }

        if (showDialog) {
            if (taskToEdit == null) {
                AddTaskDialog(
                    onAddTask = { taskName, dueDate, description, priorityLevel ->  // Include description
                        viewModel.addTask(Task(taskName = taskName, dueDate = dueDate, description = description, priorityLevel = priorityLevel))
                        viewModel.fetchTasks()
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )

            } else {
                EditTaskDialog(
                    task = taskToEdit!!,
                    onUpdateTask = { updatedTask ->
                        viewModel.updateTask(updatedTask)
                        viewModel.fetchTasks()
                        showDialog = false
                        taskToEdit = null
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}
@Composable
fun AddTaskDialog(onAddTask: (String, String, String, Int) -> Unit, onDismiss: () -> Unit) {
    var taskName by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }  // New description state
    var priorityLevel by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) } // State to manage DatePicker visibility

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

                Spacer(Modifier.height(16.dp))

                // Button to show DatePicker with a calendar icon
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), // Add some vertical padding
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEC300)), // Change button color
                    shape = MaterialTheme.shapes.medium // Use material shape for rounded corners
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.padding(end = 8.dp), // Space between icon and text
                        tint = Color.White // Icon color
                    )
                    Text(
                        text = if (dueDate.isNotBlank()) dueDate else "Select Due Date",
                        color = Color.White // Text color
                    )
                }

                // Show DatePickerDialog if showDatePicker is true
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        onDateSelected = { year, month, day ->
                            dueDate = "$day/${month + 1}/$year" // Format the date
                            showDatePicker = false // Close the date picker
                        }
                    )
                }

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },  // Handling description input
                    label = { Text(text = "Description") },
                    singleLine = false,  // Allows multiple lines for the description
                    maxLines = 3  // Optional: limit the number of visible lines
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = priorityLevel,
                    onValueChange = { priorityLevel = it },
                    label = { Text(text = "Priority Level") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (taskName.isNotBlank() && dueDate.isNotBlank() && description.isNotBlank() && priorityLevel.isNotBlank()) {
                    // Pass the description along with other task details
                    onAddTask(taskName, dueDate, description, priorityLevel.toIntOrNull() ?: 0)
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

@Composable
fun TaskList(
    taskList: List<Task>,
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier,
    onEdit: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(taskList) { task ->
            TaskItem(task, viewModel::deleteTask, onEdit, viewModel)
        }
    }
}

@Composable
fun EditTaskDialog(task: Task, onUpdateTask: (Task) -> Unit, onDismiss: () -> Unit) {
    var taskName by remember { mutableStateOf(task.taskName) }
    var dueDate by remember { mutableStateOf(task.dueDate) }
    var priorityLevel by remember { mutableStateOf(task.priorityLevel.toString()) }
    var description by remember { mutableStateOf(task.description) }

    // State to manage the visibility of the DatePicker
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Task") },
        text = {
            Column {
                TextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    label = { Text(text = "Task Name") },
                    singleLine = true
                )

                // Button to show DatePickerDialog
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), // Add some vertical padding
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEC300)), // Change button color
                    shape = MaterialTheme.shapes.medium // Use material shape for rounded corners
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.padding(end = 8.dp), // Add some space between icon and text
                        tint = Color.Black // Change icon color
                    )
                    Text(
                        text = if (dueDate.isNotBlank()) dueDate else "Select Due Date",
                        color = Color.White // Change text color
                    )
                }

                Spacer(Modifier.height(20.dp))


                // Show DatePickerDialog if showDatePicker is true
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        onDateSelected = { year, month, day ->
                            dueDate = "$day/${month + 1}/$year" // Format the date
                            showDatePicker = false // Close the date picker
                        }
                    )

                }

                TextField(
                    value = priorityLevel,
                    onValueChange = { priorityLevel = it },
                    label = { Text(text = "Priority Level") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Description") },
                    singleLine = false // Allow multi-line input
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (taskName.isNotBlank()) {
                    onUpdateTask(
                        task.copy(
                            taskName = taskName,
                            dueDate = dueDate,
                            priorityLevel = priorityLevel.toIntOrNull() ?: task.priorityLevel,
                            description = description
                        )
                    )
                }
            }) {
                Text(text = "Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun DatePickerDialog(onDismissRequest: () -> Unit, onDateSelected: (Int, Int, Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Creating and showing DatePickerDialog
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
        },
        year,
        month,
        day
    )

    datePickerDialog.setOnDismissListener { onDismissRequest() }
    datePickerDialog.show()
}


