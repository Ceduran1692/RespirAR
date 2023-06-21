package ar.edu.ort.respirar.ui.views.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel

class StationCarouselAdapter(private val viewModel: StationViewModel,
                             private val stationId: String,
                             private val viewPager2: ViewPager2) : RecyclerView.Adapter<StationCarouselAdapter.ViewHolder>() {


    private var sensores: MutableMap<String, Double?> = mutableMapOf()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val nextPosition = (viewPager2.currentItem + 1) % itemCount
            viewPager2.setCurrentItem(nextPosition, true)
            handler.postDelayed(this, 3000L)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val detailsContainer: LinearLayout = itemView.findViewById(R.id.stationDetailsContainer)
        init {
            viewPager2.isUserInputEnabled = false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.station_details_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sensores = viewModel.getStationSensors(stationId)
        val sensorValues = sensores.entries.toList()

        if (position < sensorValues.size) {
            val sensor = sensorValues[position]
            holder.detailsContainer.removeAllViews()

            generateDetailsViews(sensor, holder.detailsContainer)
        }

        if (sensores.size > 1) {
            holder.itemView.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sensores.size
    }

    fun updateSensores(sensores: MutableMap<String, Double?>) {
        this.sensores.clear()
        this.sensores = sensores
        notifyDataSetChanged()
    }


    private fun generateDetailsViews(sensor: Map.Entry<String, Double?>, parent: ViewGroup) {
        parent.removeAllViews()

        val key = sensor.key
        val value = sensor.value

        if (key == "Temperatura") {
            viewModel.getTemperatureDetails(value, parent, true)
        }

        if (key == "Humedad") {
            viewModel.getHumidityDetails(value, parent, true)
        }

        if (key == "Fiabilidad") {
            viewModel.getReliabilityDetails(value,parent, true)
        }

        if (key == "Precipitaciones"){
            viewModel.getPrecipitationsDetails(value, parent, true)
        }
    }


    fun startCarousel() {
        stopCarousel()
        handler.postDelayed(runnable, 3000L)
    }


    fun stopCarousel() {
        handler.removeCallbacks(runnable)
    }


}
