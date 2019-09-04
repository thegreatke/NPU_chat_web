function fenxie() {
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
var id;
function getid( str ) {
    id=str;
}
function  loading(){
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
                if(pid==0){
                    var id=result[i].id;
                    var title=result[i].title;
                    var pageName=result[i].pageName;
                    var content=result[i].moodWordContent;
                    var date=result[i].leaveWordDate;
                    var newHtml="        <div class=\"box\" name=\"box\">\n" +
                        "            <article class=\"format-standard type-post hentry clearfix\" ground>\n" +
                        "\n" +
                        "\n" +
                        "                <h3 class=\"post-title\" id=\"bbs-title\">\n" +
                        "                   <a href=\"foruminner\" onclick='getid(id)'>"+title+"</a>\n" +
                        "                </h3>\n" +
                        "\n" +
                        "                <div class=\"post-meta clearfix\" id=\"bbs-label\">\n" +
                        "                    <span class=\"date\">"+date+"</span>\n" +
                        "                    <span class=\"category\"><a href=\"#\"></a>"+pageName+"</span>\n" +
                        "                    <span class=\"like-count\">66</span>\n" +
                        "                </div>\n" +
                        "\n" +
                        "                <p id=\"bbs-main-content\">"+content+" <a class=\"readmore-link\" href=\"#\" id=\"bbs-readmore\">更多</a></p>\n" +
                        "\n" +
                        "            </article>\n" +
                        "            <hr>\n" +
                        "        </div>"
                    document.getElementById("container").innerHTML = document.getElementById("container").innerHTML+newHtml;
                   fenxie();
                }
            }


        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    })
}