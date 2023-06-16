package ar.edu.ort.respirar.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.dummie.DummieData
import ar.edu.ort.respirar.databinding.FragmentAboutUsBinding
import ar.edu.ort.respirar.databinding.FragmentStationHistoricoBinding
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import java.util.Calendar


class StationHistoricoFragment : Fragment() {

    private var _binding: FragmentStationHistoricoBinding? = null
    private val binding get() = _binding!!

    private val stationViewModel: StationViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("about us fragment", "on create view")
        _binding = FragmentStationHistoricoBinding.inflate(inflater, container, false)
        val view = binding.root
        initTest()
        return view
    }

    private fun initTest(){

        val fechaActual = Calendar.getInstance().time // Obtener la fecha actual

        val calendar = Calendar.getInstance()
        calendar.time = fechaActual
        calendar.add(Calendar.YEAR, -1) // Restar un año

        val fechaRestada = calendar.time

        println(fechaRestada)

        stationViewModel.historicos.observe(viewLifecycleOwner){
            binding.textView2.text= it.size.toString()
        }

        binding.button.setOnClickListener {
            stationViewModel.getHistoricosById("urn:ngsi-ld:Estacion:est001","temperature")
        }
        binding.button1.setOnClickListener {
            stationViewModel.getHistoricosById("urn:ngsi-ld:Estacion:est001","temperature",fechaRestada,fechaActual)
        }
    }
}