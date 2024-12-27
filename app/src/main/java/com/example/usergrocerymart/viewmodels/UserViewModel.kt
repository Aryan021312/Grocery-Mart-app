package com.example.usergrocerymart.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usergrocerymart.models.Product
import com.example.usergrocerymart.roomdb.CartProducts
import com.example.usergrocerymart.roomdb.CartProductsdb
import com.example.usergrocerymart.roomdb.Dao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserViewModel(application: Application): AndroidViewModel(application) {
   //Initialization
    val sharedPreference : SharedPreferences = application.getSharedPreferences("MY_PREF" , MODE_PRIVATE)
    val dao : Dao = CartProductsdb.getdbinstance(application).dao()
    //Room DB
    suspend fun insertcartprod(products: CartProducts){
        dao.insertcartprod(products)

    }
    suspend fun updatecartprod(products: CartProducts){
        dao.updatecartprod(products)
    }
    suspend  fun deletecartprod(productId : String){
        dao.deletecartprod(productId)
    }

    fun getall() : LiveData<List<CartProducts>>{
    return dao.getallcartprod()
    }

    //Firebase Call
    fun fetchallprod(): Flow<List<Product>> = callbackFlow {
        val db =  FirebaseDatabase.getInstance().getReference("Admin").child("All Products")
        val eventlistener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = ArrayList<Product>()
                for(product in snapshot.children){
                    val prod = product.getValue(Product::class.java)
                    products.add(prod!!)


                }
                trySend(products)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        db.addValueEventListener(eventlistener)
        awaitClose{db.removeEventListener(eventlistener)}
    }
    fun getcatprod(category : String):Flow<List<Product>> = callbackFlow  {

        val db =  FirebaseDatabase.getInstance().getReference("Admin").child("ProductCategory/${category}")
        val eventListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = ArrayList<Product>()
                for(product in snapshot.children){
                    val prod = product.getValue(Product::class.java)
                    products.add(prod!!)


                }
                trySend(products)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        db.addValueEventListener(eventListener)
        awaitClose{
            db.removeEventListener(eventListener)
        }

    }
    fun updateitemcnt(product: Product,itemCount: Int){

        FirebaseDatabase.getInstance().getReference("Admin").child("All Products/${product.prod_id}").child("itemcount").setValue(itemCount)
                FirebaseDatabase.getInstance().getReference("Admin").child("ProductCategory/${product.Prodcat}/${product.prod_id}").child("itemcount").setValue(itemCount)

                        FirebaseDatabase.getInstance().getReference("Admin").child("ProductType/${product.Prodtype}/${product.prod_id}").child("itemcount").setValue(itemCount)


    }
    //Shared Preferences
    fun savingCartItemcnt(itemCount : Int){
        sharedPreference.edit().putInt("itemCount",itemCount).apply()
    }
    fun fetchcartitem () : MutableLiveData<Int>{
        val totalitemcnt = MutableLiveData<Int>()
        totalitemcnt.value = sharedPreference.getInt("itemCount", 0)
        return totalitemcnt
    }
}