package ar.edu.ort.respirar.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.entities.data.CustomEstation
import ar.edu.ort.respirar.fragments.StationListFragment
import ar.edu.ort.respirar.holders.CustomViewHolder

class CustomAdapter(private val currentFragmentType: String) : RecyclerView.Adapter<CustomViewHolder>() {

    private var data: List<CustomEstation> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView = itemView.findViewById(R.id.card_image)
        var itemTitle: TextView = itemView.findViewById(R.id.card_title)
        var itemDetails: TextView = itemView.findViewById(R.id.card_details)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_station_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, i: Int) {
        val card = data[i]

        holder.render(card)
        /*
        holder.itemImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, card.image))
        holder.itemTitle.text = card.titulo
        holder.itemDetails.text = StationListFragment.getDetails(card)
        */
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(cards: Array<CustomEstation>) {
        data = cards.toList()
        notifyDataSetChanged()
    }
}
