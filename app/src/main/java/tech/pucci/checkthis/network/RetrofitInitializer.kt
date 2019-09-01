package tech.pucci.checkthis.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://5b840ba5db24a100142dcd8c.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun eventsService(): EventsApi = retrofit.create(EventsApi::class.java)
}