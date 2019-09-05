

function getid(str) {

    $.ajax({
        type:"GET",
        url:"/setid?id="+str,
        data:{},
        datatype:"string",
        success:function() {
        }
        })
}
function fenye() {

    var $div = $(".container");
    var $show = $("#show");
    var $pages = $("#pages");
    var $moment= $("#moment");
    var pgindex = 1;
    var eachCnt = 4;
    var boxes = $("div[name='box']");
    var cnt = boxes.length;
    var indexs = new Array(cnt);
    for(var i=0; i<cnt; i++) {
        indexs[i] = i;
    }
    var allPages = Math.ceil(cnt/eachCnt);
    $(function() {
        $pages.html("总共 " + allPages + " 页");
        $moment.html("目前:第"+pgindex+"页");
        showPage(1);
        for(var i=0; i<allPages; i++) {
            $pages.append("<a href=\"javascript:showPage('"+ (i+1) +"');\"> "+ (i+1) +" </a>");
        }
        $pages.append("<a href=\"javascript:gotopage(-1);\">上一页</a> <a href=\"javascript:gotopage(1);\">下一页</a>");

    });

    function gotopage(value){
        try{
            value=="-1"?showPage(pgindex-1):showPage(pgindex+1);
        }catch(e){

        }
    }

    function showPage(pageIndex)
    {
        if(pageIndex== 0 || pageIndex>=(allPages+1)) {
            return;
        }
        var start = (pageIndex-1)*eachCnt;

        var end = start + eachCnt;
        end = end > cnt ? cnt : end;//16

        var subIndexs = indexs.slice(start, end);
        for(var i=0; i<cnt; i++) {
            if(contains(i, subIndexs)) {
                boxes.eq(i).show();
            }else{
                boxes.eq(i).hide();
            }
        }
        pgindex = pageIndex;
        $moment.empty();
        $moment.html("目前:第"+pgindex+"页");
    }

    var contains = function (element, arr) {

            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == element) {
                    return true;
                }
            }
            return false;
        }


}


function  loading(){

    $.ajax({
        type:"GET",
        url:"/allMoodWord",
        data:{},
        async:false,
        datatype:"JSON",
        success:function(data){
          var a= JSON.stringify(data);
          var b=eval("("+a+")");
          var result=b.result;

         for(var i=0;i<result.length;i++)
            {

                var pid =result[i].pid;
                if(pid==0){
                    var id=result[i].id;
                    var title=result[i].title;
                    var pageName=result[i].pageName;
                    var content=result[i].moodWordContent;
                    var date=result[i].leaveWordDate;
                    var likes=result[i].likes;
                    var newHtml="        <div class=\"box\" name=\"box\">\n" +
                        "            <article class=\"format-standard type-post hentry clearfix\" ground>\n" +
                        "\n" +
                        "\n" +
                        "                <h3 class=\"post-title\" id=\"bbs-title\">\n" +
                        "                   <a href=\"foruminner\" id=\'"+id+"\ ' onclick='getid(this.id)'>"+title+"</a>\n" +
                        "                </h3>\n" +
                        "\n" +
                        "                <div class=\"post-meta clearfix\" id=\"bbs-label\">\n" +
                        "                    <span class=\"date\">"+date+"</span>\n" +
                        "                    <span class=\"category\"><a href=\"#\"></a>"+pageName+"</span>\n" +
                        "                    <span id='like' class=\"like-count\" onclick='getid()'>"+likes+"</span>\n" +
                        "                </div>\n" +
                        "\n" +
                        "                <p id=\"bbs-main-content\">"+content+" <a class=\"readmore-link\" href=\"#\" id=\"bbs-readmore\">更多</a></p>\n" +
                        "\n" +
                        "            </article>\n" +
                        "            <hr>\n" +
                        "        </div>"
                    document.getElementById("container").innerHTML = document.getElementById("container").innerHTML+newHtml;

                }

            }
         fenye();


        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })
}

function lik() {

}


function replayLoading(){
    //主贴
    var uid;
    $.ajax({
        type: "GET",
        url: "/getid",
        data: {},
        datatype: "string",
        success: function (data) {
                uid=data;
        }
    })
    $.ajax({
        type:"GET",
        url:"/allMoodWord",
        data:{},
        datatype:"JSON",
        success:function(data){
            var a= JSON.stringify(data);
            var b=eval("("+a+")");
            var result=b.result;

            for(var i=0;i<result.length;i++)
            {
                var id =result[i].id;

                if(uid==id){

                    var title=result[i].title;
                    var pageName=result[i].pageName;
                    var userName=result[i].answerer;
                    var content=result[i].moodWordContent;
                    var date=result[i].leaveWordDate;
                    var likes=result[i].likes;
                    var newHtml=" <article class=\"format-standard type-post hentry clearfix content\">\n" +
                        "\n" +
                        "        <h1 class=\"post-mian-title\" id=\"bbs-title\" >\n" +
                        "            <a href=\"javascript:void(0);\">"+title+"</a>\n" +
                        "        </h1>\n" +
                        "        <div style=\"width: 100%\">\n" +
                        "            <div style=\"width: 95%;float: right;padding-left: 0px\">\n" +
                        "\n" +
                        "                <p id=\"bbs-content\">"+content +" </p>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"d_author\" style=\"width: 5%\">\n" +
                        "                <ul class=\"user\">\n" +
                        "\n" +
                        "                    <li class=\"icon\">\n" +
                        "                        <div>\n" +
                        "                            <a style=\"\" class=\"p_author\"><img username=\"NPU官方\" width=\"100%\" class=\"usericon\" id=\"test\" src=\"../../static/images/forum1.jpg\"/></a>\n" +
                        "                        </div>\n" +
                        "                    </li>\n" +
                        "                    <li class=\"username\" style=\"text-align: center;font-size: 18px;\">\n" +
                        "\n" +
                        "                        <a href=\"javascript:void(0)\">"+userName+"</a>\n" +
                        "\n" +
                        "                    </li>\n" +
                        "\n" +
                        "                </ul>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"post-meta clearfix\" id=\"bbs-label\" style=\"float: right\">\n" +
                        "            <span class=\"date\">"+date+"</span>\n" +
                        "            <span class=\"category\"><a href=\"#\" >"+pageName+"</a></span>\n" +
                        "            <span class=\"like-count\"><a href=\"\" id=\"good\">"+likes+"</a></span>\n" +
                        "        </div>\n" +
                        "    </article>\n" +
                        "    <hr>";
                    document.getElementById("mainForum").innerHTML=newHtml;
                    break;
                }


            }
            replay();

        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })

}

function replay(){
    var uid;
    $.ajax({
        type: "GET",
        url: "/getid",
        data: {},
        datatype: "string",
        success: function (data) {
            uid=data;
        }
    })

    $.ajax({
        type:"GET",
        url:"/allMoodWord",
        data:{},
        datatype:"JSON",
        success:function(data){
            var a= JSON.stringify(data);
            var b=eval("("+a+")");
            var result=b.result;


            for(var i=0;i<result.length;i++)
            {

                var pid =result[i].pid;
                if(pid==uid){
                    var title=result[i].title;
                    var pageName=result[i].pageName;
                    var userName=result[i].answerer;
                    var content=result[i].moodWordContent;
                    var date=result[i].leaveWordDate;
                    var likes=result[i].likes;
                    var newHtml=" <article class=\"format-standard type-post hentry clearfix content\">\n" +
                        "\n" +
                        "        <h1 class=\"post-mian-title\" id=\"bbs-title\" >\n" +

                        "        </h1>\n" +
                        "        <div style=\"width: 100%\">\n" +
                        "            <div style=\"width: 95%;float: right;padding-left: 0px\">\n" +
                        "\n" +
                        "                <p id=\"bbs-content\">"+content +" </p>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <div class=\"d_author\" style=\"width: 5%\">\n" +
                        "                <ul class=\"user\">\n" +
                        "\n" +
                        "                    <li class=\"icon\">\n" +
                        "                        <div>\n" +
                        "                            <a style=\"\" class=\"p_author\"><img username=\"NPU官方\" width=\"100%\" class=\"usericon\" id=\"test\" src=\"../../static/images/forum1.jpg\"/></a>\n" +
                        "                        </div>\n" +
                        "                    </li>\n" +
                        "                    <li class=\"username\" style=\"text-align: center;font-size: 18px;\">\n" +
                        "\n" +
                        "                        <a href=\"javascript:void(0)\">"+userName+"</a>\n" +
                        "\n" +
                        "                    </li>\n" +
                        "\n" +
                        "                </ul>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"post-meta clearfix\" id=\"bbs-label\" style=\"float: right\">\n" +
                        "            <span class=\"date\">"+date+"</span>\n" +
                        "            <span class=\"category\"><a href=\"#\" >"+pageName+"</a></span>\n" +
                        "            <span class=\"like-count\"><a href=\"\" id=\"good\">"+likes+"</a></span>\n" +
                        "        </div>\n" +
                        "    </article>\n" +
                        "    <hr>";
                    document.getElementById("mainForum").innerHTML= document.getElementById("mainForum").innerHTML+newHtml;
                }

            }


        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })
}