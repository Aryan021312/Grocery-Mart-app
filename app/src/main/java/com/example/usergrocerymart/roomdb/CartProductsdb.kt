package com.example.usergrocerymart.roomdb

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = [CartProducts::class] , version = 1, exportSchema = false)
abstract  class CartProductsdb : RoomDatabase() {
    abstract fun dao() : Dao
    companion object{
        @Volatile
        var  INSTANCE :CartProductsdb ?= null
        fun getdbinstance(context: Context):CartProductsdb{
            val tempinstance = INSTANCE
            if(tempinstance != null) return tempinstance
            synchronized(this){
                val roomdb =  Room.databaseBuilder(context,CartProductsdb::class.java,"CartProducts").allowMainThreadQueries().build()
                INSTANCE = roomdb
                return roomdb
            }

        }
    }
}