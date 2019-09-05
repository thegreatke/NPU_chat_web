function  getName( str) {
    $.ajax({
        type:"GET",
        url:"/setid?id="+str,
        data:{},
        datatype:"string",
        success:function() {

        }
    })
}