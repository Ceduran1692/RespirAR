package ar.edu.ort.respirar.ui.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.FragmentStationDetailsBinding
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StationDetailsFragment: Fragment(){

    private var view: View? = null
    private var _binding: FragmentStationDetailsBinding? = null
    private val binding get() = _binding!!
    private val stationViewModel: StationViewModel by activityViewModels()
    private var stationId:String? = null
    private var stationName:String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.i("StationDetailsFragment", "onCreateView() - in")

        _binding = FragmentStationDetailsBinding.inflate(inflater, container, false)
        view = binding.root

        Log.i("StationDetailsFragment", "onCreateView() - out")

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        initObservers()
        stationViewModel.getStationById(stationId!!)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("StationDetailsFragment", "onDestroyView() - in")
        _binding = null
        Log.i("StationDetailsFragment", "onDestroyView() - out")
    }
    private fun initUi(){
        stationId = arguments?.getString("stationId")
        Log.i("StationDetailsFragment", "initUi() - stationId: "+ stationId)
    }


    private fun initListeners(){
       onClickDetailListener(binding.stationDetailsTemperature,"temperature","c°",60.0)
       onClickDetailListener(binding.stationDetailsReliability,"reliability","%",100.0)
       onClickDetailListener(binding.stationDetailsPrecipitations,"precipitation","mm",100.0)
       onClickDetailListener(binding.stationDetailsHumidity,"relativeHumidity","%",100.0)
       onClickDetailListener(binding.stationDetailsPm1,"pm1","%",100.0)
       onClickDetailListener(binding.stationDetailsPm10,"pm10","%",100.0)
    }

    private fun initObservers(){
        stationViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            loadingProgressBar(loading)
        }

        stationViewModel.station.observe(viewLifecycleOwner){station ->
            clearCards()
            val titulo: TextView = requireView().findViewById(R.id.stationDetailsTitle)
            titulo.text = station.titulo.toString()
            stationViewModel.getTemperatureDetails(station.temperatura, binding.stationDetailsTemperature, false)
            stationViewModel.getHumidityDetails(station.humedad, binding.stationDetailsHumidity, false)
            stationViewModel.getReliabilityDetails(station.reliability, binding.stationDetailsReliability, false)
            stationViewModel.getPrecipitationsDetails(station.precipitations, binding.stationDetailsPrecipitations, false)
            stationViewModel.getPm1Details(station.pm1, binding.stationDetailsPm1, false)
            stationViewModel.getPm10Details(station.pm10, binding.stationDetailsPm10, false)
        }
    }

    private fun clearCards() {
        binding.stationDetailsTemperature.removeAllViews()
        binding.stationDetailsReliability.removeAllViews()
        binding.stationDetailsPrecipitations.removeAllViews()
        binding.stationDetailsHumidity.removeAllViews()
        binding.stationDetailsPm1.removeAllViews()
        binding.stationDetailsPm10.removeAllViews()
    }

    private fun loadingProgressBar(loading: Boolean) {
        if (loading) {
            binding.pbStationDetails.visibility = View.VISIBLE
            binding.stationDetailsTop.visibility = View.GONE
            binding.gridLayoutStationDetails.visibility = View.GONE
        } else {
            binding.pbStationDetails.visibility = View.GONE
            binding.stationDetailsTop.visibility = View.VISIBLE
            binding.gridLayoutStationDetails.visibility = View.VISIBLE

        }
    }

    private fun onClickDetailListener(view: View, attr:String,metricType:String,metricMaxValue:Double){
        view.setOnClickListener {
            val bundle = Bundle().apply {
                putString("stationId", stationId)
                putString("stationName", stationName)
                putString("attr", attr)
                putString("metricType", metricType)
                putString("metricMaxValue", metricMaxValue.toString())
            }
            findNavController().navigate(R.id.action_stationDetailsFragment_to_stationHistoricoFragment, bundle)

        }
    }


}