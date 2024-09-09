package com.gdbc.clubdeportivo.ui.listarcuotasvencidas

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.gdbc.clubdeportivo.MainActivity
import com.gdbc.clubdeportivo.R
//import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentListarCuotasVencidasBinding
import com.gdbc.clubdeportivo.ui.home.HomeFragment

class ListarCuotasVencidasFragment : Fragment() {
    private var _binding: FragmentListarCuotasVencidasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val ListarCuotasVencidasViewModel =
            ViewModelProvider(this).get(ListarCuotasVencidasViewModel::class.java)*/

        _binding = FragmentListarCuotasVencidasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Simulación de lista de socios morosos con nombre y DNI usando Pair
        val mockSocios = listOf(
            Pair("Juan Pérez", "DNI: 12345678"),
            Pair("María Gómez", "DNI: 87654321"),
            Pair("Carlos López", "DNI: 11223344"),
            Pair("Gustavito López", "DNI: 11223344"),
            Pair("José López", "DNI: 11223344"),
            Pair("Todos López", "DNI: 11223344"),
            Pair("Jajajaja López", "DNI: 11223344")
        )

        val borderDrawable = GradientDrawable().apply {
            setColor(Color.WHITE) // Color de fondo del CardView
            setStroke(1, Color.GRAY) // Ancho del borde y color
            cornerRadius = 12f // Radio de las esquinas
        }

        // Añadir dinámicamente los elementos a la vista
        mockSocios.forEach { socio ->
            // Crear un CardView para contener cada elemento
            val cardView = CardView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 16) // Márgenes del CardView
                }
                radius = 12f // Esquinas redondeadas del CardView
                cardElevation = 8f // Sombra del CardView

                setCardBackgroundColor(Color.WHITE) // Color de fondo del CardView
                setPadding(16, 16, 16, 16) // Padding interno del CardView
                background = borderDrawable // Aplicar el borde
            }

            // Crear un LinearLayout horizontal para contener el TextView y el botón
            val horizontalLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // Crear un LinearLayout vertical para contener el nombre y el DNI
            val verticalLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0, // Ocupa todo el espacio disponible en el horizontal
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f // Peso para que el layout vertical ocupe todo el espacio disponible
                )
            }

            // Crear un TextView para el nombre
            val textViewNombre = TextView(requireContext()).apply {
                text = socio.first // socio.first debe ser un String
                textSize = 16f
                setTextColor(Color.BLACK)
                setPadding(16, 10, 0, 8) // Padding inferior del TextView
            }

            // Crear un TextView para el DNI
            val textViewDNI = TextView(requireContext()).apply {
                text = socio.second // socio.second debe ser un String
                textSize = 14f
                setTextColor(Color.GRAY)
                setPadding(16, 0, 0, 16) // Padding inferior del TextView
            }

            // Crear un botón de Pagar
            val buttonPagar = Button(requireContext()).apply {
                text = "Pagar"
                setBackgroundColor(Color.BLACK)
                setTextColor(Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                ).apply {
                    setMargins(16, 0, 0, 0) // Márgenes del botón
                }
            }

            // Acción al presionar el botón Pagar
            buttonPagar.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Ha seleccionado: ${socio.first}",
                    Toast.LENGTH_SHORT
                ).show()
                // Aquí podrías obtener el DNI y realizar una acción
            }

            // Agregar el TextView de nombre y DNI al LinearLayout vertical
            verticalLayout.addView(textViewNombre)
            verticalLayout.addView(textViewDNI)

            // Agregar el LinearLayout vertical y el botón al LinearLayout horizontal
            horizontalLayout.addView(verticalLayout)
            horizontalLayout.addView(buttonPagar)

            // Agregar el LinearLayout horizontal al CardView
            cardView.addView(horizontalLayout)

            // Agregar el CardView al contenedor principal
            binding.linearLayoutList.addView(cardView)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
