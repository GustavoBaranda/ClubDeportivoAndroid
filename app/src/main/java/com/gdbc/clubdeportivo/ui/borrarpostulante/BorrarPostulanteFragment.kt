package com.gdbc.clubdeportivo.ui.borrarpostulante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentBorrarPostulanteBinding

class BorrarPostulanteFragment : Fragment() {

    private var _binding: FragmentBorrarPostulanteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(BorrarPostulanteViewModel::class.java)

        _binding = FragmentBorrarPostulanteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBorrarPostulante
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