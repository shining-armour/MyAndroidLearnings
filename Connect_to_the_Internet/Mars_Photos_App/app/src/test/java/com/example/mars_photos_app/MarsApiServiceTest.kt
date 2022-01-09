package com.example.mars_photos_app

import com.example.mars_photos_app.network.MarsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Child class
class MarsApiServiceTest : BaseTest() {

    // Late initializing MarsApiService interface
    private lateinit var service: MarsApiService

    @Before
    fun setUp(){
        val url = mockWebServer.url("/")

        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun test_api_service(){
        enqueue("mars_photos.json")

        /**
         * getPhotos() is a suspend function, and it must be called from a coroutine scope.
         */
        runBlocking {
            val apiResponse = service.getPhotos()

            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.isNotEmpty())
            assertEquals("The IDs did not match", "424906", apiResponse[1].id)
        }
    }


}