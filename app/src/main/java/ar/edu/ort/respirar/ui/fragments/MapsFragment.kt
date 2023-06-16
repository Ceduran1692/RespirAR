package ar.edu.ort.respirar.ui.fragments

import CustomInfoWindow
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import org.osmdroid.views.overlay.OverlayManager

class MapsFragment : Fragment()  {

    private lateinit var mapView: MapView
    private lateinit var overlayManager: OverlayManager
    private val stationViewModel: StationViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SharedPreferences
        val sharedPreferences = requireContext().applicationContext.getSharedPreferences("MapPreferences", Context.MODE_PRIVATE)

        val context = requireContext().applicationContext

        Configuration.getInstance().load(context, sharedPreferences)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar mapa
        mapView = view.findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        overlayManager = mapView.overlayManager

        //Propiedades del mapa
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(12.0)

        //Establecer centro (Buenos Aires)
        val buenosAires = GeoPoint(-34.6037, -58.3816)
        mapView.controller.setCenter(buenosAires)

        //Estaciones prueba
        //stationViewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        //val estaciones = stationViewModel.stationList.
        stationViewModel.stationList.observe(viewLifecycleOwner) { stations ->
            addMarkersOnMap(stations)

        }

        stationViewModel.getStations()
//        mapView.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                closeInfoWindows()
//            }
//            false
//        }

        val searchButton = requireActivity().findViewById<ImageButton>(R.id.search_button)
        val searchView = requireActivity().findViewById<SearchView>(R.id.search_view)
        val toolbarCenter = requireActivity().findViewById<ImageView>(R.id.toolbar_center)

        searchButton.visibility = View.VISIBLE

        searchButton.setOnClickListener {
            if (searchView.visibility == View.GONE) {
                searchView.isIconified = false
                searchView.visibility = View.VISIBLE
                toolbarCenter.visibility = View.GONE
                searchView.requestFocus()
            } else {
                searchView.visibility = View.GONE
                toolbarCenter.visibility = View.VISIBLE
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMarkerByTitle(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener {
            searchView.visibility = View.GONE
            toolbarCenter.visibility = View.VISIBLE
            true
        }
    }

    private fun searchMarkerByTitle(title: String) {
        val overlayItems = overlayManager.overlays()
        for (overlay in overlayItems) {
            if (overlay is Marker && overlay.title == title) {
                val markerPosition = overlay.position
                mapView.controller.animateTo(markerPosition, 15.0, 2000L)
                overlay.showInfoWindow()
                break
            }
        }
    }
    private fun closeInfoWindows() {
        val overlayItems = mapView.overlays
        for (overlay in overlayItems) {
            if (overlay is Marker) {
                overlay.closeInfoWindow()
            }
        }
    }
    fun addMarkersOnMap(estaciones: MutableList<CustomStation>) {

        for (estacion in estaciones) {
            val latitude = estacion.geoPoint?.latitude
            val longitude = estacion.geoPoint?.longitude

            val marker = Marker(mapView)
            marker.position = GeoPoint(latitude!!, longitude!!)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = estacion.titulo
            marker.icon = AppCompatResources.getDrawable(requireContext(), estacion.image)

            marker.infoWindow = CustomInfoWindow(stationViewModel, mapView, estacion)

            marker.setOnMarkerClickListener { _, _ ->
                mapView.controller.animateTo(marker.position, 15.0, 2000L)
                marker.showInfoWindow()
                true
            }

            marker.dragOffset

            overlayManager.add(marker)
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

        val searchButton = requireActivity().findViewById<ImageButton>(R.id.search_button)
        val searchView = requireActivity().findViewById<SearchView>(R.id.search_view)
        searchButton.visibility = View.GONE
        searchView.visibility = View.GONE

        mapView.onDetach()
    }

}