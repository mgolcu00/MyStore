package com.mertgolcu.data.test

import com.mertgolcu.data.core.ApiProvider
import com.mertgolcu.data.core.utils.listenFlow
import com.mertgolcu.data.model.request.AddOrUpdateProductRequest
import com.mertgolcu.data.model.utils.ProductSort
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
    private val repository = ProductRepositoryImpl(ApiProvider.productService)

    @Test
    fun getAllProducts() {
        runBlocking {
            repository.getAllProducts(sort = ProductSort.DESC.value, limit = 5).catch {
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

    @Test
    fun addNewProduct() {
        val product = AddOrUpdateProductRequest(
            title = "Example Product",
            price = 100.0,
            category = "electronics",
            description = "Example Description",
            image = "https://example.com/image.jpg"
        )
        runBlocking {
            listenFlow(
                repository.addNewProduct(product),
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

    @Test
    fun updateProduct() {
        val id = 1
        val product = AddOrUpdateProductRequest(
            title = "Example Product",
            price = 100.0,
            category = "electronics",
            description = "Example Description",
            image = "https://example.com/image.jpg"
        )
        runBlocking {
            listenFlow(
                repository.updateProduct(id, product),
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

    @Test
    fun deleteProduct() {
        val id = 1
        runBlocking {
            listenFlow(
                repository.deleteProduct(id),
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