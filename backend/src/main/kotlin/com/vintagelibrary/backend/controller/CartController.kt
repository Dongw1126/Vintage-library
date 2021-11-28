package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.entity.Cart
import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.BookService
import com.vintagelibrary.backend.service.BooktransService
import com.vintagelibrary.backend.service.CartService
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class CartController(val booktransService: BooktransService,
                     val bookService: BookService, val cartService: CartService) {

    @Transactional
    @ResponseBody
    @GetMapping("/purchase")
    fun purchase(session: HttpSession): String {
        val currentUser: User = session.getAttribute("user") as User
        val currentCart = cartService.findAllByUserId(currentUser.id!!)
        var scripts = "<script>"
        var flag = false
        if (currentCart != null) {
            for(item in currentCart) {
                val bookInfo = bookService.findByBookId(item.bookId)
                val booktransInfo = booktransService.findByBookId(item.bookId)
                if(booktransInfo.state == 0L) {
                    booktransInfo.buyerId = currentUser.id
                    booktransInfo.state = 1L
                    booktransService.save(booktransInfo)
                }
                else{ // 장바구니에 담아놓았던 책이 누군가에게 팔렸음
                    // 리다이렉트 전에 플래그로 alert 띄우기?
                    if(!flag) {
                        scripts += "alert(`"
                        flag = true
                    }
                    val book = bookService.findByBookId(item.bookId)
                    scripts += book!!.bookName
                    scripts += "\n"
                }
                cartService.deleteByCartId(item.cartId!!)
            }
        }

        if(flag)
            scripts += "책은 현재 구매 진행중이거나 판매 완료되었습니다. \n자동으로 구매 목록에서 제외됩니다.'`);"
        scripts += "location.href='mypageinfo';" + "</script>"

        println("scripts " + scripts)

        return scripts
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
        // 근데 이제 현재 유저에 해당하는 목록들만 지워야겠지?
        val delBookIds = request.getParameterValues("deleteBookIds")
        val user = session.getAttribute("user") as User

        if(delBookIds != null){
            val books = cartService.findAllByUserId(user.id!!) // 일단 현재 유저 장바구니 목록 가져와서
            for(delBookId in delBookIds){ // 넘어온 삭제할 책 번호에 대하여
                if (books != null) {
                    for(bookInCart in books){ // 장바구니 목록에서
                        if(bookInCart.bookId == delBookId.toLong() && bookInCart.userId == user.id!!) // 장바구니 정보에서 책정보와 유저정보가 같으면,
                            cartService.deleteByCartId(bookInCart.cartId!!) // 해당 장바구니 목록을 삭제
                    }
                }
            }
        }
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