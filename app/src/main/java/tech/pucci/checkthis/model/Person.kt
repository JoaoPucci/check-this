package tech.pucci.checkthis.model

data class Person(var id: String,
                  var eventId: String,
                  val name: String,
                  val email: String,
                  val picture: String)