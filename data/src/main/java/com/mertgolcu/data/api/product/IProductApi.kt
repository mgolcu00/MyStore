package com.mertgolcu.data.api.product

import com.mertgolcu.data.api.utils.Resource
import com.mertgolcu.data.model.response.ProductListResponse
import com.mertgolcu.data.model.response.ProductResponse


/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
interface IProductApi {

    suspend fun getAllProducts(): Resource<List<ProductResponse>>

    suspend fun getSingleProduct(id: Int): Resource<ProductResponse>

    suspend fun getAllCategories(): Resource<List<String>>

    suspend fun getInCategory(category: String): Resource<List<ProductResponse>>

    suspend fun addNewProduct(product: ProductResponse): Resource<ProductResponse>

    suspend fun updateProduct(id: Int, product: ProductResponse): Resource<ProductResponse>

    suspend fun deleteProduct(id: Int): Resource<ProductResponse>
}