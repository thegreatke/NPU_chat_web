function  signup() {

   var userName=$('#username').val();
    var password=$('#password').val();
    var gender=$('#gender').val();

   $.ajax({
     type:"POST",
       url:"/regist?password="+password+"&gender="+gender+"&userName="+userName,
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