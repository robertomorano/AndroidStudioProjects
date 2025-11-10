package com.example.cleanarchmvvmtest.ui.model

object UserRepository {
    val list = mutableListOf<User>(
        User(1, "ubai"),
        User(2, "daso"),
        User(3, "cinco"),
        User(4, "wdwwa"),
        User(5, "adw"),

    )
    fun getUsers(): List<User> {
        return list.toList()
    }

    fun insert(user:User) { list.add(user) }
}