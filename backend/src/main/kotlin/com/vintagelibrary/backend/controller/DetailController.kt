package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.BookService
import com.vintagelibrary.backend.service.CartService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class DetailController(val bookService: BookService) {


    @GetMapping("/detail")
    fun test(req: HttpServletRequest, model : Model, @RequestParam bookid : String) : String{
        try{
            //println("bookId " + bookid)
            val book = bookService.findByBookId(bookid.toLong())
            if(book != null) {
                model.addAttribute("bookname", book.bookName)
                model.addAttribute("btype", book.bookType)
                model.addAttribute("bauth", book.author)
                model.addAttribute("bpub", book.publisher)
                model.addAttribute("bcomm", book.comm)
                model.addAttribute("bqual", book.quality)
                model.addAttribute("bpri", book.price)
                model.addAttribute("bimage", book.imageName)
            }
            else{
                return "null"
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return "detailtest";
    }

}