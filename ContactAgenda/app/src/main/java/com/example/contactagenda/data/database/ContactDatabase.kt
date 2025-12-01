package com.example.contactagenda.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactagenda.data.dao.ContactDao
import com.example.contactagenda.domain.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase(){
    abstract fun ContactDao(): ContactDao
}