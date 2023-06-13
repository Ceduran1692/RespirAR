package ar.edu.ort.respirar.data.service

import android.util.Log
import ar.edu.ort.respirar.data.models.CustomStation
import ar.edu.ort.respirar.data.models.toDomain
import ar.edu.ort.respirar.data.service.implementations.StationServiceImpl
import javax.inject.Inject

class StationService @Inject constructor(
    private val service: StationServiceImpl
) {
    suspend fun getAllStation(): List<CustomStation> {
        Log.i("StationService","getAllStation() - init")

        try{
            var response= service.getAllStation()

            Log.i("StationService","response.isEmpty(): "+ response.isEmpty())
            Log.i("StationService","getAllStation() - out")
            return response.map { it.toDomain() }
        }catch (e:Exception){
            Log.i("StationService","getAllStation() - error: "+ e.message)
            return mutableListOf()
        }


    }
}