document.addEventListener("DOMContentLoaded",function (){
    if($('meta[name=blocked]').attr('content')=='true'){
        alert("Your account is blocked!!!")
        $.post({
            url: '/logout',
            data: {_csrf:$('#csrfToken').val()}
        }).done(function(){
            window.location.replace("/");
        })
    }
})