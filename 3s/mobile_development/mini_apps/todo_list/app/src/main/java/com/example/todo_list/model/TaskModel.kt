package com.example.todo_list.model

import java.util.UUID

enum class Category (val label: String){
    LAZER("Lazer"),
    ESTUDO("Estudo"),
    ESPORTE("Esporte"),
    SAUDE("Saude")
}

data class Task(
    val id: UUID = UUID.randomUUID(),
    val description: String,
    var done: Boolean = false,
    val category: Category
)

val tasks = listOf(
    Task(description = "dormir 12h", category = Category.SAUDE),
    Task(description = "Estudar", done = true, category = Category.LAZER),
)