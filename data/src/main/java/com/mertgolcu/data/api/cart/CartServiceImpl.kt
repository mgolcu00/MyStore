package com.mertgolcu.data.api.cart

import com.mertgolcu.data.core.utils.Resource
import com.mertgolcu.data.core.utils.delete
import com.mertgolcu.data.core.utils.get
import com.mertgolcu.data.core.utils.post
import com.mertgolcu.data.core.utils.put
import com.mertgolcu.data.model.response.CartResponse
import io.ktor.client.HttpClient

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
class CartServiceImpl(
    private val client: HttpClient
) : ICartService {
    override suspend fun getAllCarts(): Resource<List<CartResponse>> = client.get(url = "/carts")

    override suspend fun getCartsInDateRange(
        startDate: String,
        endDate: String
    ): Resource<List<CartResponse>> = client.get(
        url = "/carts",
        query = mapOf(
            "startdate" to startDate,
            "enddate" to endDate
        )
    )

    override suspend fun getUserCarts(userId: Int): Resource<List<CartResponse>> {
        return client.get(url = "/carts/user", params = listOf(userId.toString()))
    }

    override suspend fun getSingleCart(id: Int): Resource<CartResponse> =
        client.get(url = "/carts", params = listOf(id.toString()))

    override suspend fun addNewCart(cart: CartResponse): Resource<CartResponse> =
        client.post(url = "/carts", body = cart)

    override suspend fun updateCart(id: Int, cart: CartResponse): Resource<CartResponse> =
        client.put(url = "/carts", params = listOf(id.toString()), body = cart)

    override suspend fun deleteCart(id: Int): Resource<CartResponse> =
        client.delete(url = "/carts", params = listOf(id.toString()))
}