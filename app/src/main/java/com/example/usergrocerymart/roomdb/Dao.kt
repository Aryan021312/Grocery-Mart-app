package com.example.usergrocerymart.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertcartprod(products: CartProducts)
    @Update
    fun updatecartprod(products: CartProducts)
    @Query("SELECT * FROM CartProducts ")
    fun getallcartprod() : LiveData<List<CartProducts>>
    @Query("Delete FROM CartProducts WHERE Prod_id = :productId")
    fun deletecartprod(productId : String)
}