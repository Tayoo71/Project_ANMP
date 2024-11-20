package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.txtUsername.text.isEmpty() || binding.txtPassword.text.isEmpty()) {
                Toast.makeText(
                    context,
                    "Enter username and password.",
                    Toast.LENGTH_LONG
                ).show()
            } else {


                val action = SignInFragmentDirections.actionItemWhatWePlay()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}