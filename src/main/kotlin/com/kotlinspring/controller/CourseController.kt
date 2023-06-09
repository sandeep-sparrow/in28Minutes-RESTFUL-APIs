package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDto: CourseDTO): CourseDTO{
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses() : List<CourseDTO>{
        return courseService.retrieveAllCourses()
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(@RequestBody courseDto: CourseDTO, @PathVariable("courseId") courseId: Int){
        courseService.updateCourse(courseDto, courseId)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("courseId") courseId: Int){
        courseService.deleteCourse(courseId)
    }
}