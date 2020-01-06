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

    @Query("DELETE FROM Food")
    suspend fun wipe()

    @Query("SELECT * FROM Food WHERE id = :id LIMIT 1")
    fun selectById(id: Int): LiveData<Food>

}
