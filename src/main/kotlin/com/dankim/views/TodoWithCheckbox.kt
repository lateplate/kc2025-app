package com.dankim.views

import com.dankim.db.Todo
import com.github.mvysny.karibudsl.v10.*

class TodoWithCheckbox(
    todo: Todo,
    onCheckChanged: (Boolean) -> Unit,
    onClickLink: () -> Unit
) : KComposite() {
    private val root = ui {
        verticalLayout(false, false) {
            horizontalLayout {
                checkBox {
                    value = todo.completedAt != null
                    addValueChangeListener { event -> onCheckChanged(event.value) }
                }

                span(todo.title) {
                    addClassName("link-text")
                    if (todo.completedAt != null) {
                        addClassName("strikethrough")
                    }

                    addClickListener { onClickLink() }
                }
            }

            todo.notes?.let {
                span(it) {
                    if (todo.completedAt != null) {
                        addClassName("strikethrough")
                    }
                }
            }
        }
    }
}