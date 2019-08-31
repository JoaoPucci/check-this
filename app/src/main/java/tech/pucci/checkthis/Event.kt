package tech.pucci.checkthis

class Event(val people: List<Person>,
            val date: Long,
            val description: String,
            val image: String,
            val longitude: String,
            val latitude: String,
            val price: Double,
            val title: String,
            val id: String,
            val cupons: List<Coupon>)

