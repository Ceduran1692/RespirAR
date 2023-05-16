package ar.edu.ort.respirar.activities
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.fragments.StationListFragment

class CustomAdapter(private val currentFragmentType: String) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var data: List<StationListFragment.Estacion> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView = itemView.findViewById(R.id.card_image)
        var itemTitle: TextView = itemView.findViewById(R.id.card_title)
        var itemDetails: TextView = itemView.findViewById(R.id.card_details)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_station_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val card = data[i]
        viewHolder.itemImage.setImageDrawable(ContextCompat.getDrawable(viewHolder.itemView.context, card.image))
        viewHolder.itemTitle.text = card.titulo
        viewHolder.itemDetails.text = StationListFragment.getDetails(card)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(cards: Array<StationListFragment.Estacion>) {
        data = cards.toList()
        notifyDataSetChanged()
    }
}
