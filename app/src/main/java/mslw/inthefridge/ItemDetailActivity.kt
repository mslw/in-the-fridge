package mslw.inthefridge

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

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

        // Prepare handle for delete button
        val deleteButton = findViewById<ImageButton>(R.id.detail_delete_button)

        // Create a simple date formatter - uses locale by default
        val fmt = SimpleDateFormat()

        // Create the observer which updates the UI
        val itemObserver = Observer<Food> {item ->
            foodNameView.text = item.name
            foodDescView.text = item.description
            foodOpenView.text = getString(R.string.detail_open_date, fmt.format(item.openDate))
            foodExpView.text = when (item.expiryDate) {
                null -> getString(R.string.detail_expire_date_miss)
                else -> getString(R.string.detail_expire_date, fmt.format(item.expiryDate))
            }
            foodShelfLifeView.text = when (item.shelfLife) {
                null -> getString(R.string.detail_shelf_life_miss)
                else -> getString(R.string.detail_shelf_life,
                    resources.getQuantityString(
                        R.plurals.plural_days, item.shelfLife.first, item.shelfLife.first),
                    resources.getQuantityString(
                        R.plurals.plural_hours, item.shelfLife.second, item.shelfLife.second)
                    )
            }
        }

        // observe the LiveData containing selected food item
        val selected = foodViewModel.selectById(idToDisplay)
        selected.observe(this, itemObserver)

        // Prepare an alert dialog which handles deletion confirmation
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.dialog_delete_question)
        dialogBuilder.setPositiveButton(R.string.dialog_yes) { dialog, _ ->
            dialog.dismiss()
            deleteAndFinish(selected)
        }
        dialogBuilder.setNegativeButton(R.string.dialog_no) { dialog, _ ->  dialog.dismiss()}

        // Hook up the delete button with the deletion dialog
        val delAlertDialog = dialogBuilder.create()
        deleteButton.setOnClickListener {
            delAlertDialog.show()
        }

    }

    private fun deleteAndFinish(selectedItem: LiveData<Food>){
        selectedItem.removeObservers(this)  // don't wanna observe deleted item
        foodViewModel.delete(selectedItem.value!!)
        finish()
    }
}
