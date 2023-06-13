package ar.edu.ort.respirar.data.service.implementations

import android.util.Log
import ar.edu.ort.respirar.data.dto.station.StationDto
import ar.edu.ort.respirar.data.service.interfaces.StationServiceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}