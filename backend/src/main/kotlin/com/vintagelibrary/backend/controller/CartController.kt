package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.Booktrans
import com.vintagelibrary.backend.domain.entity.Cart
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.BookService
import com.vintagelibrary.backend.service.BooktransService
import com.vintagelibrary.backend.service.CartService
import com.vintagelibrary.backend.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class CartController(val booktransService: BooktransService,
                     val bookService: BookService, val cartService: CartService) {

    @GetMapping("/purchase")
    fun purchase(session: HttpSession): String {
        val currentUser: User = session.getAttribute("user") as User
        val currentCart = cartService.findAllByUserId(currentUser.id!!)

        if (currentCart != null) {
            for(item in currentCart) {
                val bookInfo = bookService.findByBookId(item.bookId)
                val booktransInfo = booktransService.findByBookId(item.bookId)

                booktransInfo.buyerId = currentUser.id
                // booktransService.save(Booktrans(item.bookId, currentUser.id, booktransInfo.sellerId, true))
                booktransService.save(booktransInfo)
            }
        }

        return "redirect:/mypageinfo"
/*        for(bookId in bookIds){
            val bookIdLong = bookId.toLong()
            val bookTrans = booktransService.findByBookId(bookIdLong)
            if(bookTrans != null){

            }
        }
        return */
    }

    @RequestMapping("/cart")
    @Transactional // 레포지토리에서 delete 사용하려면 필요한 것 같다
    fun cart(session: HttpSession, response: HttpServletResponse,
             request : HttpServletRequest, model: Model) : String?{
        if(session.getAttribute("user") == null) { // 로그인안했을 시 해당 코드 실행 후 끝
            response.contentType = "text/html; charset=UTF-8";
            val out : PrintWriter = response.writer;
            out.println("<script>" + "alert(\"먼저 로그인을 해주세요\");" + "location.href=\"login\";" + "</script>");
            out.flush();
            return null
        }

        // 장바구니에서 삭제 전달온 책들 처리
        val delBookIds = request.getParameterValues("deleteBookIds")
        if(delBookIds != null){
            for(delBookId in delBookIds){
                cartService.deleteByBookId(delBookId.toLong())
            }
        }
        val user = session.getAttribute("user") as User
        // 이전에 해당 유저가 담아놓은 장바구니 목록
        val prevCartList = cartService.findAllByUserId(user.id!!)
        val bookInCartList = ArrayList<Book>() // 해당 유저 장바구니에 담긴 책들 목록

        // 기존에 담겨있늘 책들 완성
        if(prevCartList != null) {
            for (prevBook in prevCartList) {
                val book = bookService.findByBookId(prevBook.bookId)
                if(book != null)
                    bookInCartList.add(book)
            }
        }

        // 새롭게 장바구니에 넘어온 책id
        var bookIds = request.getParameterValues("books");
        if(bookIds != null) {
            for (bookId in bookIds) {
                val book = bookService.findByBookId(bookId.toLong())
                var overlap = false
                var cartInUser = cartService.findAllByUserId(user.id!!)
                if (cartInUser != null) { // 기존 장바구니에 있는지, 중복되는지 탐색
                    for (bookInCart in cartInUser) {
                        if (bookInCart.bookId == book!!.bookid) {
                            //println("산거 또사려하네 이사람")
                            overlap = true
                            break;
                        }
                    }
                }
                if (!overlap) { // 장바구니에 없을 시 장바구니 목록에 추가
                    cartService.save(Cart(bookId.toLong(), user.id!!))
                    bookInCartList.add(book!!)
                }
            }

/*            println("장바구니 목록 출력 : ")
            for (books in bookInCartList) {
                println(books.bookName)
            }*/
        }

        // view단에 books로 장바구니 안의 책들을 넘겨준다
        model.addAttribute("books", bookInCartList)
        return "cart"
    }

}