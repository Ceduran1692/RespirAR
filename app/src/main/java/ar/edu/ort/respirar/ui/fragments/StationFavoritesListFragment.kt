package ar.edu.ort.respirar.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.adapters.CustomAdapter
import ar.edu.ort.respirar.data.models.CustomEstation
import ar.edu.ort.respirar.ui.fragments.StationListFragment.Companion.estaciones


class StationFavoritesListFragment : Fragment() {

    private var favoriteStations: List<CustomEstation> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        recyclerView = view.findViewById(R.id.stations_recycler)
        adapter = CustomAdapter("StationsFragment",
            StationListFragment.StationPreferences(requireContext())
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        filterFavoriteStations()
        adapter.setData(favoriteStations.toTypedArray())

        return view
    }

    private fun filterFavoriteStations() {
        favoriteStations = estaciones.filter { station ->
            StationListFragment.StationPreferences(requireContext()).isStationFavorite(station.stationId)
        }
    }
}


