package com.vintagelibrary.backend.domain.repository

import com.vintagelibrary.backend.domain.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    fun findByBookid(bookid: Long) : Book?
    fun findByBookName(bookName: String) : Book?
    fun findAllByBookNameContains(bookName: String) : List<Book>?
}