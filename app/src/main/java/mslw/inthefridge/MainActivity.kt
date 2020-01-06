package mslw.inthefridge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val newFoodActivityRequestCode = 1
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = Intent(this@MainActivity, NewFoodActivity::class.java)
            startActivityForResult(intent, newFoodActivityRequestCode)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FoodListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        foodViewModel.storedFoods.observe(this, Observer { words ->
            // update the cached copy of foods in the adapter
            words?.let { adapter.setFoods(it)}
        })

        // supply a click listener to be used with all items (foodItemClicked defined below)
        adapter.clickListener = {foodItem: Food -> foodItemClicked(foodItem)}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra(NewFoodActivity.EXTRA_NAME)
            val description = data?.getStringExtra(NewFoodActivity.EXTRA_DESCRIPTION)
            val days = data?.getIntExtra(NewFoodActivity.EXTRA_DAYS, 0)
            val hours = data?.getIntExtra(NewFoodActivity.EXTRA_HOURS, 0)

            if (name != null && description != null && days != null && hours != null) {
                // alternatively could use non-null assertion !! above

                val openDate = Calendar.getInstance()

                val expDate: Calendar?
                val shelfLife: Pair<Int, Int>?
                if (days > 0 || hours > 0){
                    expDate = Calendar.getInstance()  // it's milliseconds later but don't care
                    expDate.add(Calendar.DAY_OF_MONTH, days)
                    expDate.add(Calendar.HOUR_OF_DAY, hours)
                    shelfLife = Pair(days, hours)
                } else {
                    expDate = null
                    shelfLife = null
                }

                // note that calendar.time returns Date
                val food = Food(name, description, openDate.time, expDate?.time, shelfLife)
                foodViewModel.insert(food)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun foodItemClicked(foodItem: Food){

        val intent = Intent(this@MainActivity, ItemDetailActivity::class.java)
        intent.putExtra(EXTRA_ID, foodItem.id)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_ID = "mslw.inthefridge.foodlistsql.ID"
    }
}
