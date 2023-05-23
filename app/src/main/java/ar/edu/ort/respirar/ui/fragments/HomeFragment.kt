package ar.edu.ort.respirar.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.edu.ort.respirar.R

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        val buttonMap = view.findViewById<LinearLayout>(R.id.mapButton)
        val buttonStations = view.findViewById<LinearLayout>(R.id.stationsButton)
        val buttonFavorites = view.findViewById<LinearLayout>(R.id.favoritesButton)
        val buttonAboutUs = view.findViewById<LinearLayout>(R.id.aboutUsButton)

        buttonMap.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
        }
        buttonStations.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_stationListFragment)
        }
        buttonFavorites.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_stationFavoritesListFragment)
        }
        buttonAboutUs.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_aboutUsFragment)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

}
