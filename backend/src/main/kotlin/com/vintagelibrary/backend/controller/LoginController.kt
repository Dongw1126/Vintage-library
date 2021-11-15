package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class LoginController(val userService: UserService) {

    @GetMapping("/login")
    fun login_form() : String{
        return "login"
    }

    @PostMapping("/login")
    fun login(model : Model,
              @RequestParam("id") id : String,
              @RequestParam("pw") pw : String, session: HttpSession) : String{
        val found = userService.findByUserId(id) // userId로 db 검색
        if(found == null){
            println("아예 없다")
        }
        else{
            if(pw == found.password){
                println("찾았다")
                session.setAttribute("user", found) // session 에 유저 정보 등록
            }
            else{
                println("아이디는 맞았다")
            }
        }
        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession) : String{
        session.invalidate()
        return "redirect:/"
    }

}