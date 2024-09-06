package com.gdbc.clubdeportivo.ui.finalizarinscripcion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentFinalizarInscripcionBinding

class FinalizarInscripcionFragment : Fragment() {

    private var _binding: FragmentFinalizarInscripcionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(FinalizarInscripcionModel::class.java)

        _binding = FragmentFinalizarInscripcionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFinalizarinscripcion
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