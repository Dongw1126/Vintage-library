package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.service.PostService
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest


@CrossOrigin
@RestController
class PostController(val postService: PostService) {
    @PostMapping("/upload")
    // bookname author publisher
    fun upload(req: HttpServletRequest, @RequestParam("image") multipartFile: MultipartFile) : String{

        val bookName = req.getParameter("bookname")
        val author = req.getParameter("author")
        val publisher = req.getParameter("publisher")
        var imageName: String = StringUtils.cleanPath(multipartFile.originalFilename.toString())
/*        user.setPhotos(fileName)
        val savedUser: User = repo.save(user)*/
        val uploadDir = "image-upload/"
        imageName = (1+postService.count()).toString() + "_" + imageName
        //imageName = postService.count().toString()
        postService.imageUpload(uploadDir, imageName, multipartFile)
        postService.save(Book(bookName, author, publisher, imageName))
        //return RedirectView("/users", true)
        return "<script>" + "alert(\"책 등록 성공!\");" + "location.href='/';" + "</script>";
    }
}