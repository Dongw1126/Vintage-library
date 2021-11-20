package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
class LoginController(val userService: UserService) {

    @GetMapping("/login")
    fun login_form(req : HttpServletRequest) : String{ // login으로 접근했던 URI를 받아 세션에 기억해둔다
        val referer = req.getHeader("Referer")
        if(referer != "http://localhost:8080/login") // 다시 login으로 돌아오는 것 방지
            req.session.setAttribute("redirectURI", referer)
        return "login"
    }

    @PostMapping("/login")
    fun login(model : Model,
              @RequestParam("id") id : String,
              @RequestParam("pw") pw : String, session: HttpSession, req : HttpServletRequest) : String{
        val found = userService.findByUserId(id) // userId로 db 검색
        var logined = false
        if(found == null){
            //println("아예 없다")
        }
        else{
            if(pw == found.password){
                //println("찾았다")
                session.setAttribute("user", found) // session 에 유저 정보 등록
                logined = true
            }
            else{
                //println("아이디는 맞았다")
            }
        }

        if(logined == false) { // 로그인 실패
            return "redirect:/login"
        }

        var redirectURI = req.session.getAttribute("redirectURI") // 로그인 성공 후 접근했던 이전 URI로 연결
        if(redirectURI == null) // 이전 URI 없을 시 메인으로 보냄
            redirectURI = "/"
        else{
            redirectURI = req.session.getAttribute("redirectURI") //
        }
        return "redirect:" + redirectURI
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession) : String{
        session.invalidate()
        return "redirect:/"
    }

}