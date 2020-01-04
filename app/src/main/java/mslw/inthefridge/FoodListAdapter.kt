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
        val foodItemDescriptionView: TextView = itemView.findViewById(R.id.textView2)
        val foodItemOpenDateView: TextView = itemView.findViewById(R.id.textView3)
        val foodItemExpireView: TextView = itemView.findViewById(R.id.textView4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = foods[position]
        holder.foodItemView.text = current.name
        holder.foodItemDescriptionView.text = current.description

        // TODO: format dates, for example using method below
        // val fmt = SimpleDateFormat()
        // val fdate = fmt.format(current.openDate)
        holder.foodItemOpenDateView.text = current.openDate.toString()
        holder.foodItemExpireView.text = current.expiryDate?.toString() ?: ""
    }

    internal fun setFoods(foods: List<Food>){
        this.foods = foods
        notifyDataSetChanged()
    }

    override fun getItemCount() = foods.size
}