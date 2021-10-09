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
import com.example.cafeteria.activities.ShowDetailsActivity
import com.example.cafeteria.R
import com.example.cafeteria.models.ProductResponse
import java.io.Serializable
import java.util.ArrayList

class RecommendedAdapter(val context: Context, private var recommendedList:List<ProductResponse>) : RecyclerView.Adapter<RecommendedAdapter.RecommendViewHolder>(){

    inner class RecommendViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val price:TextView = view.findViewById(R.id.price)
        val recommendedImage :ImageView = view.findViewById(R.id.pic)
        val recommendedName : TextView = view.findViewById(R.id.title)

        val addBtn : ImageView = view.findViewById(R.id.addBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_recommended, parent, false)
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val singleRecommended = recommendedList[position]
        holder.recommendedName.text = singleRecommended.name
        holder.price.text = singleRecommended.price.toString()
        //holder.rating.text = "singleProduct.rate, need to be added by DB"
        holder.price.text = "${singleRecommended.price}+ LE"
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

        Glide.with(holder.recommendedImage)
            .load(singleRecommended.imageUrl)
            .placeholder(circularProgressDrawable)
            .into(holder.recommendedImage)


        //When press on card item:
        holder.itemView.setOnClickListener {
            //go to page to view list of foods in this menu:
            val intent = Intent(holder.itemView.context, ShowDetailsActivity::class.java)
            intent.putExtra(CATEGORY_DATA, singleRecommended as Serializable)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return  recommendedList.size


    }

    /**
     * For search functionality:
     * */
    fun updateList(filteredList: ArrayList<ProductResponse>) {
        recommendedList = filteredList
        notifyDataSetChanged()
    }

}