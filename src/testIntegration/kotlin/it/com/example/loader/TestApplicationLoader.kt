package it.com.example.loader

import it.com.example.app.TestApplication
import java.io.Closeable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.core.env.StandardEnvironment

@SpringBootApplication
class TestApplicationLoader {

    @Autowired
    lateinit var testEnvironment: Environment

    @Value("\${under-test.context.load:true}")
    private var loadContext = false

    @Bean
    fun subject(): Closeable {
        return if (loadContext) {
            val environment = StandardEnvironment()
            environment.setActiveProfiles("test")
            val sa = SpringApplication(TestApplication::class.java)
            sa.setEnvironment(environment)
            sa.run()
        } else {
            Closeable {}
        }
    }

    @Bean
    fun testRestTemplate(): TestRestTemplate {
        return TestRestTemplate()
    }
}
