package mslw.inthefridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var foodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        // Get the ViewModel
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        // Fetch id of food item passed by intent
        val idToDisplay = intent.getIntExtra(MainActivity.EXTRA_ID, -1)

        // Prepare handles for the text views
        val foodNameView = findViewById<TextView>(R.id.detail_name_textView)
        val foodDescView = findViewById<TextView>(R.id.detail_desc_textView)
        val foodOpenView = findViewById<TextView>(R.id.detail_opened_textView)
        val foodExpView = findViewById<TextView>(R.id.detail_exp_textView)
        val foodShelfLifeView = findViewById<TextView>(R.id.detail_shelf_life_textView)

        // Create the observer which updates the UI
        val itemObserver = Observer<Food> {item ->
            foodNameView.text = item.name
            foodDescView.text = item.description
            foodOpenView.text = item.openDate.toString()
            foodExpView.text = item.expiryDate?.toString() ?: "n/a"
            foodShelfLifeView.text = item.shelfLife?.toString() ?: "n/a"
        }

        // observe the LiveData containing selected food item
        foodViewModel.selectById(idToDisplay).observe(this, itemObserver)

    }
}
