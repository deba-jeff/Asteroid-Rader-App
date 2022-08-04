package ukponahiunsijeffery.example.asteroidraderapp.network

import com.squareup.moshi.Json

data class PictureOfTheDay(
    val date: String,
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)

