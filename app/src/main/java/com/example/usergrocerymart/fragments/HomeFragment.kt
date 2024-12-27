package com.example.usergrocerymart.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.usergrocerymart.Constants
import com.example.usergrocerymart.R
import com.example.usergrocerymart.adapters.Adaptercat
import com.example.usergrocerymart.databinding.FragmentHomeBinding
import com.example.usergrocerymart.models.Category
import com.example.usergrocerymart.viewmodels.UserViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        setstatusbarcolor()
        navtosearch()
        get()
        setallcategory()
        return binding.root
    }



    private fun navtosearch() {
        binding.searchCV.setOnClickListener{
            findNavController().navigate(R.id.action_HomeFragment_to_searchFragment)
        }
    }

    private fun setallcategory() {
        val prodlist=ArrayList<Category>()
        for(i in 0  until Constants.allprodcaticon.size)
        {
            prodlist.add(Category(Constants.allprodcat[i], Constants.allprodcaticon[i]))
        }
        binding.categories.adapter=Adaptercat(prodlist , ::oncategoryiconclicked)
    }

   fun oncategoryiconclicked(category: Category) {
        val bundle = Bundle()
        bundle.putString("category", category.title)
        findNavController().navigate(R.id.action_HomeFragment_to_categoryFragment, bundle)
    }
private fun get(){
    viewModel.getall().observe(viewLifecycleOwner){
        for(i in it){
            Log.d("vvv" , i.Prodtitle.toString())
            Log.d("vvv" , i.Prodcount.toString())

        }
    }
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

}