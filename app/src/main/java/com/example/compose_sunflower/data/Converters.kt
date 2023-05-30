package com.example.compose_sunflower.data

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datastampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

}