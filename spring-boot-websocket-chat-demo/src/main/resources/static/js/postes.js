
function more(){
    var uname='';
    $.ajax({
        type: "GET",
        url: "/getname",
        data: {},
        async:false,
        datatype: "string",
        success: function (data) {
            uname =data;
        }
    });
    // alert(uname);
    return uname;
}

function  postes() {

    var tit=$('#titleone').val();
    var text=$('#textdivone').val();
    var tag=$('#tag').val();
  //  alert(tag);

    $.ajax({
        type:"POST",
        url:"/publishMoodMessage?moodMessageContent="+text+"&pageName="+tag+"&answerer="+more()+"&title="+tit,
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

