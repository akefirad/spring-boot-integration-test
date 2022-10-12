package it.com.example.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import com.example.api.MyService

@SpringBootApplication(scanBasePackages = ["com.example.api"])
class TestApplication {

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @Bean
    @Primary
    fun myService(): MyService {
        return object : MyService {
            override fun hello(): String {
                return "mocked in $applicationName"
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TestApplication>(*args)
        }
    }
}
