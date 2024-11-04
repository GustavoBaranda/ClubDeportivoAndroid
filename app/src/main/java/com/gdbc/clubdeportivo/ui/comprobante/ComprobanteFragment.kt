package com.gdbc.clubdeportivo.ui.comprobante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.databinding.FragmentComprobanteBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ComprobanteFragment : Fragment() {

    private var _binding: FragmentComprobanteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentComprobanteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListeners()

        val pagoId = arguments?.getString("pagoId")?.toIntOrNull()?.let { formatearPagoId(it) }
        val nombre = arguments?.getString("nombre")
        val apellido = arguments?.getString("apellido")
        val dni = arguments?.getString("dni")
        val fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val monto = arguments?.getDouble("monto") ?: 0.0
        val cuota = arguments?.getString("cuota")

        binding.tvComprobante.text = getString(R.string.comprobante, pagoId)
        binding.tvNombreCliente.text = getString(R.string.nombre_completo, nombre, apellido)
        binding.tvDniNumber.text = dni
        binding.tvFechaPago.text = fechaPago
        binding.tvMonto.text = getString(R.string.monto_abonado, monto)
        binding.tvCuotaTipo.text = getString(R.string.cuota, cuota)

        return root
    }

    private fun initListeners() {
        binding.btnCarnet.setOnClickListener {
            navegarAlCarnet()
        }
    }

    private fun navegarAlCarnet() {
        val nombre = arguments?.getString("nombre") ?: ""
        val apellido = arguments?.getString("apellido") ?: ""
        val dni = arguments?.getString("dni") ?: ""
        val cuota = arguments?.getString("cuota") ?: ""
        val fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        val bundle = Bundle().apply {
            putString("nombre", nombre)
            putString("apellido", apellido)
            putString("dni", dni)
            putString("cuota", cuota)
            putString("fechaPago", fechaPago)
        }

        findNavController().navigate(R.id.nav_visualizar_carnet,bundle)
    }

    private fun formatearPagoId(id: Int): String {
        return String.format(Locale.US, "%06d", id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}