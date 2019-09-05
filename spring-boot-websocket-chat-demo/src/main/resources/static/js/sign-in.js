function  signIn() {

    var userName=$('#login-username').val();
    var password=$('#login-password').val();
    var result;

    $.ajax({
        type:"GET",
        url:"/setname?name="+userName,
        data:{},
        datatype:"string",
        success:function(data) {

        }
    })

    $.ajax({
        type:"POST",
        url:"/login?userName="+userName+"&password="+password,
        data:{},
        datatype:"string",
        success:function(data){

           if(data=="login successfully!"){
               window.location.href="index";
           }
           else{
               alert(data);
           }

        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })


}

