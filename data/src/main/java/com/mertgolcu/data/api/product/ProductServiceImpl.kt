package com.mertgolcu.data.api.product

import com.mertgolcu.data.core.utils.Resource
import com.mertgolcu.data.core.utils.delete
import com.mertgolcu.data.core.utils.get
import com.mertgolcu.data.core.utils.post
import com.mertgolcu.data.core.utils.put
import com.mertgolcu.data.model.request.AddOrUpdateProductRequest
import com.mertgolcu.data.model.response.ProductResponse
import io.ktor.client.HttpClient

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */


class ProductServiceImpl(private val client: HttpClient) : IProductService {

    override suspend fun getAllProducts(
        limit: Int?,
        sort: String?
    ): Resource<List<ProductResponse>> {
        val query = hashMapOf<String, String>()
        limit?.let {
            query["limit"] = it.toString()
        }
        sort?.let {
            query["sort"] = it
        }
        return client.get(url = "/products", query = query)
    }

    override suspend fun getSingleProduct(id: Int): Resource<ProductResponse> =
        client.get(url = "/products", params = listOf(id.toString()))

    override suspend fun getAllCategories(): Resource<List<String>> =
        client.get(url = "/products/categories")

    override suspend fun getInCategory(category: String): Resource<List<ProductResponse>> =
        client.get(url = "/products", query = mapOf("category" to category))

    override suspend fun addNewProduct(product: AddOrUpdateProductRequest): Resource<ProductResponse> =
        client.post(url = "/products", body = product)

    override suspend fun updateProduct(
        id: Int,
        product: AddOrUpdateProductRequest
    ): Resource<ProductResponse> =
        client.put(url = "/products", params = listOf(id.toString()), body = product)

    override suspend fun deleteProduct(id: Int): Resource<ProductResponse> =
        client.delete(url = "/products", params = listOf(id.toString()))

}