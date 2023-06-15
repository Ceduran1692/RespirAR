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
        val humedad: TextView = view.findViewById(R.id.humidityValue)
        val fiabilidad: TextView = view.findViewById(R.id.reliabilityValue)
        val precipitaciones: TextView = view.findViewById(R.id.precipitationsValue)
        val property5: TextView = view.findViewById(R.id.property5Value)
        val property6: TextView = view.findViewById(R.id.property6Value)
        val humidityProgressBar: ProgressBar = view.findViewById(R.id.humidityCircularProgressBar)
        val reliabilityProgressBar: ProgressBar = view.findViewById(R.id.reliabilityCircularProgressBar)
        val precipitationsProgressBar: ProgressBar = view.findViewById(R.id.precipitationsCircularProgressBar)
        val property5ProgressBar: ProgressBar = view.findViewById(R.id.property5CircularProgressBar)
        val property6ProgressBar: ProgressBar = view.findViewById(R.id.property6CircularProgressBar)

        humidityProgressBar.max = 100
        reliabilityProgressBar.max = 100
        precipitationsProgressBar.max = 100
        property5ProgressBar.max = 100
        property6ProgressBar.max = 100

        val station = stationViewModel.getStationById(stationId)


        if (station != null) {
            titulo.text = station.titulo
            stationViewModel.getTemperatureDetails(station.temperatura, temperatura, false)
            humedad.text = station.humedad.toString()
            fiabilidad.text = station.reliability.toString()
            precipitaciones.text = 90.toString()
            property5.text = 80.toString()
            property6.text = 20.toString()
            animateCircularProgressBar(humidityProgressBar, station.humedad)
            animateCircularProgressBar(reliabilityProgressBar, station.reliability)
            animateCircularProgressBar(precipitationsProgressBar, 90.1)
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