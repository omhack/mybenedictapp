package com.example.mybenedictapp.core.common

fun <T : Any> T?.isNotNull(): Boolean {
    return this != null
}

fun <T : Any> T?.isNull(): Boolean {
    return this == null
}