package com.example.usergrocerymart

interface CartListener {
    fun showcartlayout(itemcnt:Int)
    fun savingCartItem(itemcnt: Int)
}