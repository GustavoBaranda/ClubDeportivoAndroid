package com.gdbc.clubdeportivo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.data.model.Moroso

class MorosoAdapter(
    private val morosos: List<Moroso>,
    private val onClick: (String) -> Unit,
    private val noResultsTextView: TextView
) : RecyclerView.Adapter<MorosoAdapter.MorosoViewHolder>() {

    private var morososFiltrados = morosos.toMutableList()

    inner class MorosoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDefaulter: TextView = itemView.findViewById(R.id.tvDefaulter)
        private val tvDNI: TextView = itemView.findViewById(R.id.tvDNI)
        private val btnPay: Button = itemView.findViewById(R.id.btnPay)

        fun bind(moroso: Moroso) {
            "${moroso.nombre} ${moroso.apellido}".also { tvDefaulter.text = it }
            "DNI: ${moroso.dni}".also { tvDNI.text = it }
            btnPay.setOnClickListener {
                onClick(moroso.dni)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String?) {

        // Si la lista original está vacía, no hay nada que filtrar
        if (morosos.isEmpty()) {
            morososFiltrados.clear()
            notifyDataSetChanged()
            return
        }
        // Limpiar la lista filtrada antes de aplicar el filtro
        morososFiltrados.clear()

        // Si la búsqueda está vacía, mostramos todos los morosos
        if (query.isNullOrEmpty()) {
            morososFiltrados.addAll(morosos)
        } else {
            morososFiltrados.addAll(morosos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.apellido.contains(query, ignoreCase = true)
            })
        }
        notifyDataSetChanged()

        // Si la lista filtrada está vacía, mostramos el mensaje de "sin resultados"
        if (morososFiltrados.isEmpty()) {
            noResultsTextView.visibility = View.VISIBLE
        } else {
            noResultsTextView.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MorosoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_moroso, parent, false)
        return MorosoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MorosoViewHolder, position: Int) {
        holder.bind(morososFiltrados[position])
    }

    override fun getItemCount() = morososFiltrados.size
}

