package com.example.cafeteria.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.cafeteria.CATEGORY_DATA
import com.example.cafeteria.activities.ShowDetailsActivity
import com.example.cafeteria.R
import com.example.cafeteria.models.CategoryResponse
import java.io.Serializable
import java.util.ArrayList

class CategoryAdapter(val context: Context, private var categoryList: List<CategoryResponse>) :
    RecyclerView.Adapter<CategoryAdapter.MealsviewHolder>(){

    inner class  MealsviewHolder(view: View):RecyclerView.ViewHolder(view){
        val categoryImage: ImageView = view.findViewById(R.id.categoryPic)
        val categoryName: TextView = view.findViewById(R.id.categoryName)
       // val description : TextView = view.findViewById(R.id.descriptionTxt)
        //val rating : TextView = view.findViewById(R.id.starTxt)
        val mainLayout:ConstraintLayout= view.findViewById(R.id.mainLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meals_item, parent, false)
        return MealsviewHolder(view)
    }

    override fun onBindViewHolder(holder: MealsviewHolder, position: Int) {
        val singleCategory = categoryList[position]
        holder.categoryName.text = singleCategory.name

        //show loading for glide:
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.categoryImage)
            .load(singleCategory.imageUrl)
            .placeholder(circularProgressDrawable)
            .into(holder.categoryImage)


        //When press on card item:
        holder.itemView.setOnClickListener {
            //go to page to view list of foods in this menu:
            val intent = Intent(holder.itemView.context, ShowDetailsActivity::class.java)
            intent.putExtra(CATEGORY_DATA, singleCategory as Serializable)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    /**
     * For search functionality:
     * Added here to access main list which works on it
     * */
    fun updateList(filteredList: ArrayList<CategoryResponse>) {
        //ADD it to the Meals Fragment, like food list
        categoryList = filteredList
        notifyDataSetChanged()
    }
}