package com.sunplacestudio.movieapplication.utils

fun String?.orNull(): String = this ?: ""

fun Int?.orNull(): Int = this ?: -1

fun Float?.orNull() : Float = this ?: -1f

fun Double?.orNull() : Double = this ?: -1.0

fun <T> List<T>?.orNull(): List<T> = this ?: emptyList()