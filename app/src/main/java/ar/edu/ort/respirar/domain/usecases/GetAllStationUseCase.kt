package ar.edu.ort.respirar.domain.usecases

import android.util.Log
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.data.service.StationService
import javax.inject.Inject

class GetAllStationUseCase  @Inject constructor(
    private val service:StationService
) {
    suspend operator fun invoke():MutableList<CustomStation>{
        try{
            Log.i("GetAllCarsUseCase","GetAllCarsUseCase() - init")
            var result:MutableList<CustomStation>
            result= mutableListOf()

            result.addAll(service.getAllStation())
            Log.i("GetAllCarsUseCase","response.size: "+ result.size)

            Log.i("GetAllCarsUseCase","GetAllCarsUseCase() - out")
            return result
        }catch (e:Exception){
            Log.i("GetAllCarsUseCase","Error: GetAllCarsUseCase: "+e.message)
            return mutableListOf()
        }
    }
}