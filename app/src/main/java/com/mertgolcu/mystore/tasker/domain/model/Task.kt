package com.mertgolcu.mystore.tasker.domain.model

import java.util.Date

/**
 * @author mertgolcu
 * @since 27.09.2023
 */
@Deprecated("This class is deprecated")
data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: Date,
) {
    fun toggleCompleted() = copy(isCompleted = !isCompleted)

    fun updateCompleted(newCompleted: Boolean) = copy(isCompleted = newCompleted)

    fun updateTitle(newTitle: String) = copy(title = newTitle)

    fun updateDescription(newDescription: String) = copy(description = newDescription)

    fun updateCreatedAt(newCreatedAt: Date) = copy(createdAt = newCreatedAt)

    fun getFormattedCreatedAt(): String? = // result must be -> 27.09.2023
        try {
            val formatter = java.text.SimpleDateFormat(DATE_FORMAT)
            formatter.format(createdAt)
        } catch (e: Exception) {
            null
        }

    fun setDateFromFormattedString(dateString: String): Task? = // dateString must be -> 27.09.2023
        try {
            val formatter = java.text.SimpleDateFormat(DATE_FORMAT)
            formatter.parse(dateString)
            copy(createdAt = formatter.parse(dateString))
        } catch (e: Exception) {
            null
        }

    companion object {
        const val DATE_FORMAT = "dd.MM.yyyy"
        fun create(
            id: Int = 0,
            title: String,
            description: String? = null
        ) = Task(
            id = id,
            title = title,
            description = description,
            isCompleted = false,
            createdAt = Date()
        )
    }
}
