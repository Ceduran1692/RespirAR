package ar.edu.ort.respirar.domain.usecases

import android.util.Log
import ar.edu.ort.respirar.domain.models.CustomStation
import ar.edu.ort.respirar.data.service.StationService
import ar.edu.ort.respirar.domain.models.Historico
import javax.inject.Inject


class GetHistoricosByIdUseCase @Inject constructor(
    private val service: StationService
){
    suspend operator fun invoke(stationId:String,attr:String):MutableList<Historico>{
        try{
            Log.i("GetHistoricosByIdUseCase","GetHistoricosByIdUseCase() - init")
            var result:MutableList<Historico>
            result= mutableListOf()

            result.addAll(service.getHistoricosById(stationId,attr))
            Log.i("GetHistoricosByIdUseCase","result.size: "+ result.size)

            Log.i("GetHistoricosByIdUseCase","GetHistoricosByIdUseCase() - out")
            return result
        }catch (e:Exception){
            Log.i("GetHistoricosByIdUseCase","Error: GetHistoricosByIdUseCase: "+e.message)
            return mutableListOf()
        }
    }
}
