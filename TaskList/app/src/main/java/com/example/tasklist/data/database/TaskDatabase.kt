package com.example.tasklist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasklist.data.dao.TaskDao
import com.example.tasklist.domain.entities.TaskEntity

    @Database(entities = [TaskEntity::class], version= 1)
    abstract class TaskDatabase : RoomDatabase() {
        abstract fun taskDao(): TaskDao
    }