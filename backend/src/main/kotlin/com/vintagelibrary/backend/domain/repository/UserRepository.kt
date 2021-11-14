package com.vintagelibrary.backend.domain.repository
import com.vintagelibrary.backend.domain.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserRepository : JpaRepository<User, Long>{
    fun findByUserId(userId:String) : User? // userId로 디비에 User 있는지 찾음
}