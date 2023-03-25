package com.example.weatherapplication.model

data class Data (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,

    val current: Current,
    val minutely: List<Minutely>,
    val hourly: List<Current>,
    val daily: List<Daily>
)

data class Current (
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Long,
    val visibility: Long,
    val wind_speed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val pop: Double? = null,
    val rain: Rain? = null
)

data class Rain (
    val the1H: Double
)

data class Weather (
    val id: Long,
    val main: Main,
    val description: String,
    val icon: String
)


enum class Icon(val value: String) {
    The01D("01d"),
    The02D("02d"),
    The03D("03d"),
    The04D("04d"),
    The04N("04n"),
    The10D("10d"),
    The10N("10n");

    companion object {
        public fun fromValue(value: String): Icon = when (value) {
            "01d" -> The01D
            "02d" -> The02D
            "03d" -> The03D
            "04d" -> The04D
            "04n" -> The04N
            "10d" -> The10D
            "10n" -> The10N
            else  -> throw IllegalArgumentException()
        }
    }
}

enum class Main(val value: String) {
    Clear("Clear"),
    Clouds("Clouds"),
    Rain("Rain");

    companion object {
        public fun fromValue(value: String): Main = when (value) {
            "Clear"  -> Clear
            "Clouds" -> Clouds
            "Rain"   -> Rain
            else     -> throw IllegalArgumentException()
        }
    }
}

data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val wind_speed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Long,
    val pop: Double,
    val uvi: Double,
    val rain: Double? = null
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Minutely (
    val dt: Long,
    val precipitation: Long
)