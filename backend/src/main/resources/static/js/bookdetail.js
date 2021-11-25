function bookdetail(book_name){
    $.ajax({
        method: "GET",
        url: "https://dapi.kakao.com/v3/search/book?target=title",
        data: { query: book_name , size : 10},
        headers: { Authorization: "KakaoAK 4bbf689c01c573ecde4edf8a2551bad1" }
    })
        .done(function (msg) {
                $( "#book_title" ).append( '<strong class="book_name_list' + index + '">' + msg.documents[index].title + "</strong><br>" );
                $( "#book_image" ).append( "<img src='" + msg.documents[index].thumbnail + "'/><br>" );
                $( "#book_author" ).append( "<strong> 저자 : " + msg.documents[index].author + "</strong>" );
                $( "#book_publisher" ).append( "<strong> 출판사 : " + msg.documents[index].publisher + "</strong><br><br><hr><br>" );
                $( "#book_info" ).append( "<strong> 도서소개 : " + msg.documents[index].contents + "</strong>" );
                $( "#book_info" ).append( "<strong> 정가 : " + msg.documents[index].price + "</strong>" );}
            );
}