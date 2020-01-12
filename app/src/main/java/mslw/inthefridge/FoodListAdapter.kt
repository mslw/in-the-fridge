package mslw.inthefridge

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant

class FoodListAdapter internal constructor(context: Context):
    RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foods = emptyList<Food>() // cached copy of foods
    lateinit var clickListener: (Food) -> Unit

    inner class FoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodItemView: TextView = itemView.findViewById(R.id.textView1)
        val foodItemDescriptionView: TextView = itemView.findViewById(R.id.textView2)
        val foodItemIntervalView: TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = foods[position]
        val timeNow = Instant.now().toEpochMilli()

        holder.foodItemView.text = current.name
        holder.foodItemDescriptionView.text = current.description
        holder.foodItemIntervalView.text = if (current.expiryDate != null)
            DateUtils.getRelativeTimeSpanString(
                current.expiryDate.time,
                timeNow,
                DateUtils.HOUR_IN_MILLIS
            ) else "-"

        if (this::clickListener.isInitialized) {
            holder.itemView.setOnClickListener { clickListener(current) }
        }
    }

    internal fun setFoods(foods: List<Food>){
        this.foods = foods
        notifyDataSetChanged()
    }

    override fun getItemCount() = foods.size
}