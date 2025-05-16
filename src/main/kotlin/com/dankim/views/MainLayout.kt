package com.dankim.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.content
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.flexGrow
import com.github.mvysny.karibudsl.v10.h1
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout

class MainLayout : KComposite(), RouterLayout {
    private val root = ui {
        verticalLayout {
            content {
                align(center, top)
            }
            div {
                h1("Todos")
            }
        }
    }

    override fun showRouterLayoutContent(content: HasElement) {
        root.add(content as Component)
        content.isExpand = true
    }
}