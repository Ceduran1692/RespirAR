package ar.edu.ort.respirar.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.adapters.CustomAdapter
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel


class StationListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var stationViewModel: StationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        stationViewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        recyclerView = view.findViewById<RecyclerView>(R.id.stations_recycler)
        adapter = CustomAdapter(stationViewModel, StationPreferences(requireContext()))

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val navController = findNavController()
        adapter.initNavController(navController)

        adapter.setData(stationViewModel.estaciones)

        return view
    }

    class StationPreferences(context: Context) {
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("StationPreferences", Context.MODE_PRIVATE)

        fun isStationFavorite(stationId: String): Boolean {
            return sharedPreferences.getBoolean(stationId, false)
        }

        fun setStationFavorite(stationId: String, isFavorite: Boolean) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(stationId, isFavorite)
            editor.apply()
        }
    }

}