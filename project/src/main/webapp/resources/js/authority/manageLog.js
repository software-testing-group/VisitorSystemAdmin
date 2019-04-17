/**
 * @author wyp
 */

var pageNum=0;
var sumLogs=0;

$(function(){
	var page = $("#page").text();
	loadPage(page);  //加载页面
})
$("#next").click(function(){
    var page = $("#page").text();
    page = page - 0 + 1;
    if (page > pageNum) {
        alert("已是最后一页");
    } else {
        $("#page").text(page);
        loadPage(page);
    }
});

$("#prev").click(function(){
    var page = $("#page").text();
    page = page - 1;
    console.log(page);
    if (page < 1) {
        alert("已是第一页");
    } else {
        $("#page").text(page);
        loadPage(page);
    }
});
$(".close-btn").click(function(){
	$("#start").val("");
	$("#end").val("");
	$("#username").val("");
	$("#content").val("");
	$("#page").text(1);
	loadPage(1);
});

function getSumLogs()
{
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var start = $("#start").val();
    var end = $("#end").val();
    var username = $("#username").val();
    var content = $("#content").val();

    $.ajax({
        url:"/authority/getSumLogs",
        type:"POST",
        async:false,
        data:{username:username,content:content,start:start,end:end},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){

            sumLogs=data;
            $("#sumLogs").text("每页展示10条，共"+sumLogs+"条信息");
            pageNum = Math.ceil(sumLogs/10);
            $("#pageNumber").text(pageNum);
            console.log(pageNum);

        },error:function(){
            alert("数据加载错误");
        }

    });
}


function loadPage(page)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");

    $("#page").text(page);
    getSumLogs();

    if(sumLogs==0)
    {
        $("#content-table tbody").html("");
        $("#pageNumber").text(1);
    }

	var start = $("#start").val();
	var end = $("#end").val();
	var username = $("#username").val();
	var content = $("#content").val();
	
	$.ajax({
		url:"/authority/getAdminLogList",
		type:"POST",
		data:{page:page,username:username,content:content,start:start,end:end},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data.length == 0)
			{
                alert("不存在满足条件的结果");
			}else
			{
				$("#content-table tbody").html("");
				//放入数据
				
				for(var i in data)
				{
					var log = data[i];
					
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + log.adminUsername + '</td>'
							+   '<td>' + log.createTime + '</td>'
							+   '<td>' + log.ip + '</td>'
							+   '<td>' + log.description + '</td>'
							+   '</tr>'
					);
				}	
			}
		},error:function(){
			alert("数据加载错误");
		}
		
	});
}
/*
function filter()
{
	$("#page").text("1");
	var page = $("#page").text();
	loadPage(page);  //加载页面
}
*/
//导出
function exportGraphCSV()
{
    if (!!window.ActiveXObject || "ActiveXObject" in window){
    	alert("IE9及以下浏览器不支持，请使用Chrome或Firefox浏览器");
    	return false;
    }
	
	var str = "用户名,上次登录时间,上次登录ip,操作描述\n";
	
	$("#content-table tr").each(function(){
		$(this).children("td").each(function(i){
			
			if(i < 4)
				str += $(this).text() + ",";
		});
		str = str.substring(0,str.length - 1);
		str += '\n';
	});
	
	//判断浏览器
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    if (userAgent.indexOf("MSIE") > -1) {
        str = 'sep=,\r\n' + str;
    } //判断是否IE浏览器
    else
    {
    	str =  encodeURIComponent(str);
    }
    
    try{
	    var downloadLink = document.createElement("a");
	    var uri = 'data:text/csv;charset=utf-8,\uFEFF' + str;
		downloadLink.href = uri; 
		downloadLink.download = "export.csv";
		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
    }catch(e){
    	alert("文件太大，下载失败");
    }
}