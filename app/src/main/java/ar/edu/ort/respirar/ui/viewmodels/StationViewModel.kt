package ar.edu.ort.respirar.ui.viewmodels

import android.animation.ObjectAnimator
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
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.domain.models.Historico
import ar.edu.ort.respirar.domain.usecases.GetAllStationUseCase
import ar.edu.ort.respirar.domain.usecases.GetHistoricosByIdBetweenDatesUseCase
import ar.edu.ort.respirar.domain.usecases.GetHistoricosByIdUseCase
import ar.edu.ort.respirar.domain.usecases.GetStationByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class StationViewModel @Inject constructor(
    private val getAllStationUseCase: GetAllStationUseCase,
    private val getHistoricosByIdUseCase: GetHistoricosByIdUseCase,
    private val getStationByIdUseCase: GetStationByIdUseCase,
    private val getHistoricosByIdBetweenDatesUseCase: GetHistoricosByIdBetweenDatesUseCase
    ) : ViewModel(){

    val station= MutableLiveData<CustomStation>()
    val historicos= MutableLiveData<MutableList<Historico>>()
    val isLoading= MutableLiveData<Boolean>()
    val stationList= MutableLiveData<MutableList<CustomStation>>()

    fun getStations(){
        isLoading.postValue(true)
        viewModelScope.launch {
            var result= getAllStationUseCase()
            if(result.isNotEmpty()) {
                stationList.postValue(result)
            }
            isLoading.postValue(false)
        }
    }

    fun getHistoricosById(stationId: String, attr:String){
        Log.i("StationViewModel", "getHistoricosById() - init")
        isLoading.postValue(true)
        viewModelScope.launch {
            var result= getHistoricosByIdUseCase(stationId, attr)
            Log.i("StationViewModel", "result.isNotEmpty()= "+result.isNotEmpty())
            if(result.isNotEmpty()) {
                historicos.postValue(result)
            }
            isLoading.postValue(false)
        }
        Log.i("StationViewModel", "getHistoricosById() - out")

    }

    fun getHistoricosById(stationId: String, attr:String, minDate: Date, maxDate:Date){
        Log.i("StationViewModel", "getHistoricosById() - init")
        isLoading.postValue(true)
        viewModelScope.launch {
            var result= getHistoricosByIdBetweenDatesUseCase(stationId,attr,minDate,maxDate)
            Log.i("StationViewModel", "result.isNotEmpty()= "+result.isNotEmpty())
            if(result.isNotEmpty()) {
                historicos.postValue(result)
            }
            isLoading.postValue(false)
        }
        Log.i("StationViewModel", "getHistoricosById() - out")

    }
//    fun getStationById(id: String){
//        val result= stationList.value?.find { it.stationId == id}
//        if(result == null ) {
//            isLoading.postValue(true)
//            viewModelScope.launch {
//                val result = getStationByIdUseCase(id)
//                if (result != null) {
//                    station.postValue(result!!)
//                }
//                isLoading.postValue(false)
//            }
//        }
//    }

    fun getStationById(id: String) {
        Log.i("StationViewModel", "getStationById() - init")
        var result= stationList.value?.find { it.stationId == id}
        Log.i("StationViewModel", "result= "+ result.toString())
        if(result == null  ) {
            Log.i("StationViewModel", "getStationById() - init")
            isLoading.postValue(true)
            viewModelScope.launch {
                result = getStationByIdUseCase(id)
                if (result != null) {
                    station.postValue(result!!)
                }
                isLoading.postValue(false)

                Log.i("StationViewModel", "result == null " + (result == null))
            }
        }else{
            station.postValue(result!!)
            isLoading.postValue(false)

        }
        Log.i("StationViewModel", "getStationById() - out")

    }

    fun getStationSensors(stationId: String): MutableMap<String, Double?> {
        val sensors = mutableMapOf<String, Double?>()
        val station = stationList.value?.find { it.stationId == stationId }
        if (station != null) {
            sensors["Temperatura"] = station.temperatura
            sensors["Humedad"] = station.humedad
            sensors["Fiabilidad"] = station.reliability
            sensors["Precipitaciones"] = station.precipitations
        }
        return sensors
    }

    fun getTemperatureDetails(value: Double?, parent: ViewGroup,title: Boolean):View{
        val temperatureView = LayoutInflater.from(parent.context).inflate(R.layout.details_temperature, parent, false)
        val temperatureTextView = temperatureView.findViewById<TextView>(R.id.details_temperature_value)
        val temperatureIconView = temperatureView.findViewById<ImageView>(R.id.details_temperature_icon)
        val temperatureCelsiusView = temperatureView.findViewById<TextView>(R.id.details_temperature_celsius)
        val temperatureTitle = temperatureView.findViewById<TextView>(R.id.details_temperatureTitle)
        temperatureTextView.text = value?.toInt().toString()
        when {
            value != null -> {
                when {
                    value < 10 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.COLD))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.COLD))
                        temperatureCelsiusView.setTextColor(ContextCompat.getColor(parent.context, R.color.COLD))
                        temperatureTitle.setTextColor(ContextCompat.getColor(parent.context, R.color.COLD))
                    }
                    value >= 10 && value < 20 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                        temperatureCelsiusView.setTextColor(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                        temperatureTitle.setTextColor(ContextCompat.getColor(parent.context, R.color.NEUTRAL))
                    }
                    value >= 20 && value < 30 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.WARM))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.WARM))
                        temperatureCelsiusView.setTextColor(ContextCompat.getColor(parent.context, R.color.WARM))
                        temperatureTitle.setTextColor(ContextCompat.getColor(parent.context, R.color.WARM))
                    }
                    value >= 30 -> {
                        temperatureTextView.setTextColor(ContextCompat.getColor(parent.context, R.color.HOT))
                        temperatureIconView.setColorFilter(ContextCompat.getColor(parent.context, R.color.HOT))
                        temperatureCelsiusView.setTextColor(ContextCompat.getColor(parent.context, R.color.HOT))
                        temperatureTitle.setTextColor(ContextCompat.getColor(parent.context, R.color.HOT))
                    }
                }
            }
            else -> {
            }
        }
        if(!title){
            temperatureTitle.visibility = View.GONE
        }

        parent.addView(temperatureView)
        return temperatureView
    }
    fun getHumidityDetails(value: Double?,parent: ViewGroup, title: Boolean):View{
        val humidityView = LayoutInflater.from(parent.context).inflate(R.layout.details_humidity, parent, false)
        val humidityTextView = humidityView.findViewById<TextView>(R.id.details_humidity_value)
        val valorPorcentual = value!! * 100
        humidityTextView.text = valorPorcentual.toString() + " %"

        val humidityProgressBar: ProgressBar = humidityView.findViewById(R.id.humidity_circular_ProgressBar)
        humidityProgressBar.max = 100
        humidityProgressBar.progress = valorPorcentual.toInt()


        if(!title){
            val humidityTitle = humidityView.findViewById<TextView>(R.id.details_humidityTitle)
            humidityTitle.visibility = View.GONE
            animateCircularProgressBar(humidityProgressBar, valorPorcentual)
        }

        parent.addView(humidityView)
        return humidityView
    }

    fun getReliabilityDetails(value: Double?, parent: ViewGroup, title: Boolean):View{
        val reliabilityView = LayoutInflater.from(parent.context).inflate(R.layout.details_reliability, parent, false)
        val reliabilityTextView = reliabilityView.findViewById<TextView>(R.id.details_reliability_value)
        val valorPorcentual = value!! * 100
        reliabilityTextView.text = valorPorcentual.toString() + " %"

        val reliabilityProgressBar: ProgressBar = reliabilityView.findViewById(R.id.reliability_circular_ProgressBar)
        reliabilityProgressBar.max = 100
        reliabilityProgressBar.progress = valorPorcentual.toInt()

        if(!title){
            val reliabilityTitle = reliabilityView.findViewById<TextView>(R.id.details_reliabilityTitle)
            reliabilityTitle.visibility = View.GONE
            animateCircularProgressBar(reliabilityProgressBar, valorPorcentual)
        }

        parent.addView(reliabilityView)
        return reliabilityView
    }

    fun getPrecipitationsDetails(value: Double?, parent: ViewGroup, title: Boolean):View{
        val precipitationsView = LayoutInflater.from(parent.context).inflate(R.layout.details_precipitations, parent, false)
        val precipitationsTextView = precipitationsView.findViewById<TextView>(R.id.details_precipitations_value)
        precipitationsTextView.text = value.toString()

        if(!title){
            val precipitationsTitle = precipitationsView.findViewById<TextView>(R.id.details_precipitationsTitle)
            precipitationsTitle.visibility = View.GONE
        }

        parent.addView(precipitationsView)
        return precipitationsView
    }
/*
    fun getPm1Details(value: Double?, parent: ViewGroup, title: Boolean){
        val pm1View = LayoutInflater.from(parent.context).inflate(R.layout.details_pm1, parent, false)
        val pm1TextView = pm1View.findViewById<TextView>(R.id.details_pm1_value)
        pm1TextView.text = value.toString()

        val pm1ProgressBar: ProgressBar = pm1View.findViewById(R.id.pm1_circular_ProgressBar)
        pm1ProgressBar.max = 100
        pm1ProgressBar.progress = value!!.toInt()

        if(!title){
            val pm1Title = pm1View.findViewById<TextView>(R.id.details_pm1Title)
            pm1Title.visibility = View.GONE
        }

        parent.addView(pm1View)
    }

    fun getPm10Details(value: Double?, parent: ViewGroup, title: Boolean){
        val pm10View = LayoutInflater.from(parent.context).inflate(R.layout.details_pm1, parent, false)
        val pm10TextView = pm10View.findViewById<TextView>(R.id.details_pm1_value)
        pm10TextView.text = value.toString()


        val pm10ProgressBar: ProgressBar = pm10View.findViewById(R.id.pm1_circular_ProgressBar)
        pm10ProgressBar.max = 100
        pm10ProgressBar.progress = value!!.toInt()

        if(!title){
            val pm10Title = pm10View.findViewById<TextView>(R.id.details_pm1Title)
            pm10Title.visibility = View.GONE
        }

        parent.addView(pm10View)
    }
*/
    private fun animateCircularProgressBar(progressBar: ProgressBar, progress: Double?) {
        progressBar.progress = 0

        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress!!.toInt())
        animator.duration = 700
        animator.start()
    }


}