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