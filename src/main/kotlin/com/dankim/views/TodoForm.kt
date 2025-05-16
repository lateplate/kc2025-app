package com.dankim.views

import com.dankim.db.Todo
import com.dankim.db.User
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.setPrimary
import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField

class TodoForm(
    private val todo: Todo? = null,
    onClickDelete: ((Todo) -> Unit)? = null,
    onClickSave: (Todo) -> Unit
) :
    KComposite() {
    private val currentUi = UI.getCurrent()
    private lateinit var dialog: Dialog
    private lateinit var titleField: TextField
    private lateinit var dueDateField: DatePicker
    private lateinit var notesField: TextArea
    private lateinit var userField: Select<User>
    private val viewModel = TodoViewModel()

    private val root = ui {
        verticalLayout {
            dialog = openDialog {
                verticalLayout {
                    formLayout {
                        titleField = textField("Title") {
                            isRequired = true
                            todo?.title?.let { value = it }
                        }

                        dueDateField = datePicker("Due Date") {
                            todo?.dueAt?.let { value = it }
                        }

                        notesField = textArea("Notes") {
                            maxRows = 2
                            todo?.notes?.let { value = it }
                        }

                        userField = select("Assignee") {
                            itemLabelGenerator = ItemLabelGenerator { it.name }
                        }
                    }

                    horizontalLayout {
                        if (todo != null) {
                            button("Delete") {
                                onClick {
                                    onClickDelete?.invoke(todo)
                                    dialog.close()
                                }
                            }
                        }

                        button("Cancel") {
                            onClick { dialog.close() }
                        }

                        button("Save") {
                            setPrimary()
                            onClick {
                                if (formIsInvalid()) return@onClick

                                val newTodo = generateTodoModel()
                                onClickSave(newTodo)
                                dialog.close()
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        configObservers()
        fetchUsers()
    }

    private fun configObservers() {
        viewModel.users.observe {
            currentUi.accessSynchronously {
                userField.setItems(it)
                todo?.user?.let {
                    userField.value = it
                }
            }
        }
    }

    fun fetchUsers() {
        viewModel.fetchUsers()
    }

    fun formIsInvalid(): Boolean {
        titleField.isInvalid = titleField.value.isNullOrEmpty()

        return titleField.isInvalid
    }

    fun generateTodoModel(): Todo {
        return if (todo != null) {
            todo.copy(
                title = titleField.value,
                dueAt = dueDateField.value,
                notes = notesField.value,
                user = userField.value
            )
        } else {
            Todo(
                title = titleField.value,
                dueAt = dueDateField.value,
                notes = notesField.value,
                user = userField.value
            )
        }
    }
}
