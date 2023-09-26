package com.mertgolcu.data.api.product

import com.mertgolcu.data.api.utils.Resource
import com.mertgolcu.data.api.utils.delete
import com.mertgolcu.data.api.utils.get
import com.mertgolcu.data.api.utils.post
import com.mertgolcu.data.api.utils.put
import com.mertgolcu.data.model.response.ProductResponse
import io.ktor.client.HttpClient

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */


class ProductApi(private val client: HttpClient) : IProductApi {
    override suspend fun getAllProducts(): Resource<List<ProductResponse>> =
        client.get(url = "/products")

    override suspend fun getSingleProduct(id: Int): Resource<ProductResponse> =
        client.get(url = "/products", params = listOf(id.toString()))

    override suspend fun getAllCategories(): Resource<List<String>> =
        client.get(url = "/products/categories")

    override suspend fun getInCategory(category: String): Resource<List<ProductResponse>> =
        client.get(url = "/products", query = mapOf("category" to category))

    override suspend fun addNewProduct(product: ProductResponse): Resource<ProductResponse> =
        client.post(url = "/products", body = product)

    override suspend fun updateProduct(
        id: Int,
        product: ProductResponse
    ): Resource<ProductResponse> =
        client.put(url = "/products", params = listOf(id.toString()), body = product)

    override suspend fun deleteProduct(id: Int): Resource<ProductResponse> =
        client.delete(url = "/products", params = listOf(id.toString()))

}