package com.example.tasklist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasklist.domain.entities.TaskEntity

@Dao
interface TaskDao {
        @Query("SELECT * FROM task_entity")
        suspend fun getAllTask(): List<TaskEntity>

        @Query("SELECT * FROM task_entity where id = :id")
        suspend fun getTaskById(id: Long): TaskEntity

        @Insert
        suspend fun addTask(taskEntity: TaskEntity):Long

        @Update
        suspend fun  updateTask(task : TaskEntity):Int

        @Delete
        suspend fun deleteTask(task : TaskEntity):Int
}