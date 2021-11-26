package com.vintagelibrary.backend.domain.entity

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Entity
@Table(name = "booktrans")
@Getter
@Setter
@NoArgsConstructor
class Booktrans(bookId: Long?, buyerId: Long?,
                sellerId: Long?, isInPurchase: Boolean) {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var transId: Long?=null // 거래 id
    var bookId = bookId // 책 id
    var buyerId = buyerId // 판매자 id
    var sellerId = sellerId // 구매자 id
    var isInPurchase = isInPurchase // 구매 진행중 여부
}