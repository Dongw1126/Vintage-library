function getParentText(){
    $('#search-result-div').remove();
    $('#recycle-div').append("<div id=\"search-result-div\"></div>")
    search(document.getElementById("cInput").value)
}
function search(book_name){
    $.ajax({
        method: "GET",
        url: "https://dapi.kakao.com/v3/search/book?target=title",
        data: { query: book_name , size : 10},
        headers: { Authorization: "KakaoAK 4bbf689c01c573ecde4edf8a2551bad1" }
    })
        .done(function (msg) {
            book_list = []
            msg.documents.forEach((element, index) => {
                $( "#search-result-div" ).append( '<strong class="book_name_list' + index + '">' + msg.documents[index].title + "</strong><br>" );
                $( "#search-result-div" ).append( "<img src='" + msg.documents[index].thumbnail + "'/><br>" );
                $( "#search-result-div" ).append( "<strong> 출판사 : " + msg.documents[index].publisher + "</strong><br><br><hr><br>" );
                $('.book_name_list' + index).on({
                    "click" : function() {
                        window.opener.document.getElementById("bookname").value = msg.documents[index].title;
                        window.opener.document.getElementById("author").value = msg.documents[index].authors[0];
                        window.opener.document.getElementById("publisher").value = msg.documents[index].publisher;
                        window.close();
                    }

                })
            });
        });
}

search(window.opener.document.getElementById("bookname").value);
