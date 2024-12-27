package com.example.usergrocerymart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.usergrocerymart.databinding.ItemViewcartprodBinding
import com.example.usergrocerymart.databinding.ItemViewprodBinding
import com.example.usergrocerymart.models.Product
import com.example.usergrocerymart.roomdb.CartProducts
import com.example.usergrocerymart.roomdb.CartProductsdb

class AdapterCartproducts : RecyclerView.Adapter<AdapterCartproducts.CartproductsViewHolder>() {
    class CartproductsViewHolder(val binding: ItemViewcartprodBinding) : ViewHolder(binding.root)

    val diffUtil = object : DiffUtil .ItemCallback<CartProducts>(){
        override fun areItemsTheSame(oldItem: CartProducts, newItem: CartProducts): Boolean {
            return oldItem.Prod_id == newItem.Prod_id
        }

        override fun areContentsTheSame(oldItem: CartProducts, newItem: CartProducts): Boolean {
           return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartproductsViewHolder {
       return CartproductsViewHolder(ItemViewcartprodBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartproductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(product.Prod_img).into(ivprodimage)
            tvprodtitle.text = product.Prodtitle
            tvprodqty.text = product.Prodqty
            tvprodprice.text = product.Prodprice
            tvnoofprodcnt.text = product.Prodcount.toString()


        }
    }

}