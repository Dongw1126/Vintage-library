package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.CartService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class DetailController {

    @PostMapping("/detail1")
    @ResponseBody // js 사용 위함
    fun detail(req: HttpServletRequest, model : Model) : String{
        try{
            // name id pw email address
            val name = req.getParameter("bookname")
            model.addAttribute("bookname", name)
        }catch (e:Exception){
            e.printStackTrace()
        }


        return "detailtest";
    }


}