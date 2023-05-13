package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.service.CourseService
import org.springframework.http.HttpStatus
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
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: CourseDto){
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses() : List<CourseDto>{
        return courseService.retrieveAllCourses()
    }

    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(@RequestBody courseDto: CourseDto, @PathVariable("courseId") courseId: Int){
        courseService.updateCourse(courseDto, courseId)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("courseId") courseId: Int){
        courseService.deleteCourse(courseId)
    }
}