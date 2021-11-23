package com.vintagelibrary.backend.service

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {
    fun findByName(bookname: String) : Book? {
        return bookRepository.findByBookName(bookname)
    }
    fun findAll() : List<Book>?{
        return bookRepository.findAll()
    }

    fun searchByBookname(bookname: String) : List<Book>?{
        return bookRepository.findAllByBookNameContains(bookname)
    }

}