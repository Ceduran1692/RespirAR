package ar.edu.ort.respirar.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationDetailsFragment: Fragment(){

    private val stationViewModel: StationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_station_details, container, false)

        val stationId = arguments?.getString("stationId")

        val titulo: TextView = view.findViewById(R.id.stationDetailsTitle)
        val temperatura: LinearLayout = view.findViewById(R.id.stationDetailsTemperature)
        val humedad: LinearLayout = view.findViewById(R.id.stationDetailsHumidity)
        val fiabilidad:  LinearLayout = view.findViewById(R.id.stationDetailsReliability)
        val precipitaciones:  LinearLayout = view.findViewById(R.id.stationDetailsPrecipitations)
        val property5: TextView = view.findViewById(R.id.property5Value)
        val property6: TextView = view.findViewById(R.id.property6Value)
        val property5ProgressBar: ProgressBar = view.findViewById(R.id.property5CircularProgressBar)
        val property6ProgressBar: ProgressBar = view.findViewById(R.id.property6CircularProgressBar)

        property5ProgressBar.max = 100
        property6ProgressBar.max = 100

        val station = stationViewModel.getStationById(stationId)


        if (station != null) {
            titulo.text = station.titulo
            stationViewModel.getTemperatureDetails(station.temperatura, temperatura, false)
            stationViewModel.getHumidityDetails(station.humedad, humedad, false)
            stationViewModel.getReliabilityDetails(station.reliability, fiabilidad, false)
            stationViewModel.getPrecipitationsDetails(station.precipitations, precipitaciones, false)
            property5.text = 80.toString()
            property6.text = 20.toString()
            animateCircularProgressBar(property5ProgressBar, 80.3)
            animateCircularProgressBar(property6ProgressBar, 20.1)
        }

        return view
    }

    private fun animateCircularProgressBar(progressBar: ProgressBar, progress: Double?) {
        progressBar.progress = 0

        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress!!.toInt())
        animator.duration = 700
        animator.start()
    }

}