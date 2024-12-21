package com.example.usergrocerymart.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartProducts")
data class CartProducts(
    @PrimaryKey
    val Prod_id : String = "random",

    val Prodtitle : String ?= null,
    var Prodcat : String ?= null,
    var Prodcount : Int ?= null,
    var Prod_img : String ?= null,
    val Prodqty : String ?= null,
    var Prodstock : Int ?= null,
    val Prodprice : String ?= null,
    var admin_uid  : String ?= null,
)