package com.kotlinspring.controller

import com.kotlinspring.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Test
    fun retrieveGreeting(){

        val name = "sandeep"

        every { greetingServiceMock.retrieveGreeting(any()) } returns "$name, Hello from default profile"
        var result = webTestClient
            .get()
            .uri("/api/v1/greetings/{name}", name)
            .exchange()
            .expectBody(String::class.java).returnResult()

        Assertions.assertEquals("$name, Hello from default profile", result.responseBody)

    }

}