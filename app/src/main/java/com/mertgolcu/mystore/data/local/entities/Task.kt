package com.mertgolcu.mystore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mertgolcu.mystore.data.utils.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author mertgolcu
 * @since 16.10.2023
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun formattedCreatedAt(): String {
        return createdAt.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
    }

    fun formattedUpdatedAt(): String {
        return updatedAt.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
    }
}
