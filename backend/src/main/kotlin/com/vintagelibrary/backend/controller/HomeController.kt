package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.domain.repository.UserRepository
import com.vintagelibrary.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class HomeController(val userService: UserService) {

    @GetMapping("/")
    fun index() : String{
        return "mainpage"
    }

    @GetMapping("/mypage")
    fun mypage(@SessionAttribute("user") user : User?) : String{
        if(user == null){
            println("로그인 정보가 없어")
            return "redirect:/login"
        }
        else {
            return "mypage"
        }
    }

    @GetMapping("/research")
    fun research() : String{
        return "research_page"
    }

    @GetMapping("/register")
    fun register_form() : String{
        return "bookregister"
    }

    @GetMapping("/cart")
    fun cart() : String{
        return "cart"
    }

    @GetMapping("/detail")
    fun detail() : String{
        return "book1_detailpage"
    }


}