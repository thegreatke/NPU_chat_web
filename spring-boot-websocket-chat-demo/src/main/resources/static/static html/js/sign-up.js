function  signup() {

   var username=$('#username').val();
    var password=$('#password').val();
    var email=$('#gender').val();

   $.ajax({
     type:"POST",
       url:"127.0.0.1/regist?password="+password +"&gender="+email+"&userName="+username,
       data:{},
       datatype:"string",
       success:function(){
        alert("success");
    },
       error:function(XMLHttpRequest, textStatus, errorThrown) {
         alert(XMLHttpRequest.responseText);

       }
   })
}