package ar.edu.ort.respirar.domain.usecases

import android.util.Log
import ar.edu.ort.respirar.data.service.StationService
import ar.edu.ort.respirar.domain.models.Historico
import java.util.Date
import javax.inject.Inject

class GetHistoricosByIdBetweenDatesUseCase @Inject constructor(
    private val service:StationService
) {
    suspend operator fun invoke(stationId:String,attr:String,minDate:Date,maxDate: Date):MutableList<Historico>{
        try{
            Log.i("GetHistoricosByIdBetweenDatesUseCase","GetHistoricosByIdBetweenDatesUseCase() - init")
            var result:MutableList<Historico>
            result= mutableListOf()

            result.addAll(service.getHistoricosById(stationId,attr,minDate,maxDate))
            Log.i("GetHistoricosByIdBetweenDatesUseCase","result.size: "+ result.size)

            Log.i("GetHistoricosByIdBetweenDatesUseCase","GetHistoricosByIdBetweenDatesUseCase() - out")
            return result
        }catch (e:Exception){
            Log.i("GetHistoricosByIdBetweenDatesUseCase","Error: GetHistoricosByIdBetweenDatesUseCase: "+e.message)
            return mutableListOf()
        }
    }

}
