package com.mertgolcu.data.model.response

import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
/**
 * {
 *                     id:1,
 *                     title:'...',
 *                     price:'...',
 *                     category:'...',
 *                     description:'...',
 *                     image:'...'
 *                 },
 */

@Serializable
data class ProductResponse(
    val id: Int? = null,
    val title: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val rating: Rating? = null
)

@Serializable
data class Rating(
    val rate:Double?=null,
    val count:Int?=null
)

@Serializable
data class ProductListResponse(
    val products: List<ProductResponse>
)

@Serializable
data class ProductGenericIdResponse(
    val id: Int
){
    fun combine(productResponse: ProductResponse): ProductResponse {
        return productResponse.copy(id = id)
    }
}

