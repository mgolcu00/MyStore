package com.mertgolcu.data.test

import com.mertgolcu.data.api.client.ApiProvider
import com.mertgolcu.data.api.utils.listenFlow
import com.mertgolcu.data.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
class ProductRepositoryTest {
    private val repository = ProductRepositoryImpl(ApiProvider.productApi)

    @Test
    fun getAllProducts() {
        runBlocking {
            repository.getAllProducts().catch {
                println(it)
                fail(it)
            }.onEach {
                println(it)
                assertNotNull(it)
            }.collect()
        }
    }

    @Test
    fun getSingleProduct() {
        val id = 1
        runBlocking {
            repository.getSingleProduct(id).catch {
                println(it)
                fail(it)
            }.onEach {
                println(it)
                assertNotNull(it)
            }.collect()
        }
    }

    @Test
    fun getAllCategories() {
        runBlocking {
            listenFlow(
                flow = repository.getAllCategories(),
                loading = {
                    println("Loading $it")
                },
                success = {
                    println(it)
                    assertNotNull(it)
                },
            )
        }
    }

    @Test
    fun getInCategory() {
        val category = "electronics"
        runBlocking {
            listenFlow(
                repository.getInCategory(category),
                loading = {
                    println("Loading $it")
                },
                success = {
                    println(it)
                    assertNotNull(it)
                },
                error = {
                    println(it)
                    fail(it)
                }
            ).collect()
        }
    }
}