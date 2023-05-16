package ar.edu.ort.respirar.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import ar.edu.ort.respirar.R


class MapsFragment : Fragment()  {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SharedPreferences
        val sharedPreferences = requireContext().applicationContext.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        val context = requireContext().applicationContext
        Configuration.getInstance().load(context, sharedPreferences)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar mapa
        mapView = view.findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        //Propiedades del mapa
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(12.0)

        //Establecer centro (Buenos Aires)
        val buenosAires = GeoPoint(-34.6037, -58.3816)
        mapView.controller.setCenter(buenosAires)

        //Estaciones prueba
        val estaciones: Array<StationListFragment.Estacion> = StationListFragment.estaciones
        addMarkersOnMap(estaciones)

    }

    fun addMarkersOnMap(estaciones: Array<StationListFragment.Estacion>) {
        for (estacion in estaciones) {
            val latitude = estacion.geoPoint.latitude
            val longitude = estacion.geoPoint.longitude

            val marker = Marker(mapView)
            marker.position = GeoPoint(latitude, longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = estacion.titulo
            marker.snippet = StationListFragment.getDetails(estacion)
            marker.icon = AppCompatResources.getDrawable(requireContext(), estacion.image)
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
    }

}