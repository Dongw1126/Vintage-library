package com.vintagelibrary.backend.controller

import com.vintagelibrary.backend.domain.entity.User
import com.vintagelibrary.backend.service.BookService
import com.vintagelibrary.backend.service.BooktransService
import com.vintagelibrary.backend.service.CartService
import com.vintagelibrary.backend.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
class MypageController (val booktransService: BooktransService,
                        val bookService: BookService, val userService: UserService, val cartService: CartService) {
    @GetMapping("/mypageinfo")
    fun getInfo(model: Model, session: HttpSession): String {
        val user = session.getAttribute("user") as User
        val buyingList = booktransService.findAllByBuyerId(user.id!!)
        val sellingList = booktransService.findAllBySellerId(user.id!!)
        val cartList = cartService.findAllByUserId(user.id!!)

        data class BookTransInfo(var bookImage:String, var bookName:String,
                              var bookAuthor:String, var bookStat: String,
                              var bookSeller: String, var bookBuyer: String,
                              var transId: Long?, var sellerAccount: String? = "")

        data class CountInfo(var inCart:Int, var inPur:Int, var pur:Int, var inSell:Int, var sell:Int)

        val buyingBookList = mutableListOf<BookTransInfo>()
        val sellingBookList = mutableListOf<BookTransInfo>()

        var inCart = 0
        var inPur = 0
        var pur = 0
        var inSell = 0
        var sell = 0
        
        // 현재 사용자가 구매중인 리스트
        if (buyingList != null) {
            for(item in buyingList) {
                val b = bookService.findByBookId(item.bookId!!)
                val seller = userService.findById(item.sellerId!!)
                var stat = ""
                if (item.isInPurchase) {
                    inPur++
                    stat = "구매진행중"
                } else {
                    pur++
                    stat = "구매완료"
                }

                val obj = BookTransInfo(b!!.imageName, b.bookName, b.author, stat,
                                        seller.name, user.name, item.transId, seller.account)
                buyingBookList.add(obj)
            }
        }

        // 현재 사용자가 판매중인 리스트
        if (sellingList != null) {
            for(item in sellingList) {
                val b = bookService.findByBookId(item.bookId!!)
                var buyer = "미정"
                if (item.buyerId != -1L) {
                    val tmp = userService.findById(item.buyerId!!)
                    buyer = tmp.name
                }
                var stat = ""
                if (item.isInPurchase) {
                    inSell++
                    if (buyer.equals("미정")) stat = "판매중"
                    else stat = "구매진행중"
                } else {
                    sell++
                    stat = "판매완료"
                }

                val obj = BookTransInfo(b!!.imageName, b.bookName, b.author, stat, user.name, buyer, item.transId)
                sellingBookList.add(obj)
            }
        }

        if (cartList != null) { // 현재 유저의 장바구니에 담긴 책 개수
                inCart = cartList.size
        }

        val countInfo = CountInfo(inCart, inPur, pur, inSell, sell)

        model.addAttribute("sellingBookList", sellingBookList)
        model.addAttribute("buyingBookList", buyingBookList)
        model.addAttribute("countInfo", countInfo)

        return "mypage"
    }

    @PostMapping("/payconfirm")
    fun paymentConfirm(req: HttpServletRequest): String {
        val transId = req.getParameter("transId")
        val bookTransInfo = booktransService.findById(transId.toLong())
        bookTransInfo.isInPurchase = false
        booktransService.save(bookTransInfo)

        return "redirect:/mypageinfo"
    }

}