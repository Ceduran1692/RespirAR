package ar.edu.ort.respirar.data.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class HistoricoDto(
    @SerializedName("recvTime") val recvTime:String,
    @SerializedName("entityId") val entityId:String,
    @SerializedName("attrName") val attrName:String,
    @SerializedName("attrValue") val attrValue:String
)
