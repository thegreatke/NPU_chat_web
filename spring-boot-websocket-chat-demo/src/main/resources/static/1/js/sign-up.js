function  signup() {

   var username=$('#username').val();
    var password=$('#password').val();
    var gender=$('#gender').val();

   $.ajax({
     type:"POST",
       url:"127.0.0.1/regist?password="+password +"&gender="+gender+"&userName="+username,
       data:{},
       datatype:"text",
       success:function(){
        alert("success");
    },
       error:function(XMLHttpRequest, textStatus, errorThrown) {
         alert(XMLHttpRequest.responseText);
         alert(XMLHttpRequest.status);
         alert(textStatus);

       }
   })
}