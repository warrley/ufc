package com.example.todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_list.model.Task
import com.example.todo_list.model.tasks
import com.example.todo_list.ui.AddTaskDialog
import com.example.todo_list.ui.theme.Todo_listTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Todo_listTheme {
                ScaffoldTodoMainScreen()
            }
        }
    }
}

@Composable
fun ScaffoldTodoMainScreen() {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        TodoMainScreen(
            dialogIsVisible = showDialog,
            toogleDialog = {showDialog = !showDialog},
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TodoMainScreen(dialogIsVisible: Boolean, toogleDialog: () -> Unit, modifier: Modifier = Modifier) {
    val todos = remember { mutableStateListOf(*tasks.toTypedArray()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Todo List",
            fontSize = 28.sp,
        )
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(
                items = todos
            ) {
                todo -> TodoItem(todo)
            }
        }

        AnimatedVisibility(visible = !dialogIsVisible) {
            AddTaskDialog(
                onDimiss = toogleDialog,
                onConfirm = {task ->
                    todos.add(task)
                    toogleDialog()
                }
            )
        }
    }
}

@Composable
fun TodoItem(task: Task, modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(task.done) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = {checked = it})
        Text(text = task.description, fontSize = 24.sp)
    }
}

@Composable
fun AddTaskButton(openDialog: () -> Unit) {
    FloatingActionButton(
        onClick = openDialog
    ) {
        Text(text = "+")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Todo_listTheme {
        TodoMainScreen(dialogIsVisible = true, toogleDialog = {})
    }
}