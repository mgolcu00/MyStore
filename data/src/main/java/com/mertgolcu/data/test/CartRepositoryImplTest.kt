package com.mertgolcu.data.test

import com.mertgolcu.data.core.ApiProvider
import com.mertgolcu.data.core.utils.listenFlow
import com.mertgolcu.data.repository.cart.CartRepositoryImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
class CartRepositoryImplTest {

    val repository = CartRepositoryImpl(ApiProvider.cartService)

    var userId = 1

    @Test
    fun getUserCart() {
        runBlocking {
            repository.getUserCart(userId)
                .onStart {
                    println("Loading getUserCart")
                }
                .catch {
                    println("Error getUserCart $it")
                    fail(it)
                }.onEach {
                    println("Success getUserCart $it")
                    assertNotNull(it)
                }
                .collect()
        }
    }

    @Test
    fun addProductToCart() {
        val productId = 1
        val quantity = 1
        runBlocking {
            repository.addProductToCart(userId, productId, quantity)
                .onStart {
                    println("Loading addProductToCart")
                }
                .catch {
                    println("Error addProductToCart $it")
                    fail(it)
                }.onEach {
                    println("Success addProductToCart $it")
                    assertNotNull(it)
                }
                .collect()
        }
    }

    @Test
    fun removeProductFromCart() {
        val productId = 1
        val quantity = 1
        runBlocking {
            repository.removeProductFromCart(userId, productId, quantity)
                .onStart {
                    println("Loading removeProductFromCart")
                }
                .catch {
                    println("Error removeProductFromCart $it")
                    fail(it)
                }.onEach {
                    println("Success removeProductFromCart $it")
                    assertNotNull(it)
                }
                .collect()
        }
    }

    @Test
    fun updateProductQuantity() {
        val productId = 1
        val quantity = 99
        runBlocking {
            repository.updateProductQuantity(userId, productId, quantity)
                .onStart {
                    println("Loading updateProductQuantity")
                }
                .catch {
                    println("Error updateProductQuantity $it")
                    fail(it)
                }.onEach {
                    println("Success updateProductQuantity $it")
                    assertNotNull(it)
                }
                .collect()
        }
    }

    @Test
    fun deleteCart() {
        runBlocking {
            repository.deleteCart(userId)
                .onStart {
                    println("Loading deleteCart")
                }
                .catch {
                    println("Error deleteCart $it")
                    fail(it)
                }.onEach {
                    println("Success deleteCart $it")
                    assertNotNull(it)
                }
                .collect()
        }
    }
}