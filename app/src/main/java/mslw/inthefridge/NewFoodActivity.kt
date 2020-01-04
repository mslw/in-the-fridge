package mslw.inthefridge

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker

class NewFoodActivity : AppCompatActivity() {

    private lateinit var editFoodNameView: EditText
    private lateinit var editFoodDescriptionView: EditText
    private lateinit var dayPicker: NumberPicker
    private lateinit var hoursPicker: NumberPicker
    private val hoursToChoose = intArrayOf(0, 12, 24, 48, 72)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)

        editFoodNameView = findViewById(R.id.food_name)
        editFoodDescriptionView = findViewById(R.id.food_description)
        dayPicker = findViewById(R.id.dayNumberPicker)
        hoursPicker = findViewById(R.id.hoursNumberPicker)

        setUpPickers()

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editFoodNameView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editFoodNameView.text.toString()
                val description = editFoodDescriptionView.text.toString()
                val days = dayPicker.value
                val hours = hoursToChoose[hoursPicker.value]

                replyIntent.putExtra(EXTRA_NAME, name)
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)
                replyIntent.putExtra(EXTRA_DAYS, days)
                replyIntent.putExtra(EXTRA_HOURS, hours)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun setUpPickers(){
//        val hoursToChoose = intArrayOf(12, 24, 48, 72)
//        val hoursToChoose = intArrayOf(0, 12, 24, 48, 72)

        dayPicker.minValue = 0
        dayPicker.maxValue = 60

        hoursPicker.minValue = 0
        hoursPicker.maxValue = hoursToChoose.size - 1
        hoursPicker.displayedValues = hoursToChoose.map { it.toString() }.toTypedArray()
    }

    companion object {
        const val EXTRA_NAME = "mslw.inthefridge.foodlistsql.NAME"
        const val EXTRA_DESCRIPTION = "mslw.inthefridge.foodlistsql.DESCRIPTION"
        const val EXTRA_DAYS = "mslw.inthefridge.foodlistsql.DAYS"
        const val EXTRA_HOURS = "mslw.inthefridge.foodlistsql.HOURS"
    }
}
