package it.com.example.specs

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import it.com.example.loader.TestApplicationLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(
    classes = [TestApplicationLoader::class],
    properties = ["spring.boot.enableautoconfiguration=false"],
)
class MyControllerTest : StringSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    init {
        "test" {
            val res = testRestTemplate.getForObject("http://localhost:8081/hello", String::class.java)
            res shouldBe "mocked in test-application"
        }
    }
}
