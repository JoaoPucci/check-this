package tech.pucci.checkthis.model

import org.junit.Test

import org.junit.Assert.*

class EventTest {

    private val person1 = Person("1","1", "name 1", "person1@email.com", "picture 1")
    private val person2 = Person("2","2", "name 2", "person2@email.com", "picture 2")
    private val coupon1 = Coupon("1", "1", 62)
    private val image = "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png"

    @Test
    fun should_returnEventAsJsonString_IfIsACompleteEventObject() {
        val event = Event(listOf(person1), 1534784400000, "O Patas Dadas estará na Redenção", image,
            "-51.2146267", "-30.0392981",29.99, "Feira de adoção de animais na Redenção",
            "1", listOf(coupon1))

        val expectedJsonStringResult =
            "{" +
                "\"people\":[" +
                    "{" +
                    "\"id\":\"1\"," +
                    "\"eventId\":\"1\"," +
                    "\"name\":\"name 1\"," +
                    "\"email\":\"person1@email.com\"," +
                    "\"picture\":\"picture 1\"" +
                    "}" +
                "]," +
                "\"date\":1534784400000," +
                "\"description\":\"O Patas Dadas estará na Redenção\"," +
                "\"image\":\"http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png\"," +
                "\"longitude\":\"-51.2146267\"," +
                "\"latitude\":\"-30.0392981\"," +
                "\"price\":29.99," +
                "\"title\":\"Feira de adoção de animais na Redenção\"," +
                "\"id\":\"1\"," +
                "\"cupons\":[" +
                    "{" +
                    "\"id\":\"1\"," +
                    "\"eventId\":\"1\"," +
                    "\"discount\":62" +
                    "}" +
                "]" +
            "}"

        assertEquals(expectedJsonStringResult, event.toJsonString())
    }

    @Test
    fun should_returnEventAsJsonString_IfAnInternalListIsEmpty() {
        val event = Event(listOf(), 1534784400000, "O Patas Dadas estará na Redenção", image,
            "-51.2146267", "-30.0392981",29.99, "Feira de adoção de animais na Redenção",
            "1", listOf(coupon1))

        val expectedJsonStringResult =
            "{" +
                "\"people\":[" +
                "]," +
                "\"date\":1534784400000," +
                "\"description\":\"O Patas Dadas estará na Redenção\"," +
                "\"image\":\"http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png\"," +
                "\"longitude\":\"-51.2146267\"," +
                "\"latitude\":\"-30.0392981\"," +
                "\"price\":29.99," +
                "\"title\":\"Feira de adoção de animais na Redenção\"," +
                "\"id\":\"1\"," +
                "\"cupons\":[" +
                    "{" +
                        "\"id\":\"1\"," +
                        "\"eventId\":\"1\"," +
                        "\"discount\":62" +
                    "}" +
                "]" +
            "}"

        assertEquals(expectedJsonStringResult, event.toJsonString())
    }

    @Test
    fun should_returnEventAsJsonString_IfIsAnInternalListHasMoreThanOneItem() {
        val event = Event(listOf(person1, person2), 1534784400000, "O Patas Dadas estará na Redenção",
            image,"-51.2146267", "-30.0392981",29.99, "Feira de adoção de animais na Redenção",
            "1", listOf(coupon1)
        )

        val expectedJsonStringResult =
            "{" +
                "\"people\":[" +
                    "{" +
                        "\"id\":\"1\"," +
                        "\"eventId\":\"1\"," +
                        "\"name\":\"name 1\"," +
                        "\"email\":\"person1@email.com\"," +
                        "\"picture\":\"picture 1\"" +
                    "}," +
                    "{" +
                        "\"id\":\"2\"," +
                        "\"eventId\":\"2\"," +
                        "\"name\":\"name 2\"," +
                        "\"email\":\"person2@email.com\"," +
                        "\"picture\":\"picture 2\"" +
                    "}" +
                "]," +
                "\"date\":1534784400000," +
                "\"description\":\"O Patas Dadas estará na Redenção\"," +
                "\"image\":\"http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png\"," +
                "\"longitude\":\"-51.2146267\"," +
                "\"latitude\":\"-30.0392981\"," +
                "\"price\":29.99," +
                "\"title\":\"Feira de adoção de animais na Redenção\"," +
                "\"id\":\"1\"," +
                "\"cupons\":[" +
                    "{" +
                        "\"id\":\"1\"," +
                        "\"eventId\":\"1\"," +
                        "\"discount\":62" +
                    "}" +
                "]" +
            "}"

        assertEquals(expectedJsonStringResult, event.toJsonString())
    }

}