package ar.edu.ort.respirar.domain.usecases

import android.util.Log
import ar.edu.ort.respirar.data.service.StationService
import ar.edu.ort.respirar.domain.models.CustomStation
import javax.inject.Inject

class GetStationByIdUseCase @Inject constructor(
    private val service:StationService
){
    suspend operator fun invoke(id:String):CustomStation?{
        try{
            Log.i("GetStationByIdUseCase","GetStationByIdUseCase() - init")
            var result:CustomStation?
            result= service.getStationById(id)
            Log.i("GetStationByIdUseCase","result == null: "+ (result == null))

            Log.i("GetStationByIdUseCase","GetStationByIdUseCase() - out")
            return result
        }catch (e:Exception){
            Log.i("GetStationByIdUseCase","Error: GetStationByIdUseCase: "+e.message)
            return null
        }
    }
}
