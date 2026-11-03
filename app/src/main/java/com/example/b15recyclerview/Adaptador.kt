package com.example.b15recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class Adaptador(
    private val listaPaises: MutableList<Countries>,
    private val onItemClick: (Int) -> Unit
    ) : RecyclerView.Adapter<Adaptador.PaisesVista>() {

    class PaisesVista(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bandera: ImageView = itemView.findViewById(R.id.bandera)
        val nombre : TextView = itemView.findViewById(R.id.nombre)
        val capital : TextView = itemView.findViewById(R.id.capital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisesVista {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.pais, parent, false)
        return PaisesVista(vista)
    }

    override fun onBindViewHolder(holder: PaisesVista, position: Int) {
        val pais = listaPaises[position]
        holder.nombre.text = pais.nombre
        holder.capital.text = "Capital: ${pais.capital}"

        /*
        //obtener las imagenes del drawable
        val context = holder.itemView.context
        val imageResId = context.resources.getIdentifier(
            pais.id, "drawable", context.packageName
        )

        if(imageResId != 0){
            holder.bandera.setImageResource(imageResId)
        }
        else{
            //respaldo default
            holder.bandera.setImageResource(R.drawable.ic_launcher_foreground)
        }
        */

        //URL de flagCDN
        /*val flagURL = "https://flagcdn.com/w160/${pais.id.lowercase()}.png"

        holder.bandera.load(flagURL){
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }*/

        if (pais.id.isEmpty() || pais.id == "default") {
            holder.bandera.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            val flagURL = "https://flagcdn.com/w160/${pais.id.lowercase()}.png"
            holder.bandera.load(flagURL) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }
        }

        holder.itemView.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if(pos != RecyclerView.NO_POSITION){
                onItemClick(pos)
            }
        }
    }
    override fun getItemCount(): Int = listaPaises.size
}