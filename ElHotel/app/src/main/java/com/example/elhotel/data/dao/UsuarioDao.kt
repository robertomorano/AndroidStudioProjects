package com.example.elhotel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.elhotel.domain.entities.User

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM user_entity")
    suspend fun getAllUser(): List<User>

    @Query("SELECT * FROM user_entity where id = :id")
    suspend fun getUserById(id: Long): User

    @Query("SELECT * FROM user_entity WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert
    suspend fun addUser(user: User):Long

    @Update
    suspend fun  updateUser(user : User):Int

    @Delete
    suspend fun deleteUser(user : User):Int
}