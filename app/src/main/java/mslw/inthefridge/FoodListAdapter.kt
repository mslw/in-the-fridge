package mslw.inthefridge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodListAdapter internal constructor(context: Context):
    RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foods = emptyList<Food>() // cached copy of foods

    inner class FoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodItemView: TextView = itemView.findViewById(R.id.textView1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = foods[position]
        holder.foodItemView.text = current.name
    }

    internal fun setFoods(foods: List<Food>){
        this.foods = foods
        notifyDataSetChanged()
    }

    override fun getItemCount() = foods.size
}