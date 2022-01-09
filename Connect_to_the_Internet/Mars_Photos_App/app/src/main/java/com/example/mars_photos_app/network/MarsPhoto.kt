package com.example.mars_photos_app.network

import com.squareup.moshi.Json

data class MarsPhoto(
    val id:String,
    /**
     * Use @Json annotation to use variable names in your data class that differ from the key names in the JSON response.
     * Here, The data class [imgUrl] variable is mapped to the JSON attribute img_src using @Json(name = "img_src").
     */
    @Json(name = "img_src") val imgUrl:String
)
