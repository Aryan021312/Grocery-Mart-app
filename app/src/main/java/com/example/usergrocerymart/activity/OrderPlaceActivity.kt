package com.example.usergrocerymart.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.usergrocerymart.R
import com.example.usergrocerymart.adapters.AdapterCartproducts
import com.example.usergrocerymart.databinding.ActivityOrderPlaceBinding
import com.example.usergrocerymart.databinding.AddressLayoutBinding
import com.example.usergrocerymart.mesg
import com.example.usergrocerymart.models.Users
import com.example.usergrocerymart.viewmodels.UserViewModel
import kotlinx.coroutines.launch

class OrderPlaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderPlaceBinding
    private lateinit var adapterCartproducts: AdapterCartproducts
    private val viewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityOrderPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getallcartprod()
        setstatusbarcolor()
        backtousrmainactivity()
        onplaceorderclicked()



    }

    private fun onplaceorderclicked() {
        binding.btnnext.setOnClickListener{
            viewModel.getaddressstatus().observe(this){status ->

                if(status)
                {
                    //if true

                }
                else{
                    val addressLayoutBinding  = AddressLayoutBinding.inflate(LayoutInflater.from(this))
                    val alertDialog = AlertDialog.Builder(this)
                        .setView(addressLayoutBinding.root)
                        .create()
                        alertDialog.show()
                    addressLayoutBinding.btnAdd.setOnClickListener{
                        saveAddressstatus(alertDialog,addressLayoutBinding)
                    }

                }
            }

        }


    }

    private fun saveAddressstatus(alertDialog: AlertDialog, addressLayoutBinding: AddressLayoutBinding) {
mesg.showdialog(this,"Processing...")
        val userPIN = addressLayoutBinding.textfeildPINCODE.toString()
        val userPhoneno = addressLayoutBinding.textfeildPhoneNo.toString()
        val userState = addressLayoutBinding.textfeildState.toString()
        val userDistrict = addressLayoutBinding.textfeilddistrict.toString()
        val userAddress = addressLayoutBinding.textfeildTole.toString()

        val Address = "$userPIN,$userDistrict($userState),$userPhoneno,$userAddress"
        val users = Users(
            userAddress = Address
        )
        lifecycleScope.launch {
            viewModel.saveuserAddress(users)
            viewModel.saveAddressstatus()
        }
        mesg.showtoast(this,"Saved...")
        alertDialog.dismiss()
        mesg.hidedialog()
    }

    private fun backtousrmainactivity() {
        binding.tborderfrag.setNavigationOnClickListener{
            startActivity(Intent(this,UserMainActivity::class.java))
            finish()
        }
    }

    private fun getallcartprod() {
        viewModel.getall().observe(this){cartProductsList->


adapterCartproducts = AdapterCartproducts()
            binding.rvproditems.adapter = adapterCartproducts
            adapterCartproducts.differ.submitList(cartProductsList)
            var totalprice = 0

            for(product in cartProductsList){
                val price = product.Prodprice?.substring(2)?.toInt()
                val itemcnt = product.Prodcount!!
                totalprice += (price?.times(itemcnt)!!)
            }
            binding.tvsubtotal.text = totalprice.toString()
            if(totalprice<150){
                binding.deliverychargetv.text = "Rs80"
                totalprice+=80
            }
            binding.tvgrandtotal.text = totalprice.toString()



        }
    }
    private fun setstatusbarcolor(){
        window?.apply{
            val statusbarcolor= ContextCompat.getColor(this@OrderPlaceActivity, R.color.teal)
            statusBarColor=statusbarcolor
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}