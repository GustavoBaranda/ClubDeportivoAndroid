package com.gdbc.clubdeportivo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gdbc.clubdeportivo.databinding.ActivityComprobanteBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ComprobanteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComprobanteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityComprobanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val pagoId = bundle?.getString("pagoId")?.toIntOrNull()?.let { formatearPagoId(it) }
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        val fechaPago = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val monto = bundle?.getDouble("monto") ?: 0.0
        val cuota = bundle?.getString("cuota")

        binding.tvComprobante.text = getString(R.string.factura, pagoId)
        binding.tvNombre.text = getString(R.string.nombre_completo, nombre, apellido)
        binding.tvFecha.text = getString(R.string.fecha_pago, fechaPago)
        binding.tvMonto.text = getString(R.string.monto, monto)
        binding.tvCuota.text = getString(R.string.cuota, cuota)
    }

    private fun formatearPagoId(id: Int): String {
        return String.format(Locale.US, "%06d", id)
    }
}