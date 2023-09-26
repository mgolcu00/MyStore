package com.mertgolcu.data.model.response

import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @since 26.09.2023
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

