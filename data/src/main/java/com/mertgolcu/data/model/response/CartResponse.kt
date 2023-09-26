package com.mertgolcu.data.model.response

import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
@Serializable
data class CartResponse(
    val id: Int? = null,
    val userId: Int? = null,
    val date: String? = null,
    val products: List<CartProduct>? = null
)

@Serializable
data class CartProduct(
    val productId: Int? = null,
    val quantity: Int? = null
)