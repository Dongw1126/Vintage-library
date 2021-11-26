package com.vintagelibrary.backend.domain.entity

import javax.persistence.*

@Entity
@Table(name = "cart")
class Cart(bookId: Long, userId: Long, /*available : Boolean*/) {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var cartId:Long?=null // 각 객체마다 고유한 id
    var bookId:Long=bookId
    var userId:Long=userId
    //var available:Boolean = available // 0 = delete 1 = on
}