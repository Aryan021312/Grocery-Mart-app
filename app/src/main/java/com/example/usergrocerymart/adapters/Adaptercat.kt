package com.example.usergrocerymart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.usergrocerymart.databinding.ProductCategoryBinding
import com.example.usergrocerymart.models.Category

class Adaptercat(
    val catelogue: ArrayList<Category>,
    val oncategoryiconclicked: (Category) -> Unit
) :RecyclerView.Adapter<Adaptercat.CategoryViewHolder>(){
    class CategoryViewHolder(val binding:ProductCategoryBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ProductCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return catelogue.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category=catelogue[position]
        holder.binding.apply {
            productcatimg.setImageResource(category.image)
            producttitle.text=category.title
        }
        holder.itemView.setOnClickListener{
            oncategoryiconclicked(category)
        }
    }


}