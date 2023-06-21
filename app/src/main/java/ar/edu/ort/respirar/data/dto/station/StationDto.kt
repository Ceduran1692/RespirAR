package ar.edu.ort.respirar.data.dto.station

data class StationDto(
    var dateObserved: DateObserved?,
    var id: String?,
    var location: Location?,
    var name: Name?,
    var pm1: Pm1?,
    var pm10: Pm10?,
    var pm25: Pm1?,
    var precipitation: Precipitation?,
    var relativeHumidity: RelativeHumidity?,
    var reliability: Reliability?,
    var temperature: Temperature?,
    var type: String?
)