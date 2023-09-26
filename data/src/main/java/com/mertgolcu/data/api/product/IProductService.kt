package com.mertgolcu.data.api.product

import com.mertgolcu.data.core.utils.Resource
import com.mertgolcu.data.model.request.AddOrUpdateProductRequest
import com.mertgolcu.data.model.response.ProductResponse


/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface IProductService {

    suspend fun getAllProducts(
        limit: Int? = null,
        sort: String? = null
    ): Resource<List<ProductResponse>>

    suspend fun getSingleProduct(id: Int): Resource<ProductResponse>

    suspend fun getAllCategories(): Resource<List<String>>

    suspend fun getInCategory(category: String): Resource<List<ProductResponse>>

    suspend fun addNewProduct(product: AddOrUpdateProductRequest): Resource<ProductResponse>

    suspend fun updateProduct(id: Int, product: AddOrUpdateProductRequest): Resource<ProductResponse>

    suspend fun deleteProduct(id: Int): Resource<ProductResponse>
}