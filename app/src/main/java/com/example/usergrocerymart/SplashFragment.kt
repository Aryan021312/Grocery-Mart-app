package com.example.usergrocerymart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.usergrocerymart.activity.UserMainActivity
import com.example.usergrocerymart.databinding.FragmentSplashBinding
import com.example.usergrocerymart.viewmodels.authviewmodel
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private val viewModel:authviewmodel by viewModels()
    private lateinit var binding :FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSplashBinding.inflate(layoutInflater)
        setstatusbarcolor()
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                viewModel.currentuser.collect{
                    if(it){
                        startActivity(Intent(requireActivity(),UserMainActivity::class.java))
                        requireActivity().finish()
                    }
                    else{
                        findNavController().navigate(R.id.action_splashFragment_to_signinFragment)
                    }
                }

            }

        },  2000)
        return binding.root
    }
private fun setstatusbarcolor(){
    activity?.window?.apply{
        val statusbarcolor=ContextCompat.getColor(requireContext(),R.color.darkgreen)
        statusBarColor=statusbarcolor
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
}