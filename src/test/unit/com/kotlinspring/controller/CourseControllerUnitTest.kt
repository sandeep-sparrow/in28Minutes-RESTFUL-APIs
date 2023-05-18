package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService

    @Test
    fun addCourse(){

        val courseDTO = courseDTO()

        val courseDtoResponse = webTestClient
            .post()
            .uri("/api/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        CourseControllerIntegrationTest.logger.info { "Result CourseDto: $courseDtoResponse" }

        Assertions.assertTrue{
            courseDtoResponse!!.id != null
        }

    }

    @Test
    fun retrieveAllCourses(){

        every { courseServiceMockk.retrieveAllCourses() }.returnsMany(
            listOf(courseDTO(id=1), courseDTO(id=2, name="Build Reactive Microservices using Spring WebFlux/SpringBoot"))
        )

        val listOfCourseDto = webTestClient
            .get()
            .uri("/api/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult().responseBody

        CourseControllerIntegrationTest.logger.info { "List of courses: ${listOfCourseDto!!}" }
        Assertions.assertEquals(3, listOfCourseDto!!.size)
    }
}