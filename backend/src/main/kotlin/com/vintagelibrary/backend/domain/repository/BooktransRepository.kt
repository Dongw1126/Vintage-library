package com.vintagelibrary.backend.domain.repository
import com.vintagelibrary.backend.domain.entity.Booktrans
import org.springframework.data.jpa.repository.JpaRepository

interface BooktransRepository: JpaRepository<Booktrans, Long> {
    fun findByBookId(bookId: Long) : Booktrans
    fun findAllBySellerId(sellerId: Long) : List<Booktrans>?
    fun findAllByBuyerId(buyerId: Long) : List<Booktrans>?
}