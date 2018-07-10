/**
 * layer中提示框图标描述
 * 1.√
 * 2.×
 * 3.?
 * 4.锁
 * 5.哭脸
 * 6.笑脸
 * 7以上.感叹号
 * @type {string}
 */


var URL = "http://localhost:8038/";
//var URL = "http://47.98.60.240:8081/";
var USER =  getCookie("USER")==""?null:JSON.parse( getCookie("USER"));
var MENU = getCookie("MENU")==""?null:JSON.parse( getCookie("MENU"));

//设置cookie
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    if (expiredays != -1) {
        exdate.setTime(Number(exdate) + expiredays);
    } else {
        exdate.setTime(expiredays);
    }
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}
//获取cookie
function getCookie(c_name) {
    if(document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");//获取字符串的起点
        if(c_start != -1) {
            c_start = c_start + c_name.length + 1;//获取值的起点
            c_end = document.cookie.indexOf(";", c_start);//获取结尾处
            if(c_end == -1) c_end = document.cookie.length;//如果是最后一个，结尾就是cookie字符串的结尾
            return  unescape(document.cookie.substring(c_start, c_end));//截取字符串返回
        }
    }
    return "";
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}

/**
 * 展开左边栏
 */
function showMenu() {
    if($('.left-nav').css('left')=='0px'){
        $('.left-nav').animate({left: '-221px'}, 100);
        $('.page-content').animate({left: '0px'}, 100);
        $('.page-content-bg').hide();
    }else{
        $('.left-nav').animate({left: '0px'}, 100);
        $('.page-content').animate({left: '221px'}, 100);
        if($(window).width()<768){
            $('.page-content-bg').show();
        }
    }
}
/**
 * 获取地址栏参数
 * @returns {Object}
 */
function getRequest() {
    var url = window.location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {

            theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);

        }
    }
    return theRequest;
}

/**************************************时间格式化处理************************************/
function dateFtt(fmt,date)
{ //author: meizz
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}


function createTime(v) {
    var date = new Date();
    date.setTime(v.time);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ("0" + d) : d;
    var h = date.getHours();
    h = h < 10 ? ("0" + h) : h;
    var M = date.getMinutes();
    M = M < 10 ? ("0" + M) : M;
    var str = y + "-" + m + "-" + d + " " + h + ":" + M;
    return str;
}
