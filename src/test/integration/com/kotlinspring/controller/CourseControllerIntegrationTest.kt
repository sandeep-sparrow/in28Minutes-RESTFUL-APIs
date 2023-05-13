package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.ICourseRepository
import com.kotlinspring.util.courseEntityList
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {

    companion object: KLogging()

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var iCourseRepository: ICourseRepository

    @BeforeEach
    fun setup(){
        iCourseRepository.deleteAll()
        val courses = courseEntityList()
        iCourseRepository.saveAll(courses)
    }

    // JUNIT 5
    @Test
    fun addCourse(){

        val courseDto = CourseDto(null, "Build Restful APIs using SpringBoot and Kotlin", "Sandeep Prajapati")

        val courseDtoResponse = webTestClient
            .post()
            .uri("/api/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        logger.info { "Result CourseDto: $courseDtoResponse" }

        Assertions.assertTrue{
            courseDtoResponse!!.id != null
        }

    }

    @Test
    fun retrieveAllCourses(){

        val listOfCourseDto = webTestClient
            .get()
            .uri("/api/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult().responseBody

        logger.info { "List of courses: ${listOfCourseDto!!}" }
        Assertions.assertEquals(3, listOfCourseDto!!.size)
    }

    @Test
    fun updateCourse(){
        val course = Course(null, "Cobol Programming Version 1", "Sandeep Prajapati")

        iCourseRepository.save(course)

        logger.info { "Course ID: ${course.id}" }

        val courseDto = CourseDto(null, "Cobol Programing Version 1.1", "Development")

        val courseDtoResponse = webTestClient
            .put()
            .uri("/api/v1/courses/{courseId}",course.id)
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Cobol Programming Version 1.1",courseDtoResponse!!.name)
    }
}