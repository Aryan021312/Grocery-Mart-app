package com.example.usergrocerymart.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.usergrocerymart.CartListener
import com.example.usergrocerymart.R
import com.example.usergrocerymart.adapters.AdapterProduct
import com.example.usergrocerymart.databinding.FragmentCategoryBinding
import com.example.usergrocerymart.databinding.ItemViewprodBinding
import com.example.usergrocerymart.models.Product
import com.example.usergrocerymart.roomdb.CartProducts
import com.example.usergrocerymart.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import java.lang.ClassCastException


class CategoryFragment : Fragment() {
private lateinit var binding: FragmentCategoryBinding
private val viewModel : UserViewModel by viewModels()
    private lateinit var adapterProduct: AdapterProduct
private  var category : String ? = null
    private var cartListener : CartListener ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCategoryBinding.inflate(layoutInflater)
        setstatusbarcolor()
        getcategoryprod()
        settoolbartitle()
        onsearchmenuclicked()
        fetchcatprod()
        onbackbtnclicked()
        return binding.root
    }

    private fun onbackbtnclicked() {
        binding.tbsearchfrag.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_categoryFragment_to_HomeFragment)
        }
    }

    private fun onsearchmenuclicked() {
        binding.tbsearchfrag.setOnMenuItemClickListener{menuItem->
        when(menuItem.itemId){
            R.id.searchMenu ->{
                findNavController().navigate(R.id.action_categoryFragment_to_searchFragment)
                true
            }
            else ->{
                false
            }
        }

        }
    }

    private fun fetchcatprod() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.getcatprod(category!!).collect{
                if (it.isEmpty()) {
                    binding.rvProducts.visibility = View.GONE
                    binding.tvtext.visibility = View.VISIBLE
                } else {
                    binding.rvProducts.visibility = View.VISIBLE
                    binding.tvtext.visibility = View.GONE
                }
                adapterProduct = AdapterProduct(::onaddbtnclick,::onincrementbtnclick,::ondecreamentbtnclick)
                binding.rvProducts.adapter = adapterProduct
                adapterProduct.differ.submitList(it)
                binding.shimmerViewContainer.visibility = View.GONE



           }
       }

    }

    private fun settoolbartitle() {
binding.tbsearchfrag.title = category
    }


    private fun getcategoryprod() {
val bundle = arguments
 category= bundle?.getString("category")

    }
    private fun setstatusbarcolor(){
        activity?.window?.apply{
            val statusbarcolor= ContextCompat.getColor(requireContext(), R.color.teal)
            statusBarColor=statusbarcolor
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
  private  fun onincrementbtnclick(product: Product,productBinding: ItemViewprodBinding){

        var itemcntinc = productBinding.prodcnt.text.toString().toInt()
        itemcntinc++
        productBinding.prodcnt.text = itemcntinc.toString()
        cartListener?.showcartlayout(1)
      product.itemcount = itemcntinc
      lifecycleScope.launch {
          cartListener?.savingCartItem(1)
          saveprodinroomdb(product)
      }
    }
   private fun ondecreamentbtnclick(product: Product,productBinding: ItemViewprodBinding){

        var itemcntdec = productBinding.prodcnt.text.toString().toInt()
        itemcntdec--
       product.itemcount = itemcntdec
       lifecycleScope.launch {
           cartListener?.savingCartItem(-1)
           saveprodinroomdb(product)
       }
        if(itemcntdec > 0){
        productBinding.prodcnt.text = itemcntdec.toString()
        }
        else{
            lifecycleScope.launch {
                viewModel.deletecartprod(product.prod_id!!)
            }
            productBinding.tvedit.visibility = View.VISIBLE
            productBinding.llprodcnt.visibility = View.GONE
            productBinding.prodcnt.text = "0"

        }
        cartListener?.showcartlayout(-1)

    }
    private fun onaddbtnclick(product: Product,productBinding: ItemViewprodBinding){
        productBinding.tvedit.visibility = View.GONE
        productBinding.llprodcnt.visibility = View.VISIBLE
        var itemcnt = productBinding.prodcnt.text.toString().toInt()
        itemcnt++
        productBinding.prodcnt.text = itemcnt.toString()
        cartListener?.showcartlayout(1)
        product.itemcount = itemcnt
        lifecycleScope.launch {
            cartListener?.savingCartItem(1)
            saveprodinroomdb(product)
        }

    }

    private fun saveprodinroomdb(product: Product) {

        val cartproduct = CartProducts(
            Prod_id = product.prod_id!!,
             Prodtitle = product.Prodtitle,
         Prodcat = product.Prodcat,
         Prodcount = product.itemcount,
         Prod_img = product.Prodimageuris?.get(0)!! ,
         Prodqty = product.Prodqty.toString() + product.Produnit.toString(),
         Prodstock = product.Prodstock,
         Prodprice = "Rs${product.Prodprice}",
         admin_uid  = product.admin_uid,

        )
        lifecycleScope.launch {
            viewModel.insertcartprod(cartproduct)

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CartListener){
            cartListener = context
        }
        else{
            throw ClassCastException("Please Implement Cart Listener")
        }
    }
}