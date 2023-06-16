package ar.edu.ort.respirar.data.service.interfaces

import ar.edu.ort.respirar.data.dto.HistoricoDto
import ar.edu.ort.respirar.data.dto.station.StationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date


interface StationServiceInterface {

    @GET("/estaciones")
    suspend fun getAllStation():Response<List<StationDto>>

    @GET("/estacion/")
    suspend fun getStationById(@Query("id") id: String):Response<StationDto>
    @GET("/getHistoricosById/")
    suspend fun getHistoricosById(@Query("id") id: String,
                                  @Query("attr") attr: String): Response<List<HistoricoDto>>

    @GET("/getHistoricosById/")
    suspend fun getHistoricosById(@Query("id") recvTime:String,
                                  @Query("attr") entityId:String,
                                  @Query("minDate") minDate: Date,
                                  @Query("maxDate") maxDate:Date): Response<List<HistoricoDto>>
}