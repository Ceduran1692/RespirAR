package ar.edu.ort.respirar.data.service.interfaces

import ar.edu.ort.respirar.data.dto.station.StationDto
import retrofit2.Response
import retrofit2.http.GET


interface StationServiceInterface {

    @GET("/v2/entities/")
    suspend fun getAllStation():Response<List<StationDto>>

}