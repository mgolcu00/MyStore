package com.mertgolcu.data.api.cart

import com.mertgolcu.data.core.utils.Resource
import com.mertgolcu.data.model.response.CartResponse

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface ICartService {
    suspend fun getAllCarts(): Resource<List<CartResponse>>

    suspend fun getCartsInDateRange(
        startDate: String,
        endDate: String
    ): Resource<List<CartResponse>>

    suspend fun getUserCarts(userId: Int): Resource<List<CartResponse>>

    suspend fun getSingleCart(id: Int): Resource<CartResponse>

    suspend fun addNewCart(cart: CartResponse): Resource<CartResponse>

    suspend fun updateCart(id: Int, cart: CartResponse): Resource<CartResponse>

    suspend fun deleteCart(id: Int): Resource<CartResponse>


}