import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.adapters.StationCarouselAdapter
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel

class CustomInfoWindow(
    private val viewModel: StationViewModel,
    private val mapView: MapView,
    private val estacion: CustomStation
) : InfoWindow(R.layout.map_info_window, mapView), View.OnClickListener {

    private val handler = Handler(Looper.getMainLooper())
    private var viewFlipper: ViewFlipper? = null

    private lateinit var viewPager2: ViewPager2

    override fun onOpen(item: Any) {
        closeAllInfoWindowsOn(mapView)

        val windowLayout = mView.findViewById<View>(R.id.map_window)
        windowLayout.findViewById<TextView>(R.id.titleMapWindow).text = estacion.titulo
//        windowLayout.findViewById<TextView>(R.id.mapTemperature).text = estacion.temperatura.toString()
//        windowLayout.findViewById<TextView>(R.id.mapHumidity).text = estacion.humedad.toString()
//
//        viewFlipper = mView.findViewById(R.id.viewFlipper)
//
//        viewFlipper?.let {
//            handler.postDelayed({
//                it.showNext()
//                flipperAnimation()
//            }, 3000)
//        }
        viewPager2 = mView.findViewById(R.id.viewPager2MapInfoWindow)

        val carouselAdapter = StationCarouselAdapter(viewModel, estacion.stationId!!, viewPager2)
        carouselAdapter.updateSensores(viewModel.getStationSensors(estacion.stationId))
        viewPager2.adapter = carouselAdapter
        if (carouselAdapter.itemCount > 1) {
            carouselAdapter.startCarousel()
        }

        windowLayout.setOnClickListener(this)
    }

    override fun onClose() {
        val carouselAdapter = viewPager2.adapter as? StationCarouselAdapter
        carouselAdapter?.stopCarousel()
        closeAllInfoWindowsOn(mapView)
    }

    override fun onClick(v: View?) {
        val activity = mapView.context as? AppCompatActivity
        val navController = activity?.findNavController(R.id.nav_host_fragment)

        val bundle = Bundle().apply {
            putString("stationId", estacion.stationId)
        }

        navController?.navigate(R.id.action_mapsFragment_to_stationDetailsFragment, bundle)
    }

    private fun flipperAnimation() {
        viewFlipper?.let {
            handler.postDelayed({
                it.showNext()
                flipperAnimation()
            }, 2000)
        }
    }
}
