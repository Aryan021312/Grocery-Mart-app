package com.example.usergrocerymart.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.usergrocerymart.CartListener
import com.example.usergrocerymart.R
import com.example.usergrocerymart.databinding.ActivityUserMainBinding
import com.example.usergrocerymart.viewmodels.UserViewModel

class UserMainActivity : AppCompatActivity() , CartListener{
    private lateinit var binding: ActivityUserMainBinding
    private val viewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
gettotalitemcntincart()
    }

    private fun gettotalitemcntincart() {
        viewModel.fetchcartitem().observe(this){
           if(it > 0){
               binding.llcart.visibility = View.VISIBLE
               binding.tvnoofprodcnt.text = it.toString()
           }
            else{
               binding.llcart.visibility = View.GONE
           }
        }

    }

    override fun showcartlayout(itemcnt:Int) {
val prevcnt = binding.tvnoofprodcnt.text.toString().toInt()
        val updatedcnt = prevcnt + itemcnt
        if(updatedcnt > 0){
            binding.llcart.visibility = View.VISIBLE
            binding.tvnoofprodcnt.text = updatedcnt.toString()

        }
        else{
            binding.llcart.visibility = View.GONE
            binding.tvnoofprodcnt.text = "0"
        }
    }

    override fun savingCartItem(itemcnt: Int) {
      viewModel.fetchcartitem().observe(this){
          viewModel.savingCartItemcnt(it+itemcnt)
      }

    }
}