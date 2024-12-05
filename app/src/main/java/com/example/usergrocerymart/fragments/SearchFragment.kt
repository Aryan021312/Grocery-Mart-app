package com.example.usergrocerymart.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.usergrocerymart.R
import com.example.usergrocerymart.adapters.AdapterProduct
import com.example.usergrocerymart.databinding.FragmentSearchBinding
import com.example.usergrocerymart.models.Product
import com.example.usergrocerymart.viewmodels.UserViewModel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    val viewModel: UserViewModel by viewModels()
private lateinit var binding: FragmentSearchBinding
private lateinit var adapterProduct: AdapterProduct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        getallrpoducts()
        searchproducts()
        backtohome()
        return binding.root
    }
    private fun searchproducts() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                adapterProduct.filter?.filter(query)

            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    private fun backtohome() {
        binding.searchEt.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_HomeFragment)}
        }


    private fun getallrpoducts() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.fetchallprod().collect {
                if (it.isEmpty()) {
                    binding.rvproducts.visibility = View.GONE
                    binding.tvtext.visibility = View.VISIBLE
                } else {
                    binding.rvproducts.visibility = View.VISIBLE
                    binding.tvtext.visibility = View.GONE
                }
                adapterProduct = AdapterProduct()
                binding.rvproducts.adapter = adapterProduct
                adapterProduct.differ.submitList(it)
                adapterProduct.originallist = it as ArrayList<Product>
                binding.shimmerViewContainer.visibility = View.GONE
            }
        }
    }

}