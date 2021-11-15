package com.vintagelibrary.backend.domain.entity

import lombok.*
import javax.persistence.*

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
class User(name : String, userId : String, password: String, email : String, address : String){
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id:Long?=null // 각 객체마다 고유한 id
    var name:String = name // 유저 실명
    var userId:String = userId // 유저가 지정한 id(==nickname)
    var password:String = password
    var email:String = email
    var address:String = address
    // name id pw email address
}