package com.vintagelibrary.backend.domain.entity

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
class Book(bookName : String, author : String,
           publisher : String, quality : String,
           bookType: String, price : String,
           comm : String, imageName : String)
/*  price : String,*/ {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var bookid:Long?=null // 각 객체마다 고유한 id
    var bookName = bookName
    var bookType = bookType
    var author = author
    var publisher = publisher
    var comm = comm
    var quality = quality
    var price = price
    var imageName = imageName
}