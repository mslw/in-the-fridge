package mslw.inthefridge

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Food (
    val name: String,
    val description: String,  // nullable? or empty string?
    val openDate: Date,
    val expiryDate: Date?,
    val shelfLife: Pair<Int, Int>?)
{
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}

// consider adding:    val shelfLife: Pair

