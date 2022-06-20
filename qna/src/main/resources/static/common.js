function sleep(ms) {
    const wakeUpTime = Date.now() + ms;
    while (Date.now() < wakeUpTime) {}
}
function hgj(selector, texts){
    for(let i=0;i<texts.length;i++){
        setTimeout(function(){
            //$(selector).append("<td>"+texts[i]+"</td>");
            $(selector).text(texts.substr(0,i+1));
        },(i+1)*100);

    }
}

//공백포함여부(false여야함)
function stdNameValid1(stdName){
    //alert("공백확인");
    if(stdName.search(/\s/) != -1) {
        return true;//공백있음
    } else {
        return false;
    }
}
//특문포함여부(false여야함)
function stdNameValid2(stdName){
    //alert("특문확ㅇ니");
    var special_pattern = /[(),{}`~!@#$%^&*|\\\'\";:\/?]/gi;

    if(special_pattern.test(stdName) == true) {
        return true;//특문있음
    } else {
        return false;
    }
}