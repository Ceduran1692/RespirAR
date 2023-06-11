package ar.edu.ort.respirar.data.models

import org.osmdroid.util.GeoPoint

data class CustomStation(
    val stationId: String,
    val geoPoint: GeoPoint,
    val titulo: String,
    val temperatura: Double?,
    val humedad: Double?,
    val reliability: Double?,
    val image: Int,
    var isFavorite: Boolean = false
    )
