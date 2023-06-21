package ar.edu.ort.respirar.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.ui.views.adapters.CustomAdapter
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel


class StationFavoritesListFragment : Fragment() {

    private var favoriteStations: MutableList<CustomStation> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private val stationViewModel: StationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        recyclerView = view.findViewById(R.id.stations_recycler)
        adapter = CustomAdapter(stationViewModel,
            StationListFragment.StationPreferences(requireContext())
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.initNavController(findNavController())

        stationViewModel.stationList.observe(viewLifecycleOwner) { stations ->
            val filteredStations  = stations.filter { station ->
                StationListFragment.StationPreferences(requireContext()).isStationFavorite(station.stationId!!)
            }
            favoriteStations = filteredStations.toMutableList()
            adapter.setData(favoriteStations)
        }

        return view
    }

//    private fun filterFavoriteStations() {
//        favoriteStations = stationViewModel.stationList.filter { station ->
//            StationListFragment.StationPreferences(requireContext()).isStationFavorite(station.stationId!!)
//        }
//    }
}


