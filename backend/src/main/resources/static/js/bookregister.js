function openChild()
{
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    window.open("/search",
        "childForm", "width=570, height=350, resizable = no, scrollbars = no");
}

document.querySelector("#openChild").addEventListener("click", openChild);