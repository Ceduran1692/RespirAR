package ar.edu.ort.respirar.ui.views.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.ort.respirar.ui.views.adapters.CustomAdapter
import ar.edu.ort.respirar.databinding.FragmentStationsListBinding
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel


class StationListFragment : Fragment() {

    private var _binding: FragmentStationsListBinding?=null
    private val binding get()= _binding!!
    private lateinit var adapter: CustomAdapter
    private val stationViewModel: StationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding= FragmentStationsListBinding.inflate(inflater,container,false)
        val view = binding.root
        stationViewModel.getStations()

        stationViewModel.stationList.observe(viewLifecycleOwner) { stations ->
            adapter.setData(stations)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initVmCarList()
        initRecyclerView()

    }


    private fun initRecyclerView(){

        binding.stationsRecycler.layoutManager = LinearLayoutManager(requireContext())


        adapter = CustomAdapter(stationViewModel, StationPreferences(requireContext()))
        val navController = findNavController()
        adapter.initNavController(navController)
        binding.stationsRecycler.adapter = adapter

    }

    private fun initObservers() {
        stationViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            loadingProgressBar(loading)
        }

        stationViewModel.stationList.observe(viewLifecycleOwner) { cars ->
            adapter.setData(cars.toMutableList())
        }
    }

    private fun initVmCarList(){
        stationViewModel.getStations()

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

    private fun loadingProgressBar(loading: Boolean) {
        if (loading) {
            binding.pbRvStationList.visibility = View.VISIBLE
            binding.stationsRecycler.visibility = View.GONE
        } else {
            binding.pbRvStationList.visibility = View.GONE
            binding.stationsRecycler.visibility = View.VISIBLE
        }
    }


}