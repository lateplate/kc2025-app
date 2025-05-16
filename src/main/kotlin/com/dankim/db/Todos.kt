package com.dankim.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Todos : IntIdTable("todos") {
    val title = varchar("title", 255).nullable()
    val description = varchar("description", 255).nullable()
    val dueAt = date("due_at").nullable()
    val completedAt = date("completed_at").nullable()
    val userId = reference("user_id", Users).nullable()
}

object Users : IntIdTable("users") {
    val name = varchar("name", 255).nullable()
}

data class Todo(
    val id: Int? = null,
    val title: String? = null,
    val notes: String? = null,
    val dueAt: LocalDate? = null,
    val completedAt: LocalDate? = null,
    val user: User? = null
)

data class User(
    val id: Int? = null,
    val name: String? = null
)