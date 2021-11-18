package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.domain.repository.UserRepository
import com.vintagelibrary.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.io.PrintWriter
import java.lang.System.out
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class HomeController(val userService: UserService) {

    @GetMapping("/")
    fun index(req : HttpServletRequest) : String{
        println("이전 URL : " + req.getHeader("referer"))
        return "mainpage"
    }

    @GetMapping("/mypage")
    fun mypage(session: HttpSession, response: HttpServletResponse) : String{
        checkLogin(session, response)
        return "mypage"
    }

    @GetMapping("/research")
    fun research() : String{
        return "research_page"
    }

    @GetMapping("/register")
    fun register_form(session: HttpSession, response: HttpServletResponse) : String{
        checkLogin(session, response)
        return "bookregister"
    }

    @GetMapping("/search")
    fun book_search() : String{
        return "booksearch"
    }

    @GetMapping("/cart")
    fun cart(session: HttpSession, response: HttpServletResponse) : String{
        checkLogin(session, response)
        return "cart"
    }

    @GetMapping("/detail")
    fun detail() : String{
        return "detailpage"
    }

    fun checkLogin(session: HttpSession, response: HttpServletResponse){
        if(session.getAttribute("user") == null){
            response.setContentType("text/html; charset=UTF-8");
            val out : PrintWriter = response.getWriter();
            out.println("<script>" + "alert(\"먼저 로그인을 해주세요\");" + "location.href=\"login\";" + "</script>");
            out.flush();
        }
    }


}