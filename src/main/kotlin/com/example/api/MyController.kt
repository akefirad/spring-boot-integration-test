package com.example.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(
    private val myService: MyService
) {
    @GetMapping("/hello")
    fun hello() = myService.hello()
}

interface MyService {
    fun hello(): String
}

@Service
class MyServiceImpl(
    @Value("\${spring.application.name}") private val name: String
) : MyService {
    override fun hello() = "Hello, world from $name"
}