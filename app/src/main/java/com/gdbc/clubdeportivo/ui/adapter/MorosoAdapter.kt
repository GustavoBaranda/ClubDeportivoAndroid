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
    private val onClick: (Int,String) -> Unit
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
                onClick(moroso.idCliente,moroso.dni)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String?) {
        morososFiltrados.clear()
        if (query.isNullOrEmpty()) {
            morososFiltrados.addAll(morosos)
        } else {
            morososFiltrados.addAll(morosos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.apellido.contains(query, ignoreCase = true)
            })
        }
        notifyDataSetChanged()
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

