package ar.edu.ort.respirar.data.service.implementations

import android.util.Log
import ar.edu.ort.respirar.data.dto.HistoricoDto
import ar.edu.ort.respirar.data.dto.station.StationDto
import ar.edu.ort.respirar.data.service.interfaces.StationServiceInterface
import ar.edu.ort.respirar.domain.models.CustomStation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class StationServiceImpl @Inject constructor(
    private val service:StationServiceInterface
) {
    suspend fun getAllStation(): List<StationDto> {
        Log.i("StationServiceImpl", "getAllStation() - init")

        return withContext(Dispatchers.IO) {
            val response = service.getAllStation()
            response.body() ?: emptyList()
        }
    }

    suspend fun getHistoricosById(stationId: String, attr: String): List<HistoricoDto> {
        Log.i("StationServiceImpl", "getHistoricosById() - init")

        return withContext(Dispatchers.IO) {
            val response = service.getHistoricosById(stationId, attr)
            response.body() ?: emptyList()
        }
    }

    suspend fun getHistoricosById(stationId: String, attr: String, minDate:Date,maxDate: Date): List<HistoricoDto> {
        Log.i("StationServiceImpl", "getHistoricosById() - init")

        val format = SimpleDateFormat("mm/dd/yyyy")
        var minDateFormat = format.format(minDate)
        var maxDateFormat= format.format(maxDate)

        var minDateParse= format.parse(minDateFormat)
        var maxDateParse= format.parse(maxDateFormat)

        return withContext(Dispatchers.IO) {
            val response = service.getHistoricosById(stationId, attr,minDateParse,maxDateParse)
            response.body() ?: emptyList()
        }
    }
    suspend fun getStationDtoById(id: String): StationDto? {
        Log.i("StationServiceImpl", "getHistoricosById() - init")

        return withContext(Dispatchers.IO) {
            val response = service.getStationById(id)
            response.body() ?: null
        }
    }
}