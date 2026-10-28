package com.example.b15recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador(
    private val listaPaises: MutableList<String>,
    private val onItemClick: (Int) -> Unit
    ) : RecyclerView.Adapter<Adaptador.PaisesVista>() {

    class PaisesVista(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.paises)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisesVista {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.pais, parent, false)
        return PaisesVista(vista)
    }

    override fun onBindViewHolder(holder: PaisesVista, position: Int) {
        holder.textView.text = listaPaises[position]

        holder.itemView.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if(pos != RecyclerView.NO_POSITION){
                onItemClick(pos)
            }
        }
    }
    override fun getItemCount(): Int = listaPaises.size
}