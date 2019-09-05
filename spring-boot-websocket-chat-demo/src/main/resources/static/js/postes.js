function  postes() {

    var tit=$('#titleone').val();
    var text=$('#textdivone').val();
    var tag=$('#tag').val();
  //  alert(tag);

    $.ajax({
        type:"POST",
        url:"/publishMoodMessage?moodMessageContent="+text+"&pageName="+tag+"&answerer=npu&title="+tit,
        data:{},
        datatype:"string",
        success:function(data){
            alert("发帖成功");
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })


}

