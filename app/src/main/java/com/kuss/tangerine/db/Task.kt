package com.kuss.tangerine.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    val name: String,
    val type: Int,
    var checked: Boolean,
    val date: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
