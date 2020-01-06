package mslw.inthefridge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): AndroidViewModel(application) {

    private val repository: FoodRepository  // reference to repository
    val storedFoods: LiveData<List<Food>>

    init {
        val foodsDao = FoodDatabase.getDatabase(application, viewModelScope).foodDao()
        repository = FoodRepository(foodsDao)
        storedFoods = repository.storedFoods
    }

    // wrappers to encapsulate from UI, using launch to avoid blocking main thread
    fun insert(food: Food) = viewModelScope.launch { repository.insert(food) }
    fun delete(food: Food) = viewModelScope.launch { repository.delete(food) }

    // method to query for single item (return LiveData so it can be used in main thread)
    fun selectById(id: Int): LiveData<Food> = repository.selectById(id)
}