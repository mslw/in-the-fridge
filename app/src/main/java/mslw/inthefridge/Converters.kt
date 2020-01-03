package mslw.inthefridge

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time  // returns Long
    }

    @TypeConverter
    fun pairFromString(value: String?): Pair<Int, Int>? {
        val newPair: Pair<Int, Int>?

        if (value == null) {
            newPair = null
        } else {
            val elements = value.removeSurrounding("(", ")").split(", ")
            newPair = Pair(elements[0].toInt(), elements[1].toInt())
        }

        return newPair
    }

    @TypeConverter
    fun intPairToString(pair: Pair<Int, Int>?): String? {
        return pair?.toString()
    }


}