package com.gdbc.clubdeportivo.ui.abonar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentAbonarBinding

class AbonarFragment : Fragment() {

    private var _binding: FragmentAbonarBinding? = null
    private val binding get() = _binding!!
    private lateinit var abonarViewModel: AbonarViewModel
    private lateinit var dni: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbonarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        abonarViewModel = ViewModelProvider(this)[AbonarViewModel::class.java]

        dni = arguments?.getString("dni").toString()
        abonarViewModel.setDni(dni)
        Log.i("prueba", dni)

        // Enlazar el LiveData con el TextView
        abonarViewModel.text.observe(viewLifecycleOwner) { newText ->
            // Aquí actualizamos el TextView cuando cambia el valor del LiveData
            binding.textAbonar.text = newText
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
