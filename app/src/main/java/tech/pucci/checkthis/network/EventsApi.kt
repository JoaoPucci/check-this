package tech.pucci.checkthis.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tech.pucci.checkthis.model.Event
import tech.pucci.checkthis.model.Person

interface EventsApi {

    @GET("events")
    fun get(): Call<List<Event>>

    @POST("checkin")
    fun checkIn(@Body person: Person): Call<ResponseBody>
}