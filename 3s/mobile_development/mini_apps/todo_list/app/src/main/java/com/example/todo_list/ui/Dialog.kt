package com.example.todo_list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_list.model.Category
import com.example.todo_list.model.Task

@Composable
fun AddTaskDialog(onDimiss: () -> Unit, onConfirm: (task: Task) -> Unit, modifier: Modifier = Modifier) {
    var selectCategory by remember { mutableStateOf<Category>(Category.LAZER) }
    var taskDescription by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text="Add task")
        },
        onDismissRequest = onDimiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(Task(
                    description = taskDescription,
                    category = selectCategory
                )) }
            ) {
                Text(text = "Adicionar")
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDimiss) {
                Text(text="Cancelar")
            }
        },
        text = {
            Column(

            ) {
                TextField(
                    value = taskDescription,
                    onValueChange = {taskDescription = it},
                    label = {
                        Text(text = "Descricao")
                    }
                )
                Column(modifier = Modifier.selectableGroup()) {
                    for (category in Category.entries) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectCategory == category,
                                onClick = { selectCategory = category }
                            )) {
                            RadioButton(selected = selectCategory == category, onClick = null)
                            Text(text = category.label)
                        }
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun PreviewDialog() {
    AddTaskDialog(onDimiss = {}, onConfirm = {})
}