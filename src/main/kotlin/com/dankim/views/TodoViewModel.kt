package com.dankim.views

import com.dankim.database
import com.dankim.db.Todo
import com.dankim.db.Todos
import com.dankim.db.User
import com.dankim.db.Users
import com.dankim.util.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

class TodoViewModel : CoroutineScope {
    var todos: Observable<List<Todo>> = Observable()
    var users: Observable<List<User>> = Observable()
    private var searchTerm: String? = null

    private val job = SupervisorJob()
    private val dispatcher = Dispatchers.Default
    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

    fun fetchAll(searchTerm: String? = null) {
        searchTerm?.let {
            this@TodoViewModel.searchTerm = it
        }

        launch {
            todos.value = newSuspendedTransaction(Dispatchers.IO, database) {
                val query = Todos.join(Users, JoinType.LEFT, Todos.userId, Users.id)
                    .selectAll()
                    .orderBy(Todos.completedAt, SortOrder.DESC)
                    .orderBy(Todos.dueAt, SortOrder.ASC)
                    .orderBy(Todos.title, SortOrder.ASC)

                this@TodoViewModel.searchTerm?.let {
                    query.where { Todos.title.upperCase().like("%${this@TodoViewModel.searchTerm?.uppercase()}%") }
                }

                query.map {
                    Todo(
                        id = it[Todos.id].value,
                        title = it[Todos.title],
                        dueAt = it[Todos.dueAt],
                        notes = it[Todos.description],
                        user = User(
                            id = it[Users.id]?.value,
                            name = it[Users.name]
                        ),
                        completedAt = it[Todos.completedAt]
                    )
                }
            }
        }
    }

    fun fetchUsers() {
        launch {
            users.value = newSuspendedTransaction(Dispatchers.IO, database) {
                Users.selectAll()
                    .orderBy(Users.name)
                    .map {
                        User(
                            id = it[Users.id].value,
                            name = it[Users.name] ?: "No Name"
                        )
                    }
            }
        }
    }

    fun updateCompletion(todo: Todo, isCompleted: Boolean) {
        launch {
            newSuspendedTransaction(Dispatchers.IO, database) {
                Todos.update({ Todos.id eq todo.id }) {
                    it[Todos.completedAt] = if (isCompleted) LocalDate.now() else null
                }
            }

            fetchAll()
        }
    }

    fun update(todo: Todo, isCompleted: Boolean? = null) {
        launch {
            newSuspendedTransaction(Dispatchers.IO, database) {
                Todos.update({ Todos.id eq todo.id }) {
                    it[Todos.title] = todo.title
                    it[Todos.dueAt] = todo.dueAt
                    it[Todos.description] = todo.notes
                    it[Todos.userId] = todo.user?.id
                }
            }

            fetchAll()
        }
    }

    fun insert(todo: Todo) {
        launch {
            newSuspendedTransaction(Dispatchers.IO, database) {
                Todos.insert {
                    it[Todos.title] = todo.title
                    it[Todos.dueAt] = todo.dueAt
                    it[Todos.description] = todo.notes
                    it[Todos.userId] = todo.user?.id
                }
            }

            fetchAll()
        }
    }

    fun delete(todo: Todo) {
        launch {
            newSuspendedTransaction(Dispatchers.IO, database) {
                Todos.deleteWhere { Todos.id eq todo.id }
            }

            fetchAll()
        }
    }
}