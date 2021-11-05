function getPrice(){
    let price = 0;    
    let stringArray = priceArray.map(v => parseInt(v.replace(",", "").replace("원", ""),10));
    stringArray.forEach(v => price += v);
    price = price.toLocaleString('ko-KR');
    console.log(price);
    totalPrice.innerHTML = "총 가격 : " + price + "원";
}

const totalPrice = document.getElementById('total-price');
const priceList = document.querySelectorAll('.book-price');

let priceArray = [];
priceList.forEach(v => {
    priceArray.push(v.innerHTML)
});
console.log(priceArray);

const cartTable = document.getElementById('cart-table');
const delBtnList = document.querySelectorAll('.delete-button');

delBtnList.forEach((v, idx) => v.addEventListener('click', () => {
    console.log(idx + " 삭제 " );
    priceArray[idx] = "0원";
    console.log(priceArray);
    cartTable.rows[idx+1].className = "hidden";
    getPrice();
}));

getPrice();