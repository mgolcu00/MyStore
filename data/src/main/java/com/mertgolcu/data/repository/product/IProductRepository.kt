package com.mertgolcu.data.repository.product

import com.mertgolcu.data.model.response.ProductListResponse
import com.mertgolcu.data.model.response.ProductResponse
import kotlinx.coroutines.flow.Flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface IProductRepository {
    suspend fun getAllProducts(): Flow<List<ProductResponse>>

    suspend fun getSingleProduct(id: Int): Flow<ProductResponse>

    suspend fun getAllCategories(): Flow<List<String>>

    suspend fun getInCategory(category: String): Flow<List<ProductResponse>>

    suspend fun addNewProduct(product: ProductResponse): Flow<ProductResponse>

    suspend fun updateProduct(id: Int, product: ProductResponse): Flow<ProductResponse>

    suspend fun deleteProduct(id: Int): Flow<ProductResponse>

}