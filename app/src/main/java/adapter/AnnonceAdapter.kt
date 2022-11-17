package adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.Annonce
import com.example.stage.R
import listener.OnAnnonceClickListener

class AnnonceAdapter(private val newAnnonce: ArrayList<Annonce>, private val onAnnonceClickListener: OnAnnonceClickListener) :
    RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnonceViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.annonce_list, parent, false)
        return  AnnonceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnnonceViewHolder, position: Int) {

        val currentItem = newAnnonce[position]
        holder.title.text = currentItem.title
        holder.location.text = currentItem.location
        holder.duration.text = currentItem.duration.toString() + " mois"
        holder.company.text = currentItem.company
        holder.initDate.text = currentItem.initDate

        holder.itemView.setOnClickListener{
            //Log.e("err","$position")
            onAnnonceClickListener.onAnnonceClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return  newAnnonce.size
    }


    class AnnonceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.annonce_title)
        val location : TextView = itemView.findViewById(R.id.annonce_location)
        val company : TextView = itemView.findViewById(R.id.annonce_company)
        val duration : TextView = itemView.findViewById(R.id.annonce_duration)
        val initDate : TextView = itemView.findViewById(R.id.annonce_start)
    }
}