
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

