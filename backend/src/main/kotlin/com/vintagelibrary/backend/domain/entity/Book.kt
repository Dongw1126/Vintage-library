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
class Book(bookName : String,
           author : String, publisher : String, /*quality : String,
           classify : String, price : String,*/ imageName : String) {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id:Long?=null // 각 객체마다 고유한 id
    var bookName = bookName
    var author = author
    var publisher = publisher
/*    var quality = quality
    var classify = classify
    var price = price*/
    var imageName = imageName
}