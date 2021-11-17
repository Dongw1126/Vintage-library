package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
class SignupController(val userService: UserService) {

    @GetMapping("/signup")
    fun signup_form() : String{
        return "signup"
    }

    @PostMapping("/signup")
    @ResponseBody // js 사용 위함
    fun signup(req: HttpServletRequest) : String{
        try{
            // name id pw email address
            val name = req.getParameter("name")
            val id = req.getParameter("id")
            val pw = req.getParameter("pw")
            val email = req.getParameter("email")
            val address = req.getParameter("address")

            println(id);

            val overlap = userService.findByUserId(id) // 이미 해당 id 가진 사람이 존재할 시

            if(overlap != null){ // 재시도 요청
                return "<script>" + "alert(\"이미 있는 아이디입니다.\");" + "location.href=\"signup\";" + "</script>";
            }

            // 없을 시 입력받은 정보로 db에 새로운 User 저장
            userService.save(User(name,id,pw,email,address))
        }catch (e:Exception){
            e.printStackTrace()
        }

        // 회원가입 후 로그인창으로 리다이렉트
        return "<script>" + "alert(\"회원 가입 성공!\");" + "location.href=\"login\";" + "</script>";
    }
}