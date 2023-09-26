package com.mertgolcu.data.test

import com.mertgolcu.data.api.client.ApiProvider
import com.mertgolcu.data.api.utils.Resource
import com.mertgolcu.data.model.response.ProductResponse
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */

class ProductApiTest {

    private val api = ApiProvider.productApi

    @Test
    fun getAllProducts() {
        runBlocking {
            when (val result = api.getAllProducts()) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun getSingleProduct() {
        val id = 1
        runBlocking {
            when (val result = api.getSingleProduct(id)) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun getAllCategories() {
        runBlocking {
            when (val result = api.getAllCategories()) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun getInCategory() {
        val category = "electronics"
        runBlocking {
            when (val result = api.getInCategory(category)) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun addNewProduct() {
        val product = ProductResponse(
            title = "Example Product",
            price = 100.0,
            category = "electronics",
            description = "Example Description",
            image = "https://example.com/image.jpg"
        )
        runBlocking {
            when (val result = api.addNewProduct(product)) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun updateProduct() {
        val id = 1
        val product = ProductResponse(
            title = "Example Product",
            price = 100.0,
            category = "electronics",
            description = "Example Description",
            image = "https://example.com/image.jpg"
        )
        runBlocking {
            when (val result = api.updateProduct(id, product)) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }

    @Test
    fun deleteProduct() {
        val id = 1
        runBlocking {
            when (val result = api.deleteProduct(id)) {
                is Resource.Success -> {
                    println(result.data)
                    assertNotNull(result.data)
                }

                is Resource.Error -> {
                    println(result.message)
                    fail(result.message)
                }
            }
        }
    }
}