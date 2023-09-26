package com.mertgolcu.data.repository.cart

import com.mertgolcu.data.model.response.CartResponse
import kotlinx.coroutines.flow.Flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface ICartRepository {
    suspend fun getUserCart(userId: Int): Flow<CartResponse>

    suspend fun addProductToCart(userId: Int, productId: Int, quantity: Int): Flow<CartResponse>

    suspend fun removeProductFromCart(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<CartResponse>

    suspend fun updateProductQuantity(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<CartResponse>

    suspend fun deleteCart(userId: Int): Flow<CartResponse>

}