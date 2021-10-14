function onButtons(action){
    let stringIds=checkedId()
    if(Boolean(stringIds))
        $.post({
            url: '/admin',
            data: {_csrf:$('#csrfToken').val(),action: action,ids:stringIds},
        }).done(function(){
            if(checkedId().indexOf($('meta[name=idUser]').attr('content'))>-1&&action=='delete'){
                $.post({
                    url: '/logout',
                    data: {_csrf:$('#csrfToken').val()}
                }).done(function(){
                    window.location.replace("/");
                })
            }else{
                window.location.replace('/admin');
            }
        });
}

function checkedId(){
    let stringIds="";
    let trs = document.getElementById("user-table").getElementsByTagName('tbody')[0]
        .getElementsByTagName("tr")
    for(let i=0;i<trs.length;i++){
        if(trs[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0].checked==true){
            let td = trs[i].getElementsByTagName('td')[1]
            if(Boolean(stringIds))
                stringIds+=","
            stringIds+=td.textContent||td.innerText
        }
    }
    return stringIds
}