package ukponahiunsijeffery.example.asteroidraderapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.nasa.gov/"

/**
 *  Creates a Retrofit Object for Asteroid
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


/**
 *  Defines the api the RetrofitService creates
 */
interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    fun getProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") api_key:String): Deferred<String>

}

object AsteroidApi{
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}


/**
 *  Creates a Moshi Object
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 *  Creates a Retrofit Object for PictureOfTheDay
 */
private val retrofitPictureOfTheDay = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


/**
 *  Defines the api the RetrofitService creates
 */
interface PictureApiService{
    @GET("planetary/apod")
    fun getPictureProperties(
        @Query("api_key") api_key: String): Deferred<PictureOfTheDay>
}

object PictureApi{
    val pictureRetrofitService: PictureApiService by lazy {
        retrofitPictureOfTheDay.create(PictureApiService::class.java)
    }
}

