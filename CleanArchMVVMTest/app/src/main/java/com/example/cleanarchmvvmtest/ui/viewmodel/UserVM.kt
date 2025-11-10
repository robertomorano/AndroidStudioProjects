package com.example.cleanarchmvvmtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.cleanarchmvvmtest.ui.model.User
import com.example.cleanarchmvvmtest.ui.model.UserRepository

class UserVM : ViewModel() {
    private val _repo = UserRepository
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _users.value = _repo.getUsers()
    }
    fun insertUser(user:User){
        _repo.insert(user)
        loadUsers()
    }
}
