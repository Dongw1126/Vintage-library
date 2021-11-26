package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession

@Controller
class ResearchController (val bookService: BookService){

    @GetMapping("/research")
    fun research(model : Model, @RequestParam qs : String) : String{
/*
        var bookList = ArrayList<Book>()
        bookList.add(Book("book1","작가1","출판1", "최상", "소설", "15000원", "첫번째책입니다","3_test_image.jpg"))
        bookList.add(Book("book2","작가2","출판2", "중", "전공", "53000원", "두번째책입니다","3_test_image.jpg"))
        bookList.add(Book("book3","작가3","출판3", "하", "소설", "6500원", "세번째책입니다","3_test_image.jpg"))
*/
/*        if (bookList != null) {
            for (books in bookList){
                println(books.bookname)
            }
        }*/
        var bookList = bookService.searchByBookname(qs)
        if(bookList != null)
            bookList = bookList.reversed() // 최신순으로 뒤집어줌
        model.addAttribute("bookList", bookList)
        return "research_page"
    }
}