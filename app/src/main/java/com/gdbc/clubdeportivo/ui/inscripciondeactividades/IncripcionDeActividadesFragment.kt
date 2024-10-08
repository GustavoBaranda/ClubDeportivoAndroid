package com.gdbc.clubdeportivo.ui.inscripciondeactividades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentIncripcionDeActividadesBinding

class IncripcionDeActividadesFragment : Fragment() {

    private var _binding: FragmentIncripcionDeActividadesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val IncripcionDeActividadesViewModel =
            ViewModelProvider(this).get(IncripcionDeActividadesViewModel::class.java)

        _binding = FragmentIncripcionDeActividadesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
