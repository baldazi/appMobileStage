package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.Annonce
import com.example.stage.R
import data.Entreprise

class EntrepriseAdapter(private val newEnt: ArrayList<Entreprise>) :
    RecyclerView.Adapter<EntrepriseAdapter.EntrepriseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrepriseViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_list, parent, false)
        return  EntrepriseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntrepriseViewHolder, position: Int) {

        val currentItem = newEnt[position]
        holder.name.text = currentItem.name
        holder.seat.text = currentItem.seat
        holder.sect.text = currentItem.sect
    }

    override fun getItemCount(): Int {
        return  newEnt.size
    }

    class EntrepriseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name : TextView = itemView.findViewById(R.id.company_name)
        val seat : TextView = itemView.findViewById(R.id.company_seat)
        val sect : TextView = itemView.findViewById(R.id.company_sector)
    }
}