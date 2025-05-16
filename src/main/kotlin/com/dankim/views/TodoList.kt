package com.dankim.views

import com.dankim.db.Todo
import com.dankim.util.setDefaults
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.Route

@Route(value = "", layout = MainLayout::class)
class TodoList : KComposite() {
    private val currentUi = UI.getCurrent()
    private val viewModel = TodoViewModel()
    private lateinit var grid: Grid<Todo>

    private val root = ui {
        verticalLayout {
            button("Add todo", icon = VaadinIcon.PLUS.create()) {
                onClick {
                    TodoForm { todo -> viewModel.insert(todo) }
                }
            }

            textField {
                setWidthFull()
                isClearButtonVisible = true
                placeholder = "Search for todo title..."
                valueChangeMode = ValueChangeMode.LAZY
                addValueChangeListener { event ->
                    viewModel.fetchAll(event.value)
                }
            }

            grid = grid {
                setDefaults()

                componentColumn({ todo ->
                    TodoWithCheckbox(
                        todo = todo,
                        onCheckChanged = { isChecked -> viewModel.updateCompletion(todo, isChecked) },
                        onClickLink = {
                            TodoForm(
                                todo = todo,
                                onClickSave = { viewModel.update(it) },
                                onClickDelete = { viewModel.delete(it) }
                            )
                        }
                    )
                }).setHeader("Todo")

                column({ it.dueAt?.toString() }).setHeader("Due Date")
                column({ it.user?.name }).setHeader("Assignee")
            }
        }
    }

    init {
        configObservers()
        viewModel.fetchAll()
    }

    private fun configObservers() {
        viewModel.todos.observe {
            currentUi.accessSynchronously {
                grid.setItems(it)
            }
        }
    }
}
