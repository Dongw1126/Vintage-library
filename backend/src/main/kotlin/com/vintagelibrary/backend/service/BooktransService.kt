package com.vintagelibrary.backend.service

import com.vintagelibrary.backend.domain.entity.Booktrans
import com.vintagelibrary.backend.domain.repository.BooktransRepository
import org.springframework.stereotype.Service

@Service
class BooktransService(val booktransRepository: BooktransRepository) {
    fun save(booktrans: Booktrans): Booktrans {
        return booktransRepository.save(booktrans);
    }

    fun findById(booktransId: Long): Booktrans {
        return booktransRepository.findById(booktransId).get()
    }

    fun findByBookId(bookId: Long): Booktrans {
        return booktransRepository.findByBookId(bookId)
    }

    fun findAllBySellerId(sellerId: Long): List<Booktrans>? {
        return booktransRepository.findAllBySellerId(sellerId);
    }
    fun findAllByBuyerId(buyerId: Long): List<Booktrans>? {
        return booktransRepository.findAllByBuyerId(buyerId);
    }
}