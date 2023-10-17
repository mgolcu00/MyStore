package com.mertgolcu.mystore.data.utils

/**
 * @author mertgolcu
 * @since 16.10.2023
 */

import androidx.room.TypeConverter
import java.time.LocalDateTime


class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}