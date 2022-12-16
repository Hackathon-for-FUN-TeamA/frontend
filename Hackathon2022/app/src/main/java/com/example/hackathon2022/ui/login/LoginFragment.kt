package com.example.hackathon2022.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hackathon2022.R
import com.example.hackathon2022.databinding.FragmentLoginBinding

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

        val editUsername: EditText = binding.editUsername
        val editPassword: EditText = binding.editPass

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
                loginViewModel.login(editUsername.text.toString(), editPassword.text.toString())
                if (loginViewModel.loginResponse.value != null) {
                    findNavController().navigate(R.id.navigation_home)
                }
            }
        }

        loginViewModel.loginResponse.observe(viewLifecycleOwner) {
            binding.apply {
                it.body()?.let { it1 -> loginViewModel.putToken(it1.token) }
            }
        }

        loginViewModel.token.observe(viewLifecycleOwner) {
            Log.d("token", it)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}