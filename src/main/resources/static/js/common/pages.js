/**
 * Created by Administrator on 2018/6/6 0006.
 */

var totalPage = 0,pp = "",c = '"b"',d = 'this.value=this.value.replace(/[^0-9]/g,"")',to = "onkeyup='"+d+",document.getElementById("+c+").value=this.value' onblur='document.getElementById("+c+").value=this.value'";
/**this.value=this.value.replace(/[^\d]/g,'')
 * 处理分页
 */

function pagesUtils(pages,method) {
    //总数据
    c = method;
    var count = pages.totalCount;
    $("#totalCount").html("共有数据："+count+" 条");
    //分页数据
    totalPage = pages.totalPage;
    var p = '';
    //一直显示第一页,当是第一页的时候无法点击上一页按钮
    if (pages.pageNumber == 1) {
        p += '<a href="javascript:;" class="layui-laypage-prev layui-disabled" data-page="0"> <i class="layui-icon"></i></a>';
        p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>1</em> </span>';
    } else {
        p += '<a href="javascript:;" class="layui-laypage-prev" onclick="'+method+'('+(pages.pageNumber-1)+')" data-page="0"> <i class="layui-icon"></i></a>';
        p += '<a href="javascript:;" onclick="'+method+'(1)" data-page="1">1</a>'
    }
    //当总页码小于6 的时候,不处理<1,2,3,4,5,6>
    if (totalPage <= 6) {
        for(var i=2;i<totalPage;i++) {
            if (i == pages.pageNumber) {
                p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>'+i+'</em> </span>';
            } else {
                p += '<a href="javascript:;" onclick="'+method+'('+i+')" data-page="'+i+'">'+i+'</a>';
            }
        }
    } else {
        //头,默认显示<1,2,3>
        if (pages.pageNumber < 4) {
            for(var i=2;i<=3;i++) {
                if (i == pages.pageNumber) {
                    p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>'+i+'</em> </span>';
                } else {
                    p += '<a href="javascript:;" onclick="'+method+'('+i+')" data-page="'+i+'">'+i+'</a>';
                }
            }
            //当点击第三页的时候,显示第四页的按钮
            if(pages.pageNumber == 3) {
                p += '<a href="javascript:;" onclick="'+method+'('+(pages.pageNumber+1)+')" data-page="'+(pages.pageNumber+1)+'">'+(pages.pageNumber+1)+'</a>';
            }
        } else if(pages.pageNumber < totalPage-2){
            //点击的页码大于4时,显示省略号,并且要小于总页码-2
            //...
            p += '<span class="layui-laypage-spr">…</span>';
            //中间
            for(var i=pages.pageNumber-1;i<=pages.pageNumber+1;i++) {
                if (i == pages.pageNumber) {
                    p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>'+i+'</em> </span>';
                } else {
                    p += '<a href="javascript:;" onclick="'+method+'('+i+')" data-page="'+i+'">'+i+'</a>';
                }
            }
        }
        //当点击的页码大于倒数第二页时,显示省略号和后四页<1...7,8,9,10>
        if(pages.pageNumber >= totalPage-2) {
            //...
            p += '<span class="layui-laypage-spr">…</span>';
            //尾
            if(pages.pageNumber == totalPage-2) {
                p += '<a href="javascript:;" onclick="'+method+'('+(pages.pageNumber-1)+')" data-page="'+(pages.pageNumber-1)+'">'+(pages.pageNumber-1)+'</a>';
            }
            for(var i = totalPage-2;i<totalPage;i++) {
                if (i == pages.pageNumber) {
                    p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>'+i+'</em> </span>';
                } else {
                    p += '<a href="javascript:;" onclick="'+method+'('+i+')" data-page="'+i+'">'+i+'</a>';
                }
            }
        } else {
            p += '<span class="layui-laypage-spr">…</span>';
        }

    }
    //一直显示最后一页
    if (totalPage == 1) {
        p += '<a href="javascript:;" class="layui-laypage-next layui-disabled" data-page="2"><i class="layui-icon"></i></a>';
    } else if(pages.pageNumber != totalPage){
        p += '<a href="javascript:;" onclick="'+method+'('+totalPage+')" data-page="'+totalPage+'">'+totalPage+'</a>';
        p += '<a href="javascript:;" class="layui-laypage-next" onclick="'+method+'('+(pages.pageNumber+1)+')" data-page="2"><i class="layui-icon"></i></a>';
    } else {
        p += '<span class="layui-laypage-curr"><em class="layui-laypage-em"></em><em>'+totalPage+'</em> </span>';
        p += '<a href="javascript:;" class="layui-laypage-next layui-disabled" data-page="2"><i class="layui-icon"></i></a>';
    }
    p +='<span class="layui-laypage-skip">到第' +
        '<input type="text" id="to" min="1" value="" class="layui-input" '+to+'>' +
        '<input type="hidden" name="b" id="b" onclick="'+method+'(this.value)">'+
        '页<button type="button" class="layui-laypage-btn" onclick="toPage()">确定</button></span>';

    $("#page").html(p);
}

function toPage() {
    if($("#b").val() > totalPage) {
        $("#b").val(totalPage);
    }
    $("#b").trigger("click");
}

