function getPrice(){ // 장바구니에 담긴 가격 총합을 구하는 함수
    let price = 0; // 총 가격
    let priceInt = priceStringArray.map(v => parseInt(v.replace(",", "").replace("원", ""),10)); // Int로 형변환
    priceInt.forEach(v => price += v); // 총합 Int 계산
    price = price.toLocaleString('ko-KR'); // 이후 ko-KR에 맞춰 String 포맷 맞춤
    console.log(price);
    if(price != 0){ // 장바구니가 빈 경우
        totalPrice.innerHTML = "장바구니에 아무것도 없습니다."
    }
    else{ // 장바구니에 무언가 있음
        totalPrice.innerHTML = "총 가격 : " + price + "원";
    }
    
}
const totalPrice = document.getElementById('total-price'); // 총합을 나타내는 h3
const priceList = document.querySelectorAll('.book-price'); // 모든 book-price 모음

let priceStringArray = []; // 가격 문자열 상태의 모음
priceList.forEach(v => { // book-price들에게서 innerHTML 문자값 추출
    priceStringArray.push(v.innerHTML)
});

if(priceStringArray.length === 0){ // 장바구니 빈 경우
    totalPrice.innerHTML = "장바구니에 아무것도 없습니다."
}

const cartTable = document.getElementById('cart-table'); // 장바구니 표
const delBtnList = document.querySelectorAll('.delete-button'); // 삭제버튼 모음

delBtnList.forEach((v, idx) => v.addEventListener('click', () => { // 각각의 삭제버튼에 클릭이벤트
    console.log(idx + " 삭제 " );
    priceStringArray[idx] = "0원"; // 해당 priceString을 0원으로 만듬(getPrice에서 += 0)
    console.log(priceStringArray);
    cartTable.rows[idx+1].className = "hidden"; // 행을 삭제하는 대신에 hidden 클래스로 만들어 숨김
    getPrice(); // 버튼 누를시 총합 다시 계산
}));

getPrice(); // 처음 화면 가격 총합 계산