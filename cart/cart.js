function getPrice(){
    let price = 0;
    let array = [];
    priceList.forEach(v => {array.push(v.innerHTML)});
    
    let stringArray = array.map(v => parseInt(v.replace(",", "").replace("원", ""),10));
    stringArray.forEach(v => price += v);
    price = price.toLocaleString('ko-KR');
    console.log(price);
    totalPrice.innerHTML = "총 가격 : " + price + "원";
}

const totalPrice = document.getElementById('totalPrice');
const priceList = document.querySelectorAll('.priceList');

//console.log(priceList);
getPrice();
