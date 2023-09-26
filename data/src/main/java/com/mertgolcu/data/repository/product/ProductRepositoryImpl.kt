package com.mertgolcu.data.repository.product

import com.mertgolcu.data.api.client.ApiProvider
import com.mertgolcu.data.api.product.ProductApi
import com.mertgolcu.data.api.utils.Resource
import com.mertgolcu.data.api.utils.emitWithCatch
import com.mertgolcu.data.model.response.ProductListResponse
import com.mertgolcu.data.model.response.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */

class ProductRepositoryImpl(
    private val productApi: ProductApi
) : IProductRepository {
    override suspend fun getAllProducts(): Flow<List<ProductResponse>> =
        emitWithCatch(productApi.getAllProducts())

    override suspend fun getSingleProduct(id: Int): Flow<ProductResponse> =
        emitWithCatch(productApi.getSingleProduct(id))

    override suspend fun getAllCategories(): Flow<List<String>> =
        emitWithCatch(productApi.getAllCategories())

    override suspend fun getInCategory(category: String): Flow<List<ProductResponse>> =
        emitWithCatch(productApi.getInCategory(category))

    override suspend fun addNewProduct(product: ProductResponse): Flow<ProductResponse> =
        emitWithCatch(productApi.addNewProduct(product))

    override suspend fun updateProduct(id: Int, product: ProductResponse): Flow<ProductResponse> =
        emitWithCatch(productApi.updateProduct(id, product))

    override suspend fun deleteProduct(id: Int): Flow<ProductResponse> =
        emitWithCatch(productApi.deleteProduct(id))


}