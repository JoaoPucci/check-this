package tech.pucci.checkthis.network

import org.junit.Assert.assertEquals
import org.junit.Test
import tech.pucci.checkthis.model.Person

class RetrofitInitializerTest {

    private val person1 = Person("1","1", "name 1", "person1@email.com", "picture 1")

    private val getEndpoint = "http://5b840ba5db24a100142dcd8c.mockapi.io/api/events"
    private val checkInEndpoint = "http://5b840ba5db24a100142dcd8c.mockapi.io/api/checkin"

    @Test
    fun should_ReturnEventsEndpoints_FromCallsCreatedWithRetrofitInitializer() {
        assertEquals(getEndpoint,RetrofitInitializer().eventsService().get().request().url().toString())
        assertEquals(checkInEndpoint,RetrofitInitializer().eventsService().checkIn(person1).request().url().toString())
    }
}