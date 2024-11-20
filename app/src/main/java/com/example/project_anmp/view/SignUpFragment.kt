package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.project_anmp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkBoxAgreement.setOnClickListener {
            binding.btnSubmit.isEnabled = binding.checkBoxAgreement.isChecked
        }

        // Trigger the back button using NavController
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSubmit.setOnClickListener {
            // Check if any required field is empty
            if (binding.txtFirstName.text.isEmpty() ||
                binding.txtUsername.text.isEmpty() ||
                binding.txtPassword.text.isEmpty() ||
                binding.txtRepeatPassword.text.isEmpty()
            ) {
                Toast.makeText(
                    context,
                    "All fields must be filled.",
                    Toast.LENGTH_LONG
                ).show()
            }
            // Check if passwords do not match
            else if (binding.txtPassword.text.toString() != binding.txtRepeatPassword.text.toString()) {
                Toast.makeText(
                    context,
                    "Password and Repeat Password must be the same.",
                    Toast.LENGTH_LONG
                ).show()
            }
            // Proceed with successful validation
            else {


                Toast.makeText(
                    context,
                    "Registration successful! Please Sign In to continue.",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().popBackStack()
            }
        }

    }

}