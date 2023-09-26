package com.mertgolcu.data.core.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.NullBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */

suspend inline fun <reified T> responseToResource(response: HttpResponse): Resource<T> {
    return when (response.status) {
        HttpStatusCode.OK -> {
            if (response.bodyAsText() == "null") {
                return Resource.Error("Response body is null or empty")
            }
            val data = response.body<T>()
            Resource.Success(data)
        }

        else -> {
            Resource.Error(response.status.description)
        }
    }
}

fun createUrl(
    base: String,
    params: List<String> = listOf(),
    query: Map<String, String> = hashMapOf()
): String {
    val url = StringBuilder(base)
    params.forEach {
        url.append("/$it")
    }
    if (query.isNotEmpty()) {
        url.append("?")
        query.forEach { (key, value) ->
            url.append("$key=$value&")
        }
        url.deleteCharAt(url.length - 1)
    }
    return url.toString()
}

suspend inline fun <reified T> HttpClient.get(
    url: String,
    params: List<String> = listOf(),
    query: Map<String, String> = hashMapOf()
): Resource<T> {
    val uri = createUrl(url, params, query)
    return responseToResource(this.get(uri))
}

suspend inline fun <reified T, reified R> HttpClient.post(
    url: String,
    params: List<String> = listOf(),
    query: Map<String, String> = hashMapOf(),
    body: R
): Resource<T> {
    val uri = createUrl(url, params, query)
    val response = this.post(uri) {
        setBody(body)
    }
    return responseToResource(response)
}

// put
suspend inline fun <reified T, reified R> HttpClient.put(
    url: String,
    params: List<String> = listOf(),
    query: Map<String, String> = hashMapOf(),
    body: R
): Resource<T> {
    val uri = createUrl(url, params, query)
    val response = this.put(uri) {
        setBody(body)
    }
    return responseToResource(response)
}

suspend inline fun <reified T> HttpClient.delete(
    url: String,
    params: List<String> = listOf(),
    query: Map<String, String> = hashMapOf()
): Resource<T> {
    val uri = createUrl(url, params, query)
    val response = this.delete(uri)

    return responseToResource(response)
}

fun <T> listenFlow(
    flow: Flow<T>,
    loading: (Boolean) -> Unit = {},
    error: (String) -> Unit = {},
    success: (T) -> Unit = {}
): Flow<T> {
    return flow
        .onStart {
            loading(true)
        }
        .onCompletion {
            loading(false)
        }
        .catch {
            error(it.message ?: "Unknown error")
        }
        .onEach {
            success(it)
        }
}

fun <T> emitWithCatch(resource: Resource<T>): Flow<T> {
    return flow {
        emit(
            when (resource) {
                is Resource.Success -> {
                    resource.data
                }

                is Resource.Error -> {
                    throw resource.e ?: Exception(resource.message)
                }
            }
        )
    }
}