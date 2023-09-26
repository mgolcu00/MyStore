package com.mertgolcu.data.repository.cart

import com.mertgolcu.data.api.cart.ICartService
import com.mertgolcu.data.core.utils.Resource
import com.mertgolcu.data.core.utils.emitWithCatch
import com.mertgolcu.data.model.response.CartProduct
import com.mertgolcu.data.model.response.CartResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Mert Gölcü
 * @since 26.09.2023
 */
class CartRepositoryImpl(
    private val cartService: ICartService
) : ICartRepository {
    override suspend fun getUserCart(userId: Int): Flow<CartResponse> {
        return flow {
            when (val userCarts = cartService.getUserCarts(userId)) {
                is Resource.Success -> {
                    val cart = userCarts.data.firstOrNull()
                    if (cart != null) {
                        emit(cart)
                    } else {
                        throw Exception("Cart is empty")
                    }
                }

                is Resource.Error -> {
                    throw Exception(userCarts.message)
                }
            }
        }
    }

    override suspend fun addProductToCart(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<CartResponse> {
        return flow {
            when (val userCart = cartService.getUserCarts(userId)) {
                is Resource.Success -> {
                    val cart = userCart.data.firstOrNull()
                    if (cart != null) {
                        val existProduct = cart.products?.find {
                            it.productId == productId
                        }
                        if (existProduct != null) {
                            val newCart = cart.copy(
                                products = cart.products.map {
                                    if (it.productId == productId) {
                                        it.copy(quantity = it.quantity?.plus(quantity))
                                    } else {
                                        it
                                    }
                                }
                            )
                            emit(newCart)
                        } else {
                            val newCart = cart.copy(
                                products = cart.products?.plus(
                                    CartProduct(
                                        productId = productId,
                                        quantity = quantity
                                    )
                                )
                            )
                            emit(newCart)
                        }
                    } else {
                        throw Exception("Cart is empty")
                    }
                }

                is Resource.Error -> {
                    throw Exception(userCart.message)
                }
            }
        }
    }

    override suspend fun removeProductFromCart(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<CartResponse> {
        return flow {
            when (val userCart = cartService.getUserCarts(userId)) {
                is Resource.Success -> {
                    val cart = userCart.data.firstOrNull()
                    if (cart != null) {
                        val existProduct = cart.products?.find {
                            it.productId == productId
                        }
                        if (existProduct != null) {
                            val newCart = cart.copy(
                                products = cart.products.map {
                                    if (it.productId == productId) {
                                        it.copy(quantity = it.quantity?.minus(quantity))
                                    } else {
                                        it
                                    }
                                }
                            )
                            emit(newCart)
                        } else {
                            throw Exception("Product not found in cart")
                        }
                    } else {
                        throw Exception("Cart is empty")
                    }
                }

                is Resource.Error -> {
                    throw Exception(userCart.message)
                }
            }
        }
    }

    override suspend fun updateProductQuantity(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<CartResponse> {
        return flow {
            when (val userCart = cartService.getUserCarts(userId)) {
                is Resource.Success -> {
                    val cart = userCart.data.firstOrNull()
                    if (cart != null) {
                        val existProduct = cart.products?.find {
                            it.productId == productId
                        }
                        if (existProduct != null) {
                            val newCart = cart.copy(
                                products = cart.products.map {
                                    if (it.productId == productId) {
                                        it.copy(quantity = quantity)
                                    } else {
                                        it
                                    }
                                }
                            )
                            emit(newCart)
                        } else {
                            throw Exception("Product not found in cart")
                        }
                    } else {
                        throw Exception("Cart is empty")
                    }
                }

                is Resource.Error -> {
                    throw Exception(userCart.message)
                }
            }
        }
    }

    override suspend fun deleteCart(userId: Int): Flow<CartResponse> {
        return flow {
            when (val userCart = cartService.deleteCart(userId)) {
                is Resource.Success -> {
                    val cart = userCart.data
                    emit(cart)
                }

                is Resource.Error -> {
                    throw Exception(userCart.message)
                }
            }
        }
    }

}