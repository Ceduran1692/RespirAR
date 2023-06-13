package ar.edu.ort.respirar.ui.viewmodels

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.models.CustomStation
import ar.edu.ort.respirar.domain.usecases.GetAllStationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject


@HiltViewModel
class StationViewModel @Inject constructor(
    private val getAllStationUseCase: GetAllStationUseCase
) : ViewModel(){

    val isLoading= MutableLiveData<Boolean>()
    val estaciones = mutableListOf(
        CustomStation(
            "1",
            GeoPoint(-34.670267, -58.370969),
            "Estacion 1",
            25.0,
            60.3,
            70.2,
            R.drawable.baseline_location_on_red_24
        ),
        CustomStation(
            "2",
            GeoPoint(-34.545278, -58.449722),
            "Estacion 2",
            18.3,
            20.3,
            50.6,
            R.drawable.baseline_location_on_red_24
        ),
        CustomStation(
            "3",
            GeoPoint(-34.63565, -58.36465),
            "Estacion 3",
            35.7,
            50.7,
            30.4,
            R.drawable.baseline_location_on_red_24
        ),
        CustomStation(
            "4",
            GeoPoint(-34.652064, -58.440119),
            "Estacion 4",
            12.1,
            60.2,
            90.2,
            R.drawable.baseline_location_on_red_24
        ),
        CustomStation(
            "5",
            GeoPoint(-34.6675, -58.368611),
            "Estacion 5",
            6.7,
            60.3,
            8.1,
            R.drawable.baseline_location_on_red_24
        )
    )

    val stationList= MutableLiveData<MutableList<CustomStation>>()

    fun getStations(){
        Log.i("StationViewModel", "getAllCars() - init")
        isLoading.postValue(true)
        viewModelScope.launch {
            var result= getAllStationUseCase()
            Log.i("CarViewModel", "result.isNotEmpty()= "+result.isNotEmpty())
            if(result.isNotEmpty()) {
                stationList.postValue(result)
            }else{
                if(estaciones.isNotEmpty()) {
                    stationList.postValue(estaciones)
                }
            }
            isLoading.postValue(false)
        }
        Log.i("CarViewModel", "getAllCars() - out")

    }

    fun getStationById(id: String?): CustomStation? {
        return estaciones.find { it.stationId == id }
    }

    fun getStationSensors(stationId: String): MutableMap<String, Double?> {
        val sensors = mutableMapOf<String, Double?>()
        val station = estaciones.find { it.stationId == stationId }
        if (station != null) {
            sensors["Temperatura"] = station.temperatura
            sensors["Humedad"] = station.humedad
            sensors["Fiabilidad"] = station.reliability
        }
        return sensors
    }

    fun getTemperatureDetails(value: Double?, parent: ViewGroup,title: Boolean){
        val temperatureView = LayoutInflater.from(parent.context).inflate(R.layout.details_temperature, parent, false)
        val temperatureTextView = temperatureView.findViewById<TextView>(R.id.details_temperature_value)
        val temperatureIconView = temperatureView.findViewById<ImageView>(R.id.details_temperature_icon)
        temperatureTextView.text = value.toString()
        when {
            value != null -> {
                when {
                    value < 10 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.COLD))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.COLD))
                    }
                    value >= 10 && value < 20 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                    }
                    value >= 20 && value < 30 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.WARM))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.WARM))
                    }
                    value >= 30 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.HOT))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.HOT))
                    }
                }
            }
            else -> {
            }
        }
        if(!title){
            val temperatureTitle = temperatureView.findViewById<TextView>(R.id.details_temperatureTitle)
            temperatureTitle.visibility = View.GONE
        }

        parent.addView(temperatureView)
    }

    fun getHumidityDetails(value: Double?,parent: ViewGroup, title: Boolean){
        val humidityView = LayoutInflater.from(parent.context).inflate(R.layout.details_humidity, parent, false)
        val humidityTextView = humidityView.findViewById<TextView>(R.id.details_humidity_value)
        humidityTextView.text = value.toString() + " %"

        val humidityProgressBar: ProgressBar = humidityView.findViewById(R.id.humidity_circular_ProgressBar)
        humidityProgressBar.max = 100
        humidityProgressBar.progress = value!!.toInt()


        if(!title){
            val humidityTitle = humidityView.findViewById<TextView>(R.id.details_humidityTitle)
            humidityTitle.visibility = View.GONE
        }

        parent.addView(humidityView)
    }

    fun getReliabilityDetails(value: Double?, parent: ViewGroup, title: Boolean){
        val reliabilityView = LayoutInflater.from(parent.context).inflate(R.layout.details_reliability, parent, false)
        val reliabilityTextView = reliabilityView.findViewById<TextView>(R.id.details_reliability_value)
        reliabilityTextView.text = value.toString() + " %"

        val humidityProgressBar: ProgressBar = reliabilityView.findViewById(R.id.reliability_circular_ProgressBar)
        humidityProgressBar.max = 100
        humidityProgressBar.progress = value!!.toInt()

        if(!title){
            val reliabilityTitle = reliabilityView.findViewById<TextView>(R.id.details_reliabilityTitle)
            reliabilityTitle.visibility = View.GONE
        }

        parent.addView(reliabilityView)
    }

}