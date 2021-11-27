package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.CartService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class DetailController {

    @PostMapping("/detail")
    fun detail(req: HttpServletRequest, model : Model) : String{
        try{
            // name id pw email address
            val name = req.getParameter("bookname")
            val type = req.getParameter("booktype")
            val auth = req.getParameter("bookauthor")
            val pub = req.getParameter("bookpub")
            val comm = req.getParameter("bookcomm")
            val qual = req.getParameter("bookqual")
            val pri = req.getParameter("bookpri")

            model.addAttribute("bookname", name)
            model.addAttribute("btype", type)
            model.addAttribute("bauth", auth)
            model.addAttribute("bpub", pub)
            model.addAttribute("bcomm", comm)
            model.addAttribute("bqual", qual)
            model.addAttribute("bpri", pri)
        }catch (e:Exception){
            e.printStackTrace()
        }


        return "detailtest";
    }

    fun imageGet(uploadDir: String?, fileName: String multipartFile: MultipartFile){
        val getPath: Path = Paths.get(uploadDir)
        try{

        }
    }


}