package tech.pucci.checkthis.model

import com.google.gson.Gson

data class Event(
            val people: List<Person>,
            val date: Long,
            val description: String,
            val image: String,
            val longitude: String,
            val latitude: String,
            val price: Double,
            val title: String,
            val id: String,
            val cupons: List<Coupon>){

    fun toJsonString(): String {
        return Gson().toJson(this)
    }

    companion object{
        const val EXTRA_EVENT = "EXTRA_EVENT"
    }
}

