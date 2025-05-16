package com.dankim.util

class
Observable<TType> {
    class Connection

    var value: TType? = null
        set(value) {
            field = value
            callbacks.forEach { callback ->
                callback.value(value)
            }
        }

    private val callbacks = mutableMapOf<Connection, (TType?) -> Unit>()

    fun observe(callback: (newValue: TType?) -> Unit): Connection {
        val connection = Connection()
        callbacks[connection] = callback
        return connection
    }

    fun disconnect(connection: Connection) {
        callbacks.remove(connection)
    }
}