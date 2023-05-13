package com.kotlinspring.dto

data class CourseDto(
    val id: Int?, // to be generated by the DB
    var name: String,
    var category: String
)