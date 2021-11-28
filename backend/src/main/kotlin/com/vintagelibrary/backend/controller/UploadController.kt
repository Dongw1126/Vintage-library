package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.Booktrans
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.BooktransService
import com.vintagelibrary.backend.service.PostService
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@CrossOrigin
@RestController
class UploadController(val postService: PostService, val booktransService: BooktransService) {
    @PostMapping("/upload")
    // bookname author publisher
    fun upload(session: HttpSession, req: HttpServletRequest, @RequestParam("image") multipartFile: MultipartFile) : String{


        /*
        * class Book(bookname : String, author : String,
           publisher : String, quality : String,
           booktype: String, price : Long,
           comm : String, imageName : String)
        *
        * */

        val bookName = req.getParameter("bookname")
        val author = req.getParameter("author")
        val publisher = req.getParameter("publisher")
        val quality = req.getParameter("quality")
        val booktype = req.getParameter("booktype")
        val price = req.getParameter("price")
        val comment = req.getParameter("comment")
        var imageName: String = StringUtils.cleanPath(multipartFile.originalFilename.toString())

        val uploadDir = "src/main/resources/static/image-upload/" // 업로드된 이미지 폴더
        imageName = (1+postService.count()).toString() + "_" + imageName
        //imageName = bookId_원래이름
        postService.imageUpload(uploadDir, imageName, multipartFile)
        val currentBook = postService.save(Book(bookName, author, publisher, quality, booktype, price, comment, imageName))
        val currentUser: User = session.getAttribute("user") as User

        booktransService.save(Booktrans(currentBook.bookid, -1, currentUser.id, true, 0L))

        return "<script>" + "location.href='/';" + "</script>";
    }
}