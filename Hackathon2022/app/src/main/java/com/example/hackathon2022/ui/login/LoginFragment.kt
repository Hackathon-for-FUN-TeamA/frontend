package com.example.hackathon2022.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hackathon2022.MainActivity
import com.example.hackathon2022.R
import com.example.hackathon2022.databinding.FragmentLoginBinding
import com.example.hackathon2022.ui.login.LoginViewModel

class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            btLogin.setOnClickListener{
//                findNavController().navigate(R.id.navigation_home)
                  findNavController().navigate(R.id.navigation_login)
            }
            btSignup.setOnClickListener{
//                findNavController().navigate(R.id.navigation_home)
                findNavController().navigate(R.id.navigation_signup)
            }
            btLoginSelect.setOnClickListener{
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