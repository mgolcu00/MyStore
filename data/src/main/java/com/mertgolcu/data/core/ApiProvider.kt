package com.mertgolcu.data.core

import com.mertgolcu.data.api.cart.CartServiceImpl
import com.mertgolcu.data.api.product.ProductServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * @author Mert Gölcü
 * @since 15.09.2023
 */
object ApiProvider {

    private const val HOST = "fakestoreapi.com"
    private val PROTOCOL = URLProtocol.HTTPS
    private val client =
        HttpClient(CIO) {
            defaultRequest {
                host = HOST
                url {
                    protocol = PROTOCOL
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


    val productService = ProductServiceImpl(client)

    val cartService = CartServiceImpl(client)
}