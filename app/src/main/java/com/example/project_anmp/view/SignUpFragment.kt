package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.project_anmp.databinding.FragmentSignUpBinding
import com.example.project_anmp.model.User
import com.example.project_anmp.viewmodel.SignUpViewModel

class SignUpFragment : Fragment(), SignUpActionsHandler {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Data Binding
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        // Initialize User, Repeat Password, and Interface
        val user = User("", "", "", "")
        binding.user = user
        binding.handler = this

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.signUpMessage.observe(viewLifecycleOwner, Observer {
            if(it == "Sign-up successful!"){
                findNavController().popBackStack()
            }

            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onBackClicked(v: View) {
        Navigation.findNavController(v).popBackStack()
    }


    override fun onSubmitClicked(obj: User, repeatPassword: String) {
        viewModel.signUp(obj, repeatPassword)
    }

}