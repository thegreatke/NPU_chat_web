function  signup() {

   var userName=$('#username').val();
    var password=$('#password').val();
    var email=$('#email').val();

   $.ajax({
     type:"POST",
       url:"/regist?password="+password+"&gender="+email+"&userName="+userName,
       data:{},
       datatype:"string",
       success:function(data){
        alert("data");
        if(data=="username is already exist, change one"){}
    },
       error:function(XMLHttpRequest, textStatus, errorThrown) {
         alert(XMLHttpRequest.responseText);
       }
   })
}

