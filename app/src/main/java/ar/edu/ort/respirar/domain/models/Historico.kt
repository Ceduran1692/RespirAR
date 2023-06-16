package ar.edu.ort.respirar.domain.models

import ar.edu.ort.respirar.data.dto.HistoricoDto

data class Historico(
    val recvTime:String,
    val entityId:String,
    val attrName:String,
    val attrValue:String
)

fun HistoricoDto.toDomain()= Historico(recvTime, entityId, attrName, attrValue)
