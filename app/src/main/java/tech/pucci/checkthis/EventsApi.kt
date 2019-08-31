package tech.pucci.checkthis

import retrofit2.Call
import retrofit2.http.GET

interface EventsApi {

    @GET("events")
    fun get(): Call<List<Event>>
}