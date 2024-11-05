package com.gdbc.clubdeportivo.ui.abonar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Pago
import com.gdbc.clubdeportivo.data.repository.ActividadRepository
import com.gdbc.clubdeportivo.data.repository.ClienteRepository
import com.gdbc.clubdeportivo.data.repository.MorosoRepository
import com.gdbc.clubdeportivo.data.repository.PagoRepository
import com.gdbc.clubdeportivo.databinding.FragmentAbonarBinding
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale

class AbonarFragment : Fragment() {

    companion object {
        const val DNI = "DNI"
        private const val PRECIO_MENSUAL = 30000
        private const val PRECIO_3_CUOTAS = PRECIO_MENSUAL * 1.10
        private const val PRECIO_6_CUOTAS = PRECIO_MENSUAL * 1.20
    }

    private var _binding: FragmentAbonarBinding? = null
    private val binding get() = _binding!!

    private lateinit var abonarViewModel: AbonarViewModel
    private lateinit var dbHelper: BDatos
    private lateinit var clienteRepository: ClienteRepository
    private lateinit var morosoRepository: MorosoRepository
    private lateinit var pagoRepository: PagoRepository
    private lateinit var actividadRepository: ActividadRepository

    private var esSocio: Boolean = false
    private var clienteActual: Cliente? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbonarBinding.inflate(inflater, container, false)
        abonarViewModel = ViewModelProvider(this)[AbonarViewModel::class.java]

        initDB()
        initUI()
        initListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggleSpinnerState()
    }

    private fun initDB() {
        dbHelper = BDatos(requireContext())
        clienteRepository = ClienteRepository(dbHelper)
        morosoRepository = MorosoRepository(dbHelper)
        pagoRepository = PagoRepository(dbHelper)
        actividadRepository = ActividadRepository(dbHelper)

    }

    private fun initUI() {
        val dniArg = arguments?.getString(DNI).orEmpty()
        if (dniArg.isNotEmpty()) {
            abonarViewModel.setDni(dniArg)
        }
        abonarViewModel.dni.observe(viewLifecycleOwner) { dni ->
            binding.etDNI.setText(dni)

            if (dni.isNotEmpty()) {
                verificarCliente(dni)
            }
        }

    }

    private fun verificarCliente(dni: String): Boolean {

        if (dni.isNotEmpty()) {

            clienteActual = clienteRepository.clientePorDni(dni)

            if (clienteActual != null) {
                esSocio = clienteActual!!.tipoCliente == "socio"
                uiSegunCliente()
                actualizarImporte()
                actualizarPase()

                val esMoroso = morosoRepository.esMoroso(clienteActual!!.idCliente!!)

                if (esMoroso) {
                    binding.btnPagar.visibility = View.VISIBLE
                    binding.btnBuscar.visibility = View.GONE
                    return true
                } else {
                    mostrarToast("Tiene la cuota al dia")
                }

            } else {
                mostrarToast("Cliente no encontrado")

            }
        } else {
            mostrarToast("Debe ingresar un DNI")
        }
        return false
    }

    private fun limpiarDatos() {
        clienteActual = null
        esSocio = false
        binding.etDNI.setText("")
        binding.tvImporte.text = formatearImporte(0)
        binding.tvDescription.text = getString(R.string.buscar_txt)
        binding.btnPagar.visibility = View.GONE
        binding.btnBuscar.visibility = View.VISIBLE
    }

    private fun actualizarPase() {
        val pase = binding.tvDescription

        if (esSocio) {
            "PASE LIBRE".also { pase.text = it }
        } else {
            val actividadSeleccionada = binding.spActividades.selectedItem.toString()
            pase.text = actividadSeleccionada
        }
    }

    private fun uiSegunCliente() {
        val visibilityCuotas = if (esSocio) View.VISIBLE else View.GONE
        val visibilityActividades = if (esSocio) View.GONE else View.VISIBLE

        binding.tvCuotas.visibility = visibilityCuotas
        binding.spCuotas.visibility = visibilityCuotas
        binding.tvActividad.visibility = visibilityActividades
        binding.spActividades.visibility = visibilityActividades

    }

    private fun initListeners() {

        binding.btnBuscar.setOnClickListener {
            val dni = binding.etDNI.text.toString().trim()
            if (!verificarCliente(dni)) limpiarDatos()
        }

        binding.btnPagar.setOnClickListener {
            val dni = binding.etDNI.text.toString().trim()
            if (verificarCliente(dni)) confirmarPago()
        }

        binding.btnReset.setOnClickListener {
            limpiarDatos()
        }

        binding.rbEfectivo.setOnCheckedChangeListener { _, _ ->
            actualizarImporte()
            toggleSpinnerState()
        }
        binding.rbTarjeta.setOnCheckedChangeListener { _, _ ->
            actualizarImporte()
            toggleSpinnerState()
        }

        binding.spCuotas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                actualizarImporte()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spActividades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                actualizarPase()
                actualizarImporte()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun toggleSpinnerState() {
        if (binding.rbEfectivo.isChecked) {

            binding.spCuotas.isEnabled = false
            binding.spCuotas.setBackgroundResource(R.drawable.spinner_background)
        } else if (binding.rbTarjeta.isChecked) {

            binding.spCuotas.isEnabled = true
            binding.spCuotas.setBackgroundResource(R.drawable.spinner_background)
        }
    }

    private fun actualizarImporte() {
        val tvImporte = binding.tvImporte
        val actividadSeleccionada = binding.spActividades.selectedItem.toString()
        val precioActividad = actividadRepository.obtenerPrecioPorNombre(actividadSeleccionada)

        if (binding.etDNI.text.isNotEmpty() && clienteActual != null) {
            if (binding.rbEfectivo.isChecked) {

                tvImporte.text = if (esSocio) {
                    formatearImporte(PRECIO_MENSUAL)
                } else {

                    formatearImporte(precioActividad!!)
                }
            } else if (binding.rbTarjeta.isChecked) {

                if (esSocio) {
                    val cuotasSeleccionadas = extraerNumeroDeCuotas(
                        binding.spCuotas.selectedItem.toString()
                    )
                    val importe = when (cuotasSeleccionadas) {
                        1 -> PRECIO_MENSUAL
                        3 -> PRECIO_3_CUOTAS
                        6 -> PRECIO_6_CUOTAS
                        else -> PRECIO_MENSUAL
                    }
                    tvImporte.text = formatearImporte(importe)
                } else {
                    tvImporte.text = formatearImporte(precioActividad!!)
                }
            }
        } else {
            limpiarDatos()
            tvImporte.text = formatearImporte(0)
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun extraerNumeroDeCuotas(texto: String): Int {
        return texto.split(" ").firstOrNull()?.toIntOrNull() ?: 1
    }

    private fun formatearImporte(importe: Number): String {
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
        return formatoMoneda.format(importe)
    }

    private fun confirmarPago() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmar Pago")
        builder.setMessage("¿Esta seguro de realizar el pago?")
        builder.setPositiveButton("Sí") { _, _ -> realizarPago() }
        builder.setNegativeButton("No", null)
        builder.create().show()
    }

    private fun realizarPago() {
        if (clienteActual != null) {
            val actividadId =
                actividadRepository.obtenerIdPorNombre(binding.tvDescription.text.toString())
            val montoTexto =
                binding.tvImporte.text.toString().replace(Regex("[$ ]"), "").replace(".", "")
                    .replace(",", ".").trim()
            val tarjeta = binding.rbTarjeta.isChecked
            val cuotas = extraerNumeroDeCuotas(binding.spCuotas.selectedItem.toString())
            val metodoPago = if (binding.rbEfectivo.isChecked) "efectivo" else "tarjeta"
            val cliente = clienteActual!!.idCliente!!
            val cuota = if (esSocio) "mensual" else "diario"

            val pago = Pago(
                monto = montoTexto.toDoubleOrNull() ?: 0.0,
                metodoPago = metodoPago,
                idCliente = cliente,
                tipoPago = cuota,
                idActividad = if (esSocio) null else actividadId,
                cantCuotas = if (tarjeta) cuotas else null,
                fechaPago = LocalDate.now()
            )

            val exito = pagoRepository.crearPago(pago)
            if (exito > 0) {
                morosoRepository.eliminarMoroso(cliente)

                val bundle = Bundle().apply {
                    putDouble("monto", montoTexto.toDoubleOrNull() ?: 0.0)
                    putString("nombre", clienteActual?.nombre)
                    putString("apellido", clienteActual?.apellido)
                    putString("dni", clienteActual?.dni)
                    putString("cuota", cuota.replaceFirstChar { p -> p.uppercase() })
                    putString("pagoId", exito.toString())
                }
                limpiarDatos()
                findNavController().navigate(R.id.nav_comprobante, bundle)

            } else {
                mostrarToast("Error al realizar el pago")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dbHelper.close()
        _binding = null
    }
}
