
function checkedDisplayed(elem){
    let trs = document.getElementById("tasks-table").getElementsByTagName('tbody')[0]
        .getElementsByTagName("tr")
    for(let i=0;i<trs.length;i++){
        if(trs[i].style.display!="none"){
            trs[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0].checked=elem.checked;
        }
    }
}