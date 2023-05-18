package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.exception.CourseNotFoundException
import com.kotlinspring.repository.ICourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val iCourseRepository: ICourseRepository) {

    companion object : KLogging()

    fun addCourse(courseDto: CourseDTO): CourseDTO{
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }

        iCourseRepository.save(courseEntity) // save course entity with ID populated...
        logger.info { "Saved course is: $courseEntity" }

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(): List<CourseDTO>  =
        iCourseRepository.findAll()
            .map {
                CourseDTO(it.id, it.category, it.name)
            }

    fun updateCourse(courseDto: CourseDTO, courseId: Int) : CourseDTO {
        val existingCourse = iCourseRepository.findById(courseId)

        return if(existingCourse.isPresent){
                existingCourse.get().let {
                    it.name = courseDto.name
                    it.category = courseDto.category
                    iCourseRepository.save(it)
                    CourseDTO(it.id, it.name, it.category)
                }
        }else{
            throw CourseNotFoundException("Course not found, invalid CourseId $courseId")
        }

    }

    fun deleteCourse(courseId: Int) {

        val course = iCourseRepository.findById(courseId)
        if(course.isPresent){
            iCourseRepository.deleteById(courseId)
        }else{
            throw CourseNotFoundException("Course not found, invalid courseId")
        }


    }

}
