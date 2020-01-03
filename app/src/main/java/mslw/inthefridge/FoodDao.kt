package mslw.inthefridge

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {
    @Query("SELECT * from Food ORDER BY expiryDate ASC")  //  NULLS LAST
    fun getStoredFoods(): LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(foodItem: Food)

    @Delete
    suspend fun delete(foodItem: Food)
}
