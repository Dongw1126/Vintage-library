package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.service.BooktransService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class TransactionController(val booktransService: BooktransService) {
    @GetMapping("/purchase")
    fun purchase(session: HttpSession, req: HttpServletRequest): String {
        try {
            println("in purchase");
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "cart";
    }
}