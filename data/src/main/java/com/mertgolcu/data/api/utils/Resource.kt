package com.mertgolcu.data.api.utils

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
sealed class Resource<T> {

    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String? = null, val e: Throwable? = null) : Resource<T>()
}