package ar.edu.ort.respirar.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.adapters.CustomAdapter
import ar.edu.ort.respirar.data.models.CustomStation
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel


class StationFavoritesListFragment : Fragment() {

    private var favoriteStations: List<CustomStation> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var stationViewModel: StationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        stationViewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        recyclerView = view.findViewById(R.id.stations_recycler)
        adapter = CustomAdapter(stationViewModel, StationListFragment.StationPreferences(requireContext()))

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.initNavController(findNavController())
        filterFavoriteStations()
        adapter.setData(favoriteStations.toTypedArray())

        return view
    }

    private fun filterFavoriteStations() {
        favoriteStations = stationViewModel.estaciones.filter { station ->
            StationListFragment.StationPreferences(requireContext()).isStationFavorite(station.stationId)
        }
    }
}


