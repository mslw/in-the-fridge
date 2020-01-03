package mslw.inthefridge

import androidx.lifecycle.LiveData

class FoodRepository(private val foodDao: FoodDao) {
    val storedFoods: LiveData<List<Food>> = foodDao.getStoredFoods()

    suspend fun insert(food: Food) {
        foodDao.insert(food)
    }

    suspend fun delete(food: Food) {
        foodDao.delete(food)
    }
}
