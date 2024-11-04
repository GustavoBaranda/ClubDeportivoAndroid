package com.gdbc.clubdeportivo.ui.visualizarcarnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gdbc.clubdeportivo.Login
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.repository.ClienteRepository
import com.gdbc.clubdeportivo.data.repository.PagoRepository
import com.gdbc.clubdeportivo.databinding.FragmentVisualizarCarnetBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class VisualizarCarnetFragment : Fragment() {

    private var _binding: FragmentVisualizarCarnetBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: BDatos
    private lateinit var clienteRepository: ClienteRepository
    private lateinit var pagoRepository: PagoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVisualizarCarnetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initDB()
        initListeners()

        val userId = arguments?.getInt(Login.USER_ID) ?: -1
        val userRol = arguments?.getString(Login.USER_ROL)

        if (userId != -1 && userRol == "cliente") {
            completarCarnetParaCliente(userId)
        } else {
            completarCarnetParaEmpleado()
        }

        return root
    }

    private fun initDB() {
        dbHelper = BDatos(requireContext())
        clienteRepository = ClienteRepository(dbHelper)
        pagoRepository = PagoRepository(dbHelper)
    }

    private fun completarCarnetParaCliente(userId: Int) {
        val cliente = clienteRepository.clientePorIdUsuario(userId)
        val ultimoPago = pagoRepository.obtenerUltimoPago(cliente?.idCliente!!)

        if (ultimoPago != null) {
            completarVistaCarnet(
                nombre = cliente.nombre,
                apellido = cliente.apellido,
                dni = cliente.dni,
                tipoCuota = ultimoPago.tipoPago,
                fechaPago = ultimoPago.fechaPago
            )
        } else {
            Toast.makeText(
                requireContext(),
                "El cliente todavia no resgistro un pago",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun completarCarnetParaEmpleado() {
        val nombre = arguments?.getString("nombre") ?: ""
        val apellido = arguments?.getString("apellido") ?: ""
        val dni = arguments?.getString("dni") ?: ""
        val cuota = arguments?.getString("cuota") ?: ""
        val fechaPagoString = arguments?.getString("fechaPago") ?: ""
        val fechaPago = LocalDate.parse(fechaPagoString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        completarVistaCarnet(
            nombre = nombre,
            apellido = apellido,
            dni = dni,
            tipoCuota = cuota,
            fechaPago = fechaPago
        )
    }

    private fun completarVistaCarnet(
        nombre: String,
        apellido: String,
        dni: String,
        tipoCuota: String,
        fechaPago: LocalDate
    ) {
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaPagoFormateada = fechaPago.format(formatoFecha)
        val cuota = tipoCuota.replaceFirstChar { it.uppercase() }

        "$nombre $apellido".also { binding.tvNombreCliente.text = it }
        binding.tvDniNumber.text = dni
        binding.tvCuotaTipo.text = cuota
        binding.tvFechaPago.text = fechaPagoFormateada
    }

    private fun initListeners() {
        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.nav_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}