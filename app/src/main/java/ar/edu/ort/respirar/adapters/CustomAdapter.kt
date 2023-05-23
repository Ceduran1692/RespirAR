package ar.edu.ort.respirar.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.models.CustomEstation
import ar.edu.ort.respirar.holders.CustomViewHolder
import ar.edu.ort.respirar.ui.fragments.StationListFragment

class CustomAdapter(
        private val currentFragmentType: String,
        private val stationPreferences: StationListFragment.StationPreferences
    ) : RecyclerView.Adapter<CustomViewHolder>() {

    private var data: MutableList<CustomEstation>? = ArrayList<CustomEstation>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_station_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val station = data?.get(position)
        if (station != null) {
            holder.render(station)

            val isFavorite = stationPreferences.isStationFavorite(station.stationId)

            holder.favoriteButton.isChecked = isFavorite
            holder.favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                stationPreferences.setStationFavorite(station.stationId, isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setData(cards: Array<CustomEstation>) {
        data = cards.toMutableList()
        notifyDataSetChanged()
    }

}
