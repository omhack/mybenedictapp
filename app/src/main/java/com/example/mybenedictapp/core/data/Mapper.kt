package com.example.mybenedictapp.core.data

interface Mapper<From, To> {
    fun map(dto: From): To
}