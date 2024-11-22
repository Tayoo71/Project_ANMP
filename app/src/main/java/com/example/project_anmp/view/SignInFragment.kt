package com.example.project_anmp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentSignInBinding
import com.example.project_anmp.viewmodel.SignInViewModel
import com.example.project_anmp.viewmodel.SignUpViewModel


class SignInFragment : Fragment(), SignInActionsHandler {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.handler = this  
        checkLoginStatus()
        observeViewModel()
    }

    private fun checkLoginStatus() {
        var sharedFile = "com.example.project_anmp"
        val sharedPreferences = requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val action = SignInFragmentDirections.actionItemWhatWePlay()
            findNavController().navigate(action)
        }
    }

//    private fun clearLoginData() {
//        var sharedFile = "com.example.project_anmp"
//        val sharedPreferences = requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
//        with(sharedPreferences.edit()) {
//            clear()
//            apply()
//        }
//    }

    private fun observeViewModel() {
        viewModel.signInMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            if(it == "Sign-In successful!"){
                val action = SignInFragmentDirections.actionItemWhatWePlay()
                findNavController().navigate(action)
            }
        })
    }

    override fun onSignUpClicked(v: View) {
        val action = SignInFragmentDirections.actionSignUpFragment()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onSubmitClicked(username: String, password: String) {
        viewModel.signIn(username, password)
    }
}

