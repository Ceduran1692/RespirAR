package ar.edu.ort.respirar.data.models

import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.dto.station.StationDto
import org.osmdroid.util.GeoPoint

data class CustomStation(
    val stationId: String?,
    val geoPoint: GeoPoint?,
    val titulo: String?,
    val temperatura: Double?,
    val humedad: Double?,
    val reliability: Double?,
    val image: Int= 0,
    var isFavorite: Boolean? = false
    )

fun StationDto.toDomain()= CustomStation(
    stationId = id,
    geoPoint = setLocation(location?.value),
    titulo = name?.value,
    temperatura = temperature?.value,
    humedad = relativeHumidity?.value,
    reliability= reliability?.value,
    image = setImage()
)

fun setImage():Int{
    return R.drawable.baseline_location_on_red_24
}

fun setLocation(coord:List<Double>?):GeoPoint?{
    if (coord != null) {
        return GeoPoint(coord.first()!!,coord.last()!!)
    }
    return null
}