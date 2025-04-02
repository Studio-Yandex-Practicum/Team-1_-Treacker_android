package com.example.tracker.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.AuthorizationFragmentBinding

class AuthorizationFragment : Fragment() {
    private var _binding: AuthorizationFragmentBinding? = null
    val binding: AuthorizationFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_recoveryFragment)
        }
        binding.registration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }
}