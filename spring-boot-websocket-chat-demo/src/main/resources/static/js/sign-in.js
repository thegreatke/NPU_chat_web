function  signIn() {

    var userName=$('#login-username').val();
    var password=$('#login-password').val();
    var result;


    $.ajax({
        type:"POST",
        url:"/login?userName="+userName+"&password="+password,
        data:{},
        datatype:"string",
        success:function(data){
            window.location.href='index';
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })


}

