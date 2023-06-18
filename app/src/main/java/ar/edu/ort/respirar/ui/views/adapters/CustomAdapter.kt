package ar.edu.ort.respirar.ui.views.adapters
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.ui.views.holders.CustomViewHolder
import ar.edu.ort.respirar.ui.views.fragments.StationListFragment
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel

class CustomAdapter(
    private val viewModel: StationViewModel,
    private val stationPreferences: StationListFragment.StationPreferences
) : RecyclerView.Adapter<CustomViewHolder>() {

    private var data: MutableList<CustomStation>? = ArrayList()
    private lateinit var navController : NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_station_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val station = data?.get(position)
        if (station != null) {
            holder.render(station)

            val isFavorite = stationPreferences.isStationFavorite(station.stationId!!)

            holder.favoriteButton.isChecked = isFavorite
            holder.favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                stationPreferences.setStationFavorite(station.stationId, isChecked)
            }

            holder.setItemClickListener { clickedStation ->
                val bundle = Bundle().apply {
                    putString("stationId", clickedStation?.stationId)
                }

                navController.navigate(R.id.stationDetailsFragment, bundle)
            }

            val carouselAdapter = StationCarouselAdapter(viewModel, station.stationId, holder.viewPager)
            carouselAdapter.updateSensores(viewModel.getStationSensors(station.stationId))
            holder.viewPager.adapter = carouselAdapter
            Log.d("ADAPTER EN CUSTOM", "ITEM COUNT: $carouselAdapter.itemCount")
            if (carouselAdapter.itemCount > 1) {
                carouselAdapter.startCarousel()
            } else {
                carouselAdapter.stopCarousel()
            }
        }

    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setData(cards: MutableList<CustomStation>) {
        data?.clear()
        data = cards.toMutableList()
        notifyDataSetChanged()
    }

    fun initNavController(navController: NavController) {
        this.navController = navController
    }
    override fun onViewDetachedFromWindow(holder: CustomViewHolder) {
        val carouselAdapter = holder.viewPager.adapter as? StationCarouselAdapter
        carouselAdapter?.stopCarousel()
    }


}
