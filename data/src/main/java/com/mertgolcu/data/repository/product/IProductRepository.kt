package com.mertgolcu.data.repository.product

import com.mertgolcu.data.model.request.AddOrUpdateProductRequest
import com.mertgolcu.data.model.response.ProductResponse
import com.mertgolcu.data.model.utils.ProductSort
import kotlinx.coroutines.flow.Flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface IProductRepository {
    suspend fun getAllProducts(
        limit: Int? = null,
        sort: String = ProductSort.ASC.value
    ): Flow<List<ProductResponse>>

    suspend fun getSingleProduct(id: Int): Flow<ProductResponse>

    suspend fun getAllCategories(): Flow<List<String>>

    suspend fun getInCategory(category: String): Flow<List<ProductResponse>>

    suspend fun addNewProduct(product: AddOrUpdateProductRequest): Flow<ProductResponse>

    suspend fun updateProduct(id: Int, product: AddOrUpdateProductRequest): Flow<ProductResponse>

    suspend fun deleteProduct(id: Int): Flow<ProductResponse>

}