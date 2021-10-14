function logout(){
    $.post({
        url: '/logout',
        data: {_csrf:$('#csrfToken').val()}
    }).done(function(){
        window.location.replace("/");
    })
}