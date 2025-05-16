package com.dankim.util

import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.grid.Grid

fun <T : Any?> Grid<T>.setDefaults() {
    setWidthFull()
    isAllRowsVisible = true
    selectionMode = Grid.SelectionMode.NONE
    applyStripedTheme()
    addThemeVariants(com.vaadin.flow.component.grid.GridVariant.LUMO_WRAP_CELL_CONTENT)
}

fun <T : Any?> Grid<T>.applyStripedTheme() {
    this.addThemeVariants(
        com.vaadin.flow.component.grid.GridVariant.LUMO_WRAP_CELL_CONTENT,
        com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES,
        com.vaadin.flow.component.grid.GridVariant.LUMO_COLUMN_BORDERS
    )
}