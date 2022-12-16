package com.example.hackathon2022.ui.singup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.hackathon2022.R
import com.example.hackathon2022.databinding.FragmentSignupBinding
import com.example.hackathon2022.ui.singup.SignupViewModel

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val signupViewModel =
            ViewModelProvider(this).get(SignupViewModel::class.java)

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editUsername: EditText = binding.editUsername
        val editPassword: EditText = binding.editPass

        binding.apply {
            btSignup.setOnClickListener {
                findNavController().navigate(R.id.navigation_signup)
            }
            btLogin.setOnClickListener {
                findNavController().navigate(R.id.navigation_login)
            }
            btSignupSelect.setOnClickListener {
                signupViewModel.registerUser(editUsername.text.toString(), editPassword.text.toString())
                findNavController().navigate(R.id.navigation_home)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}