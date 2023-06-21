package ar.edu.ort.respirar.data.service

import android.util.Log
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.domain.models.toDomain
import ar.edu.ort.respirar.data.service.implementations.StationServiceImpl
import ar.edu.ort.respirar.domain.models.Historico
import java.util.Date
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

    suspend fun getHistoricosById(stationId: String, attr: String): List<Historico> {
        Log.i("StationService","getHistoricosById() - init")
        try{
            var response= service.getHistoricosById(stationId,attr)

            Log.i("StationService","response.isEmpty(): "+ response.isEmpty())
            Log.i("StationService","getHistoricosById() - out")
            return response.map { it.toDomain() }
        }catch (e:Exception){
            Log.i("StationService","getHistoricosById() - error: "+ e.message)
            throw Exception("StationService: "+e.message)
        }

    }

    suspend fun getHistoricosById(stationId: String, attr: String,minDate:Date,maxDate:Date): List<Historico> {
        Log.i("StationService","getHistoricosById() - init")
        try{
            var response= service.getHistoricosById(stationId,attr,minDate,maxDate)

            Log.i("StationService","response.isEmpty(): "+ response.isEmpty())
            Log.i("StationService","getHistoricosById() - out")
            return response.map { it.toDomain() }
        }catch (e:Exception){
            Log.i("StationService","getHistoricosById() - error: "+ e.message)
            throw Exception("StationService: "+e.message)
        }

    }
    suspend fun getStationById(id: String): CustomStation? {
        Log.i("StationService","getHistoricosById() - init")
        try{
            var response= service.getStationDtoById(id)

            Log.i("StationService","response == null: "+ (response == null))
            Log.i("StationService","getHistoricosById() - out")
            return response?.toDomain()
        }catch (e:Exception){
            Log.i("StationService","getHistoricosById() - error: "+ e.message)
            throw Exception("StationService: "+e.message)
        }
    }
}