package ar.edu.ort.respirar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.activities.CustomAdapter
import org.osmdroid.util.GeoPoint



class StationListFragment : Fragment() {

    data class Estacion(val geoPoint: GeoPoint, val titulo: String, val temperatura: Double, val humedad: Double, val image: Int)
    companion object {
        val estaciones = arrayOf(
            Estacion(
                GeoPoint(-34.670267, -58.370969),
                "Estacion 1",
                25.0,
                60.0,
                R.drawable.baseline_info_black_24
            ),
            Estacion(
                GeoPoint(-34.545278, -58.449722),
                "Estacion 2",
                25.0,
                60.0,
                R.drawable.baseline_info_black_24
            ),
            Estacion(
                GeoPoint(-34.63565, -58.36465),
                "Estacion 3",
                25.0,
                60.0,
                R.drawable.baseline_info_black_24
            ),
            Estacion(
                GeoPoint(-34.652064, -58.440119),
                "Estacion 4",
                25.0,
                60.0,
                R.drawable.baseline_info_black_24
            ),
            Estacion(
                GeoPoint(-34.6675, -58.368611),
                "Estacion 5",
                25.0,
                60.0,
                R.drawable.baseline_info_black_24
            )
        )
        fun getDetails(estacion: Estacion): String =
            "Temperatura: ${estacion.temperatura}°C, Humedad: ${estacion.humedad}%"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stations_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.stations_recycler)
        val adapter = CustomAdapter("StationsFragment")

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        //Pruebas para setear data
//        val mapsFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.main_frame_layout) as? MapsFragment
//        mapsFragment?.addMarkersOnMap(estaciones)
        adapter.setData(estaciones)

        return view
    }




}