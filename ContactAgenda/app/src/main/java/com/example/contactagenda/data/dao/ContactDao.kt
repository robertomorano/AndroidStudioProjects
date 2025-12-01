package com.example.contactagenda.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contactagenda.domain.entities.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    suspend fun getAllContact(): List<Contact>

    @Query("SELECT * FROM contact where id = :id")
    suspend fun getContactById(id: Long): Contact

    @Insert
    suspend fun addContact(contact: Contact):Long

    @Update
    suspend fun  updateContact(contact : Contact):Int

    @Delete
    suspend fun deleteContact(contact: Contact):Int
}