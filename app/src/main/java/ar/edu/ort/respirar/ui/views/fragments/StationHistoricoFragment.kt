package ar.edu.ort.respirar.ui.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.FragmentStationHistoricoBinding
import ar.edu.ort.respirar.domain.models.Historico
import ar.edu.ort.respirar.ui.viewmodels.StationViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.util.Calendar


class StationHistoricoFragment : Fragment() {

    private var _binding: FragmentStationHistoricoBinding? = null
    private val binding get() = _binding!!

    private val stationViewModel: StationViewModel by activityViewModels()

    private lateinit var attr:String
    private lateinit var stationId:String
    private lateinit var stationName:String
    private lateinit var metricType:String
    private lateinit var metricMaxValue:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStationHistoricoBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
        stationViewModel.getHistoricosById(stationId,attr)
    }

    private fun initObserver(){
        stationViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            loadingProgressBar(loading)
        }

        stationViewModel.historicos.observe(viewLifecycleOwner){
            setGraph(it)
        }
    }

    private fun initUI(){

        stationName=arguments?.getString("stationName").toString()
        stationId= arguments?.getString("stationId").toString()
        attr= arguments?.getString("attr").toString()
        metricType= arguments?.getString("metricType").toString()
        metricMaxValue= arguments?.getString("metricMaxValue").toString()

        val fechaActual = Calendar.getInstance().time

        val calendar = Calendar.getInstance()
        calendar.time = fechaActual
        calendar.add(Calendar.YEAR, -1)

        val fechaRestada = calendar.time

        println(fechaRestada)

    }

    private fun setGraph(historicos: MutableList<Historico>){

        val chartModel : AAChartModel = generateChartModel(historicos)

        binding.aaChartView.aa_drawChartWithChartModel(chartModel)

    }

    private fun generateChartModel(historicos:MutableList<Historico>): AAChartModel {
        var attrArray:MutableList<Double> = mutableListOf(0.0)
        var dateArray:MutableList<String> = mutableListOf("")
        loadData(historicos,attrArray,dateArray,metricType)

        val customAttr = getCustomAttrValue(attr)


        val model:AAChartModel= AAChartModel()
            .chartType(AAChartType.Line)
            .title("Datos Historicos")
            .subtitle(customAttr)
            .backgroundColor("#ffffff")
            .dataLabelsEnabled(true)
            .yAxisTitle(metricType)
            .axesTextColor("#00965A")
            .yAxisMax(metricMaxValue.toDouble())
            .yAxisLabelsEnabled(true)
            .categories(dateArray.toTypedArray())
            .series(arrayOf(
                AASeriesElement()
                    .name(customAttr)
                    .color("#7692e4")
                    .allowPointSelect(true)
                    .lineWidth(3f)
                    .data(attrArray.toTypedArray()),
            )
            )
            .colorsTheme(arrayOf("#7692e4"))
        model.legendEnabled(false)

        return model
    }

    private fun loadData(historicos: MutableList<Historico>, tempArray: MutableList<Double>?, dateArray: MutableList<String>?,metricType:String) {
        historicos.forEach(){hist->
            if(metricType == "%") tempArray?.add(hist.attrValue.toDouble()* 100)
            else tempArray?.add(hist.attrValue.toDouble())
            dateArray?.add(hist.recvTime)
        }

    }

    private fun loadingProgressBar(loading: Boolean) {
        if (loading) {
            binding.pbRvHistoricList.visibility = View.VISIBLE
            binding.graph.visibility = View.GONE
        } else {
            binding.pbRvHistoricList.visibility = View.GONE
            binding.graph.visibility = View.VISIBLE
        }
    }

    private fun getCustomAttrValue(attr: String): String {
        return when (attr) {
            "temperature" -> getString(R.string.sensor_temperature)
            "relativeHumidity" -> getString(R.string.sensor_humidity)
            "reliability" -> getString(R.string.sensor_reliability)
            "precipitation" -> getString(R.string.sensor_precipitations)
            "pm1" -> getString(R.string.sensor_pm1)
            "pm10" -> getString(R.string.sensor_pm10)
            else -> "Historico"
        }
    }
}