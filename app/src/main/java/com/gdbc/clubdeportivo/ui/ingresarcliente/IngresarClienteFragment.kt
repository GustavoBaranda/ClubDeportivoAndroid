package com.gdbc.clubdeportivo.ui.ingresarcliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Usuario
import com.gdbc.clubdeportivo.data.repository.ClienteRepository
import com.gdbc.clubdeportivo.data.repository.MorosoRepository
import com.gdbc.clubdeportivo.data.repository.UsuarioRepository
import com.gdbc.clubdeportivo.databinding.FragmentIngresarClienteBinding
import com.gdbc.clubdeportivo.ui.abonar.AbonarFragment
import java.time.LocalDate

class IngresarClienteFragment : Fragment() {

    private var _binding: FragmentIngresarClienteBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: BDatos
    private lateinit var clienteRepository: ClienteRepository
    private lateinit var usuarioRepository: UsuarioRepository
    private lateinit var morosoRepository: MorosoRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngresarClienteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initDB()
        initListeners()

        return root
    }

    private fun initDB() {
        dbHelper = BDatos(requireContext())
        clienteRepository = ClienteRepository(dbHelper)
        usuarioRepository = UsuarioRepository(dbHelper)
    }

    private fun initListeners() {

        binding.btnNext.setOnClickListener {
            if (validarCampos()) {
                val datos = obtenerDatos()

                if (verificarDni(datos["dni"]!!)) {
                    mostrarToast("El cliente con ese DNI ya existe")
                    return@setOnClickListener
                }

                if (verificarAlias(datos["alias"]!!)) {
                    mostrarToast("El alias ya existe")
                    return@setOnClickListener
                }

                val usuario = crearUsuario()
                val cliente = crearCliente()

                usuarioRepository.crearUsuarioCliente(usuario, cliente)
                morosoRepository.agregarMoroso(cliente.idCliente!!)

                val bundle = Bundle().apply {
                    putString(AbonarFragment.DNI, cliente.dni)
                }
                findNavController().navigate(R.id.nav_abonar, bundle)

            }
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensaje(mensaje: String): Boolean {
        mostrarToast(mensaje)
        return false
    }

    private fun obtenerDatos(): Map<String, String> {
        return mapOf(
            "nombre" to binding.etNombre.text.trim().toString(),
            "apellido" to binding.etApellido.text.trim().toString(),
            "dni" to binding.etDni.text.trim().toString(),
            "alias" to binding.etAlias.text.trim().toString(),
            "contrasena" to binding.etContrasena.text.trim().toString()
        )
    }

    private fun validarCampos(): Boolean {
        val datos = obtenerDatos()

        return when {
            datos["nombre"].isNullOrEmpty() -> mostrarMensaje("Debe ingresar un nombre")

            datos["apellido"].isNullOrEmpty() -> mostrarMensaje("Debe ingresar un apellido")

            datos["dni"].isNullOrEmpty() || datos["dni"]?.length != 8 -> mostrarMensaje("Debe ingresar un DNI valido")

            datos["alias"].isNullOrEmpty() -> mostrarMensaje("Debe ingresar un alias")

            datos["contrasena"].isNullOrEmpty() -> mostrarMensaje("Debe ingresar una contraseña")

            binding.rgCliente.checkedRadioButtonId == -1 -> mostrarMensaje("Debe seleccionar un tipo de cliente")

            !binding.cbAptoFisico.isChecked -> mostrarMensaje("Debe marcar el apto físico")
            else -> true
        }
    }

    private fun verificarAlias(alias: String): Boolean {
        return usuarioRepository.existeAlias(alias)
    }

    private fun verificarDni(dni: String): Boolean {
        return clienteRepository.clientePorDni(dni) != null
    }

    private fun obtenerTipoCliente(): String {
        val selectedRadioButton: RadioButton =
            binding.rgCliente.findViewById(binding.rgCliente.checkedRadioButtonId)
        return selectedRadioButton.text.toString()
    }

    private fun crearUsuario(): Usuario {
        val datos = obtenerDatos()

        return Usuario(
            alias = datos["alias"]!!,
            contrasena = datos["contrasena"]!!,
            rol = "cliente"
        )
    }

    private fun crearCliente(): Cliente {
        val datos = obtenerDatos()

        return Cliente(
            nombre = datos["nombre"]!!,
            apellido = datos["apellido"]!!,
            dni = datos["dni"]!!,
            tieneAptoFisico = 1,
            tipoCliente = obtenerTipoCliente().lowercase(),
            fechaRegistro = LocalDate.now()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dbHelper.close()
        _binding = null
    }
}
