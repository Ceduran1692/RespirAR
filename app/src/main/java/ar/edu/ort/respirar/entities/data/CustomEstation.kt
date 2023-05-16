package ar.edu.ort.respirar.entities.data

import org.osmdroid.util.GeoPoint

data class CustomEstation(
    val geoPoint: GeoPoint,
    val titulo: String,
    val temperatura: Double,
    val humedad: Double,
    val image: Int
    )
