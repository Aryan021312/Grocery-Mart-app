package com.example.usergrocerymart

import android.widget.Filter
import com.example.usergrocerymart.adapters.AdapterProduct
import com.example.usergrocerymart.models.Product

import java.util.Locale

class FilteringProducts(
    val adapter : AdapterProduct,
    val filter : ArrayList<Product>
) : Filter(){
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val result = FilterResults()
        if (!constraint.isNullOrEmpty()) {
            val filteredList = ArrayList<Product>()
            val query = constraint.toString().trim().uppercase(Locale.getDefault())
            for (product in filter) {
                if (product.Prodtitle?.uppercase(Locale.getDefault())?.contains(query) == true ||
                    product.Prodcat?.uppercase(Locale.getDefault())?.contains(query) == true ||
                    product.Prodprice?.toString()?.contains(query) == true ||
                    product.Prodtype?.uppercase(Locale.getDefault())?.contains(query) == true
                ) {
                    filteredList.add(product)
                }
            }
            result.values = filteredList
            result.count = filteredList.size
        } else {
            result.values = filter
            result.count = filter.size
        }
        return result
    }

    override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
        adapter.differ.submitList(result?.values as ArrayList<Product>)
    }

}