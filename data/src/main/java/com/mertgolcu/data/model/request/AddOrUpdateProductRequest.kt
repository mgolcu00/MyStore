package com.mertgolcu.data.model.request

import com.mertgolcu.data.model.response.Rating
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
@Serializable
data class AddOrUpdateProductRequest(
    val id: Int? = null,
    val title: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val rating: Rating? = null
)