package com.vintagelibrary.backend.service

import com.vintagelibrary.backend.domain.entity.Cart
import com.vintagelibrary.backend.domain.repository.CartRepository
import org.springframework.stereotype.Service

@Service
class CartService(val cartRepository: CartRepository) {

    fun save(cart : Cart) : Cart{
        return cartRepository.save(cart)
    }

    fun findAllByUserId(userId : Long) : List<Cart>?{
        return cartRepository.findAllByUserId(userId)
    }

    fun findByBookId(bookId: Long) : Cart?{
        return cartRepository.findByBookId(bookId)
    }
    fun deleteByBookId(bookId: Long){
        cartRepository.deleteByBookId(bookId)
    }

    fun deleteByCartId(cartId : Long){
        cartRepository.deleteByCartId(cartId)
    }
}