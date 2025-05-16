package com.dankim

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.shared.communication.PushMode
import com.vaadin.flow.theme.Theme
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

@Theme("my-theme")
@Push(PushMode.AUTOMATIC) // websocket ui updates
class AppShell : AppShellConfigurator

fun main(args: Array<String>) {
    VaadinBoot().run()
}

val dataSource = HikariDataSource(HikariConfig().apply {
    jdbcUrl = "jdbc:postgresql://localhost:5432/"
    username = "postgres"
    password = "postgres"
    driverClassName = "org.postgresql.Driver"

    maximumPoolSize = 10
    minimumIdle = 5
    connectionTimeout = 30000
    idleTimeout = 10000
    maxLifetime = 1800000
})

val database = Database.connect(dataSource)
