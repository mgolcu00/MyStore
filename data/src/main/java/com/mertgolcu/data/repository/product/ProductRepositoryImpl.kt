package com.mertgolcu.data.repository.product

import com.mertgolcu.data.api.product.ProductServiceImpl
import com.mertgolcu.data.core.utils.emitWithCatch
import com.mertgolcu.data.model.request.AddOrUpdateProductRequest
import com.mertgolcu.data.model.response.ProductResponse
import kotlinx.coroutines.flow.Flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */

class ProductRepositoryImpl(
    private val productServiceImpl: ProductServiceImpl
) : IProductRepository {
    override suspend fun getAllProducts(limit: Int?, sort: String): Flow<List<ProductResponse>> {
        return emitWithCatch(productServiceImpl.getAllProducts(limit, sort))
    }

    override suspend fun getSingleProduct(id: Int): Flow<ProductResponse> =
        emitWithCatch(productServiceImpl.getSingleProduct(id))

    override suspend fun getAllCategories(): Flow<List<String>> =
        emitWithCatch(productServiceImpl.getAllCategories())

    override suspend fun getInCategory(category: String): Flow<List<ProductResponse>> =
        emitWithCatch(productServiceImpl.getInCategory(category))

    override suspend fun addNewProduct(product: AddOrUpdateProductRequest): Flow<ProductResponse> =
        emitWithCatch(productServiceImpl.addNewProduct(product))

    override suspend fun updateProduct(id: Int, product: AddOrUpdateProductRequest): Flow<ProductResponse> =
        emitWithCatch(productServiceImpl.updateProduct(id, product))

    override suspend fun deleteProduct(id: Int): Flow<ProductResponse> =
        emitWithCatch(productServiceImpl.deleteProduct(id))


}