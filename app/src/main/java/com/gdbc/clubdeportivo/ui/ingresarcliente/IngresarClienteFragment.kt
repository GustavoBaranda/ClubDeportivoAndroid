package com.gdbc.clubdeportivo.ui.ingresarcliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentIngresarClienteBinding

class IngresarClienteFragment : Fragment() {

    private var _binding: FragmentIngresarClienteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val IngresarClienteViewModel =
            ViewModelProvider(this).get(IngresarClienteViewModel::class.java)

        _binding = FragmentIngresarClienteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
