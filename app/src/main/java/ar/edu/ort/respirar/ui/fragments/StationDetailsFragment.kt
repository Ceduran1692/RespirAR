package ar.edu.ort.respirar.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.FragmentStationDetailsBinding
import ar.edu.ort.respirar.databinding.FragmentStationHistoricoBinding
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonDisposableHandle.parent

@AndroidEntryPoint
class StationDetailsFragment: Fragment(){

    private var view: View? = null
    private var _binding: FragmentStationDetailsBinding? = null
    private val binding get() = _binding!!
    private val stationViewModel: StationViewModel by activityViewModels()
    private var stationId:String? = null
    private var stationName:String? = null
    private lateinit var cardsViews:MutableList<View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Log.i("StationDetailsFragment", "onCreateView() - in")
        if(arguments?.getString("stationId").toString() != null && stationId != null && stationId != arguments?.getString("stationId").toString()){
            _binding = null
        //finishCards(container)
        }
        _binding = FragmentStationDetailsBinding.inflate(inflater, container, false)
        view = binding.root


        Log.i("StationDetailsFragment", "onCreateView() - out")

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initCards()
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
        stationId = arguments?.getString("stationId").toString()
        Log.i("StationDetailsFragment", "initUi() - stationId: "+ stationId)
        binding.property5CircularProgressBar.max= 100
        binding.property6CircularProgressBar.max= 100
    }


    private fun initListeners(){
       onClickDetailListener(binding.stationDetailsTemperature,"temperature","c°",60.0)
       onClickDetailListener(binding.stationDetailsReliability,"reliability","%",100.0)
       onClickDetailListener(binding.stationDetailsPrecipitations,"precipitation","mm",35.0)
       onClickDetailListener(binding.stationDetailsHumidity,"relativeHumidity","%",100.0)

    }

    private fun finishCards(container: ViewGroup?){
        if (container != null) {
            container.removeView(binding.stationDetailsTemperature)
            container.removeView(binding.stationDetailsHumidity)
            container.removeView(binding.stationDetailsPrecipitations)
            container.removeView(binding.stationDetailsReliability)
        }
    }

    private fun initCards(){
        binding.stationDetailsTemperature.addView(LayoutInflater.from(binding.stationDetailsTemperature.context).inflate(R.layout.details_temperature, binding.stationDetailsTemperature, false))
        binding.stationDetailsReliability.addView(LayoutInflater.from(binding.stationDetailsReliability.context).inflate(R.layout.details_reliability, binding.stationDetailsReliability, false))
        binding.stationDetailsPrecipitations.addView(LayoutInflater.from(binding.stationDetailsPrecipitations.context).inflate(R.layout.details_precipitations, binding.stationDetailsPrecipitations, false))
        binding.stationDetailsHumidity.addView(LayoutInflater.from(binding.stationDetailsHumidity.context).inflate(R.layout.details_humidity, binding.stationDetailsHumidity, false))
    }

    private fun initObservers(){
        stationViewModel.isLoading.observe(viewLifecycleOwner, { loading ->
            loadingProgressBar(loading)
        })
        stationViewModel.station.observe(viewLifecycleOwner){station ->
            stationName= station.titulo.toString()
            setTemperatureDetails(station.temperatura)
            setPrecipitationsDetails(station.precipitations)
            setHumidityDetails(station.humedad)
            setReliabilityDetails(station.reliability)
        }
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

    private fun setTemperatureDetails(value:Double?) {
        val temperatureView = binding.stationDetailsTemperature
        val temperatureTextView = temperatureView.findViewById<TextView>(R.id.details_temperature_value)
        val temperatureIconView = temperatureView.findViewById<ImageView>(R.id.details_temperature_icon)
        val temperatureCelsiusView = temperatureView.findViewById<TextView>(R.id.details_temperature_celsius)
        val temperatureTitle = temperatureView.findViewById<TextView>(R.id.details_temperatureTitle)
        temperatureTextView.text = value?.toInt().toString()
        when {
            value != null -> {
                when {
                    value < 10 -> {
                        temperatureTextView.setTextColor(
                            ContextCompat.getColor(
                                binding.stationDetailsTemperature.context,
                                R.color.COLD
                            )
                        )
                        temperatureIconView.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.COLD
                            )
                        )
                        temperatureCelsiusView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.COLD
                            )
                        )
                        temperatureTitle.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.COLD
                            )
                        )
                    }

                    value >= 10 && value < 20 -> {
                        Log.i("StationDetaisFragment","temp color:"+ ContextCompat.getColor(
                            temperatureView.context,
                            R.color.NEUTRAL
                        ) )
                        temperatureTextView.setTextColor(
                            ContextCompat.getColor(
                                temperatureView.context,
                                R.color.NEUTRAL
                            )
                        )
                        temperatureIconView.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.NEUTRAL
                            )
                        )
                        temperatureCelsiusView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.NEUTRAL
                            )
                        )
                        temperatureTitle.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.NEUTRAL
                            )
                        )
                    }

                    value >= 20 && value < 30 -> {
                        temperatureTextView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.WARM
                            )
                        )
                        temperatureIconView.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.WARM
                            )
                        )
                        temperatureCelsiusView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.WARM
                            )
                        )
                        temperatureTitle.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.WARM
                            )
                        )
                    }

                    value >= 30 -> {
                        temperatureTextView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.HOT
                            )
                        )
                        temperatureIconView.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.HOT
                            )
                        )
                        temperatureCelsiusView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.HOT
                            )
                        )
                        temperatureTitle.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.HOT
                            )
                        )
                    }
                }
            }
        }

    }

    private fun setHumidityDetails(value: Double?){
        val humidityView = binding.stationDetailsHumidity
        val humidityTextView = humidityView.findViewById<TextView>(R.id.details_humidity_value)
        val valorPorcentual = value!! * 100
        humidityTextView.text = valorPorcentual.toString() + " %"

        val humidityProgressBar: ProgressBar = humidityView.findViewById(R.id.humidity_circular_ProgressBar)
        humidityProgressBar.max = 100
        humidityProgressBar.progress = valorPorcentual.toInt()

        //parent.addView(humidityView)
    }

    fun setReliabilityDetails(value: Double?){
        val reliabilityView =binding.stationDetailsReliability
        val reliabilityTextView = reliabilityView.findViewById<TextView>(R.id.details_reliability_value)
        val valorPorcentual = value!! * 100
        reliabilityTextView.text = valorPorcentual.toString() + " %"

        val reliabilityProgressBar: ProgressBar = reliabilityView.findViewById(R.id.reliability_circular_ProgressBar)
        reliabilityProgressBar.max = 100
        reliabilityProgressBar.progress = valorPorcentual.toInt()

        //parent.addView(reliabilityView)
    }

    fun setPrecipitationsDetails(value: Double?){
        val precipitationsView = binding.stationDetailsPrecipitations
        val precipitationsTextView = precipitationsView.findViewById<TextView>(R.id.details_precipitations_value)
        precipitationsTextView.text = value.toString()

        //parent.addView(precipitationsView)
    }

    private fun animateCircularProgressBar(progressBar: ProgressBar, progress: Double?) {
        progressBar.progress = 0

        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress!!.toInt())
        animator.duration = 700
        animator.start()
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