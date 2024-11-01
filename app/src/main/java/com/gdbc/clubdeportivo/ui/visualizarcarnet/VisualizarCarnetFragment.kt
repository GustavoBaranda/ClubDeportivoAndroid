package com.gdbc.clubdeportivo.ui.visualizarcarnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentVisualizarCarnetBinding

class VisualizarCarnetFragment : Fragment() {

    private var _binding: FragmentVisualizarCarnetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[VisualizarCarnetViewModel::class.java]

        _binding = FragmentVisualizarCarnetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVisualizarCarnet
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}