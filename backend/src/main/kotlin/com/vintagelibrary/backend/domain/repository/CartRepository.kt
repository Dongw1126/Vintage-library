package com.vintagelibrary.backend.domain.repository

import com.vintagelibrary.backend.domain.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long>{
    fun findAllByUserId(userId : Long) : List<Cart>?
    fun findByBookId(bookId : Long) : Cart?
    fun deleteByBookId(bookId : Long)
    fun deleteByCartId(cartId : Long)
}