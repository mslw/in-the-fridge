package mslw.inthefridge

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_new_food.*

class NewFoodActivity : AppCompatActivity() {

    private lateinit var editFoodNameView: EditText
    private lateinit var editFoodDescriptionView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)

        editFoodNameView = findViewById(R.id.food_name)
        editFoodDescriptionView = findViewById(R.id.food_description)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editFoodNameView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editFoodNameView.text.toString()
                val description = editFoodDescriptionView.text.toString()
                replyIntent.putExtra(EXTRA_NAME, name)
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "mslw.inthefridge.foodlistsql.NAME"
        const val EXTRA_DESCRIPTION = "mslw.inthefridge.foodlistsql.DESCRIPTION"
    }
}
