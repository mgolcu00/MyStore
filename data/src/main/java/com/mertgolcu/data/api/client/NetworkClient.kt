package com.mertgolcu.data.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.http.headers
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json

/**
 * @author Mert Gölcü
 * @since 15.09.2023
 */
class NetworkClient {

    fun getClient(): HttpClient {
        val _protocol = URLProtocol.HTTPS
        val _host = "fakestoreapi.com"

        val client = HttpClient(CIO) {
            defaultRequest {
                host = _host
                url {
                    protocol = _protocol
                }
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    // content type
                    append(HttpHeaders.ContentType, "application/json")
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
        return client
    }

    suspend fun getExample() {
        val client = getClient()
        // client.get<>()
        val response = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "ktor.io"
                path("docs/welcome.html")
                parameters.append("key", "value")
            }
            headers {
                append(HttpHeaders.Accept, "text/html")
                append(HttpHeaders.Authorization, "abc123")
                append(HttpHeaders.UserAgent, "ktor client")
            }
        }


    }

    suspend fun getExampleModel(): Flow<ExampleModel> {

        val client = getClient()
        val response = client.get("/example")
        return flowOf(
            response.body<ExampleModel>()
        )
    }

    fun urlProvider(base: String, params: List<String>, queries: Map<String, String>): String {
        /**
         * urlProvider("/example", listOf("param1", "param2"), mapOf("query1" to "value1", "query2" to "value2"))
         * -> "/example/param1/param2?query1=value1&query2=value2"
         *
         * urlProvider("/example", listOf("param1", "param2"), mapOf())
         * -> "/example/param1/param2"
         *
         * urlProvider("/example", listOf(), mapOf("query1" to "value1", "query2" to "value2"))
         * -> "/example?query1=value1&query2=value2"
         *
         * urlProvider("/example", listOf(), mapOf())
         * -> "/example"
         */

        val path = params.joinToString("/") { it }
        val query = queries.map { "${it.key}=${it.value}" }.joinToString("&") { it }
        val builder = StringBuilder(base)
        if (path.isNotEmpty()) {
            builder.append("/$path")
        }
        if (query.isNotEmpty()) {
            builder.append("?")
            builder.append(query)
        }
        return builder.toString()
    }

    suspend fun getExampleWithNone() = flow<ExampleModel> {
        getClient().get("/example").body<ExampleModel>()
    }

    suspend fun getExampleWithParam(param: String) = flow<ExampleModel> {
        getClient().get("/example/$param").body<ExampleModel>()
    }

    suspend fun getExampleWithQuery(query: String) = flow<ExampleModel> {
        getClient().get("/example?query=$query").body<ExampleModel>()
    }

    suspend fun postExampleWithRequestBody(requestBody: ExampleModel) = flow<ExampleModel> {
        getClient().post("/example") {
            setBody(requestBody)
        }.body<ExampleModel>()
    }

    suspend fun postExampleWithRequestBodyAndParam(requestBody: ExampleModel, param: String) =
        flow<ExampleModel> {
            getClient().post("/example/$param") {
                setBody(requestBody)
            }.body<ExampleModel>()
        }

    suspend fun postExampleWithRequestBodyAndQuery(requestBody: ExampleModel, query: String) =
        flow<ExampleModel> {
            getClient().post("/example?query=$query") {
                setBody(requestBody)
            }.body<ExampleModel>()
        }
}

data class ExampleModel(
    val id: String,
    val name: String
)