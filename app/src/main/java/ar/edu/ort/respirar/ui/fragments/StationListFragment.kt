package ar.edu.ort.respirar.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.adapters.CustomAdapter
import ar.edu.ort.respirar.data.models.CustomEstation
import org.osmdroid.util.GeoPoint



class StationListFragment : Fragment() {

    companion object {
        val estaciones = arrayOf(
            CustomEstation(
                "1",
                GeoPoint(-34.670267, -58.370969),
                "Estacion 1",
                25.0,
                60.0,
                R.drawable.baseline_location_on_red_24
            ),
            CustomEstation(
                "2",
                GeoPoint(-34.545278, -58.449722),
                "Estacion 2",
                25.0,
                60.0,
                R.drawable.font_awesome_regular
            ),
            CustomEstation(
                "3",
                GeoPoint(-34.63565, -58.36465),
                "Estacion 3",
                25.0,
                60.0,
                R.drawable.baseline_location_on_red_24
            ),
            CustomEstation(
                "4",
                GeoPoint(-34.652064, -58.440119),
                "Estacion 4",
                25.0,
                60.0,
                R.drawable.baseline_location_on_red_24
            ),
            CustomEstation(
                "5",
                GeoPoint(-34.6675, -58.368611),
                "Estacion 5",
                25.0,
                60.0,
                R.drawable.baseline_location_on_red_24
            )
        )
        fun getDetails(CustomEstation: CustomEstation): String =
            "Temperatura: ${CustomEstation.temperatura}°C, Humedad: ${CustomEstation.humedad}%"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.stations_recycler)
        adapter = CustomAdapter("StationsFragment", StationPreferences(requireContext()))

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setData(estaciones)

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