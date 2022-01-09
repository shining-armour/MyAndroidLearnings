package com.example.mars_photos_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * Create a base url of the API to interact with the web service.
 */
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

/**
 * Create the Moshi object
 */
private val moshi:Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


/**
 * Create the retrofit object
 * Retrofit builder to build and create a Retrofit object
 * Converter tells Retrofit what to do with the data it gets back from the web service.
 * Retrofit has a ScalarsConverter that supports strings and other primitive types, so you call addConverterFactory() on the builder with an instance of ScalarsConverterFactory.
 * Replace ScalarsConverterFactory with the KotlinJsonAdapterFactory to let Retrofit know it can use Moshi to convert the JSON response into Kotlin objects.
 */
//private val retrofit:Retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()
private val retrofit:Retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()


/**
 * Interface to define how Retrofit talks to the web server using HTTP requests.
 */
interface MarsApiService{
    /**
     * When the getPhotos() method is invoked, Retrofit appends the endpoint [photos] to the [BASE_URL] and starts the request.
     */
    @GET("photos")
//    suspend fun getPhotos():String
    suspend fun getPhotos(): List<MarsPhoto>
}


/**
 * The call to create() function on a Retrofit object is expensive and the app needs only one instance of Retrofit API service. So, you expose the service to the rest of the app using object declaration.
 *  object declarations are used to declare singleton objects.
 *  Singleton pattern ensures that one, and only one, instance of an object is create that can be accessed from the rest of the app.
 *  Object declaration's initialization is thread-safe and done at first access.
 */
object MarsApi{
    /**
     * Each time your app calls MarsApi.retrofitService, the caller will access the same singleton Retrofit object that implements MarsApiService which is created on the first access.
     * "lazy instantiation" is when object creation is purposely delayed until you actually need that object to avoid unnecessary computation or use of other computing resources.
     */
    val retrofitService: MarsApiService by lazy{
        retrofit.create(MarsApiService::class.java)
    }
}




