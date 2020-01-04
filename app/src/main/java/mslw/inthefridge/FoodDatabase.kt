package mslw.inthefridge

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

@Database(entities = arrayOf(Food::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao

    private class FoodDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        // populate with some items - for development
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var foodDao = database.foodDao()

                    // Delete all content
                    foodDao.wipe()

                    // Fake some dates
                    val fakeOpen = Date(1578090519315)
                    val fakeExp = Date(1578133719315)

                    // Add an item
                    var food = Food(
                        name = "Juice",
                        description = "Orange",
                        openDate = fakeOpen,
                        expiryDate = fakeExp,
                        shelfLife = Pair(0, 12)
                    )
                    foodDao.insert(food)

                    // Add another item
                    food = Food(
                        name = "Ketchup",
                        description = "",
                        openDate = fakeOpen,
                        expiryDate = null,
                        shelfLife = null
                    )
                    foodDao.insert(food)
                }
            }
        }

    }

    companion object {
        // singleton
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FoodDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                ).addCallback(FoodDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
