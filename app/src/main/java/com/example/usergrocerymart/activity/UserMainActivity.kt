package com.example.usergrocerymart.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.usergrocerymart.CartListener
import com.example.usergrocerymart.R
import com.example.usergrocerymart.adapters.AdapterCartproducts
import com.example.usergrocerymart.databinding.ActivityUserMainBinding
import com.example.usergrocerymart.databinding.BsCartprodBinding
import com.example.usergrocerymart.roomdb.CartProducts
import com.example.usergrocerymart.viewmodels.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class UserMainActivity : AppCompatActivity() , CartListener{
    private lateinit var binding: ActivityUserMainBinding
    private val viewModel : UserViewModel by viewModels()
    private lateinit var cartProductsList : List<CartProducts>
    private lateinit var adapterCartproducts: AdapterCartproducts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getallcartprod()
gettotalitemcntincart()
        oncartclicked()
       onnextbtnclick()

    }

    private fun onnextbtnclick() {
        binding.btnnext.setOnClickListener{
            startActivity(Intent(this,OrderPlaceActivity::class.java))

        }
    }

    private fun getallcartprod(){
        viewModel.getall().observe(this){

cartProductsList = it


        }
    }

   override  fun oncartclicked() {
binding.llitemcart.setOnClickListener{
val bsCartprodBinding = BsCartprodBinding.inflate(LayoutInflater.from(this))
    val bs = BottomSheetDialog(this)
    bs.setContentView(bsCartprodBinding.root)
    bsCartprodBinding.tvnoofprodcnt.text = binding.tvnoofprodcnt.text
    bsCartprodBinding.btnnext.setOnClickListener{
        startActivity(Intent(this,OrderPlaceActivity::class.java))

    }
    adapterCartproducts = AdapterCartproducts()
    bsCartprodBinding.rvproditemsbs.adapter = adapterCartproducts
    adapterCartproducts.differ.submitList(cartProductsList)
    bs.show()
}
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