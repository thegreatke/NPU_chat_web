function  signin() {

    var userName=$('#login-username').val();
    var password=$('#login-password').val();


    $.ajax({
        type:"POST",
        url:"/login?userName="+userName+"&password="+password,
        data:{},
        datatype:"string",
        success:function(data){
            window.location.href="www.baidu.com";
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })
}

