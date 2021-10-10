package com.example.cafeteria.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.cafeteria.CATEGORY_DATA
import com.example.cafeteria.R
import com.example.cafeteria.activities.ShowDetailsActivity
import com.example.cafeteria.models.ProductResponse
import java.io.Serializable
import java.util.ArrayList

class MealAdapter (val context: Context, private var mealList:List<ProductResponse>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>(){

    inner class MealViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val mealPrice: TextView = view.findViewById(R.id.foodPrice)
        val mealImage : ImageView = view.findViewById(R.id.pic_food)
        val mealName : TextView = view.findViewById(R.id.titleTxt_food)

        val mealAddBtn : ImageView = view.findViewById(R.id.addBtn_food)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealAdapter.MealViewHolder, position: Int) {
        val singleMeal = mealList[position]
        holder.mealName.text = singleMeal.name
        holder.mealPrice.text = singleMeal.price.toString()
        //holder.rating.text = "singleProduct.rate, need to be added by DB"
        holder.mealPrice.text = "${singleMeal.price} LE"
/*
        if(!singleRecommended.inOffers!!){
            holder.offerImage.visibility=View.GONE
        }

 */

        //show loading for glide:
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.mealImage)
            .load(singleMeal.imageUrl)
            .placeholder(circularProgressDrawable)
            .into(holder.mealImage)


        //When press on card item:
        holder.itemView.setOnClickListener {
            //go to page to view list of foods in this menu:
            val intent = Intent(holder.itemView.context, ShowDetailsActivity::class.java)
            intent.putExtra(CATEGORY_DATA, singleMeal as Serializable)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return  mealList.size


    }

    /**
     * For search functionality:
     * */
    fun updateList(filteredList: ArrayList<ProductResponse>) {
        mealList = filteredList
        notifyDataSetChanged()
    }
}