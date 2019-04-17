<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="_csrf" content="${_csrf.token}"/>  
  <meta name="_csrf_header" content="${_csrf.headerName}"/>  
    <meta name="_csrf_param" content="${_csrf.parameterName}"/>
<title>后台管理系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/table.css">
</head>
<body>
	<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">证件管理</span>
        <span class="table-head-right">搜索与筛选</span>
      </div>
      <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">输入证件号</span>
          <input type="text" id="search" class="input-box" placeholder="请输入...." />
          <button onclick="searchVisitorIdentity()">搜索</button>
        </div>
        <div class="filter-item-wrapper">
           <span class="filter-title">输入姓名</span>
           <input type="text" id="searchByName" class="input-box" placeholder="请输入...." />
           <button onclick="searchVisitorIdentityByName(1)">搜索</button>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">选择框标题</span>
          <select class="filter-selector" onchange="filterVisitorIdentity(this.value,1)" id="selectbar">
            <option value="0" selected>请选择.....</option>
            <option value="1" >所有人</option>
            <option value="2" >黑名单</option>
            <option value="3" >白名单</option>
          </select>
        </div>
  <!--       <div class="filter-item-wrapper">
          <span class="filter-title">时间</span>
          <input type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <span>至</span>
          <input type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
        </div>--> 
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>全名</th>
          <th>证件号</th>
          <th>证件类型</th>
          <th>是否黑名单</th>
          <th>操作</th>
          <th onclick="downloadVisitorIdentityExcelToDisk()" class="export-wrapper">导出</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
 <!--         <span>共
            <span>8</span>条信息</span>-->
          <span id="sumIdentity">每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i onclick="previouspage()" class="material-icons">chevron_left</i>
          </span>
          <span id="page">1</span>/
          <span id="pageNumber">1</span>
          <span id="next">
            <i onclick="nextpage()" class="material-icons">chevron_right</i>
          </span>
        </div>
      </div>
    </div>

  </div>
   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						证件信息
					</h4>
				</div>
				<div class="modal-body" >
					<div class="item-wrapper">
						<span class="item-title">全名</span>
						<span class="item-content" id="modalFullName"></span>
					</div>
					<div class="item-wrapper">
						<span class="item-title">创建时间</span>
						<span class="item-content" id="modalCreateTime"></span>
					</div>
					<div class="item-wrapper">
						<span class="item-title">证件号</span>
						<span class="item-content" id="modalIdentityCard"></span>
					</div>
					<div class="item-wrapper">
						<span class="item-title">证件类型</span>
						<span class="item-content" id="modalIdentityCardType"></span>
					</div>
					<div class="item-wrapper">
						<span class="item-title">是否黑名单</span>
						<span class="item-content" id="modalIsBanned"> </span>
					</div>
                    <div class="hidden" id="showReason">
                        <div class="item-wrapper" >
                            <span class="item-title">拉黑原因</span>
                            <span class="item-content" id="whyIsBanned"> </span>
                        </div>
                    </div>
				</div>
				<div class="modal-footer" id="twoButton">
				<div>
					<button type="button"  class="btn btn-danger" data-dismiss="modal">
						移入黑名单
					</button>
					<button type="button"  class="btn btn-primary">
						移出黑名单
					</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script type="text/javascript">
var sumIdentity=0
var pageNum=0
$(function(){
	getSumIdentity();
	var page=$("#page").text();
	loadPage(page);  //加载页面
})
function getSumIdentity(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/getSumIdentity",
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){		
			sumIdentity=data;
            $("#sumIdentity").text("每页展示10条，共"+sumIdentity+"条信息");
            pageNum = Math.ceil(sumIdentity/10);
            $("#pageNumber").text(pageNum);
		},error:function(){
			alert("数据加载错误");
		}
		
	});
}

function getSumBannedIdentity(){   // 获得黑名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumBannedIdentity",
        type:"POST",
        data:{},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumIdentity=data;
            $("#sumIdentity").text("每页展示10条，共"+sumIdentity+"条信息");
            pageNum = Math.ceil(sumIdentity/10);
            $("#pageNumber").text(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}


function getSumNoBannedIdentity(){   // 获得白名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumNoBannedIdentity",
        type:"POST",
        data:{},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumIdentity=data;
            $("#sumIdentity").text("每页展示10条，共"+sumIdentity+"条信息");
            pageNum = Math.ceil(sumIdentity/10);
            $("#pageNumber").text(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}

function loadPage(page)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/getAllVisitorIdentity",
		type:"POST",
		data:{page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			$("#content-table tbody").html("");
			//放入数据
			for(var i in data) 
			{
				var visitorIdentity= data[i];
				//主身份证
				var cardType = "未知";
				if(visitorIdentity.identityCardType==0){
					cardType="中国居民身份证";
				}
				else if(visitorIdentity.identityCardType==1){
					cardType="中国护照";
				}
				else if(visitorIdentity.identityCardType==2){
					cardType="台胞证";
				}
				if(visitorIdentity.isBanned!=0){
					var black="是";
				}
				else
					var black="否";
					
				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + visitorIdentity.fullName + '</td>'
						+   '<td>' + visitorIdentity.identityCard + '</td>'
						+   '<td>' + cardType + '</td>'
						+   '<td>' + black + '</td>'
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorIdentityInfo(' + visitorIdentity.id  + ');">'+'查看' +'</a>'
						+' | ' + '<a href="javascript:defriendVisitorIdentity(' + visitorIdentity.id  + ');">' 
						+ '禁用'  +'</a>'
						+' | ' + '<a href="javascript:refriendVisitorIdentity(' + visitorIdentity.id  + ');">'
						+ '解禁'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}	
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});
	
}

function getSumByIdentity(){   // 获得白名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var identity=$("#search").val();
    var value=$('#selectbar option:selected').val();
    var isBanned=2;
    if(value=="2"){
        isBanned=1;
    }
    if(value=="3"){
        isBanned=0;
    }
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumByIdentity",
        type:"POST",
        data:{identity:identity,isBanned:isBanned},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            console.log("执行到这里了");
            sumIdentity=data;
            $("#sumIdentity").text("每页展示10条，共"+sumIdentity+"条信息");
            pageNum = Math.ceil(sumIdentity/10);
            $("#pageNumber").text(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}


function searchVisitorIdentity()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
    $("#page").text("1");
    sumIdentity = getSumByIdentity();
    console.log(sumIdentity);
	var identity=$("#search").val();
	var value=$('#selectbar option:selected').val();
	var page=1;
    var isBanned=2;
    if(value=="2"){
        isBanned=1;
    }
    if(value=="3"){
        isBanned=0;
    }
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/searchVisitorIdentity",
		type:"POST",
		data:{identity:identity,page:page,isBanned:isBanned},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data!=''){
				$("#content-table tbody").html("");
				//放入数据
				$("#page").text(page);
				for(var i in data) 
				{
					var visitorIdentity= data[i];
					//主身份证
					var cardType = "未知";
					if(visitorIdentity.identityCardType==0){
						cardType="中国居民身份证";
					}
					else if(visitorIdentity.identityCardType==1){
						cardType="中国护照";
					}
					else if(visitorIdentity.identityCardType==2){
						cardType="台胞证";
					}
					if(visitorIdentity.isBanned!=0){
						var black="是";
					}
					else
						var black="否";
						
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + visitorIdentity.fullName + '</td>'
							+   '<td>' + visitorIdentity.identityCard + '</td>'
							+   '<td>' + cardType + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorIdentityInfo(' + visitorIdentity.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorIdentity(' + visitorIdentity.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorIdentity(' + visitorIdentity.id  + ');">'
							+ '解禁'  +'</a>'+'</td>'
							+   '</tr>'
					);
				}	
			}
			else{
					alert("查无此人");
			}
		},error:function(){
			alert("返回错误");
		}
		
	});
	
}

function getSumByName(){   // 获得白名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var name=$("#searchByName").val();
    var value=$('#selectbar option:selected').val();
    var isBanned=2;
    if(value=="2"){
        isBanned=1;
    }
    if(value=="3"){
        isBanned=0;
    }
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumByName",
        type:"POST",
        data:{name:name,isBanned:isBanned},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            console.log("执行到这里了");
            sumIdentity=data;
            $("#sumIdentity").text("每页展示10条，共"+sumIdentity+"条信息");
            pageNum = Math.ceil(sumIdentity/10);
            $("#pageNumber").text(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}


function searchVisitorIdentityByName(page)
{
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    sumIdentity = getSumByName();
    var name=$("#searchByName").val();
    var value=$('#selectbar option:selected').val();
    //var page=1;
    var isBanned=2;
    if(value=="2"){
        isBanned=1;
    }
    if(value=="3"){
        isBanned=0;
    }
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/searchVisitorIdentityByName",
        type:"POST",
        data:{name:name,page:page,isBanned:isBanned},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            if(data!=''){
                $("#content-table tbody").html("");
                //放入数据
                $("#page").text(page);
                for(var i in data)
                {
                    var visitorIdentity= data[i];
                    //主身份证
                    var cardType = "未知";
                    if(visitorIdentity.identityCardType==0){
                        cardType="中国居民身份证";
                    }
                    else if(visitorIdentity.identityCardType==1){
                        cardType="中国护照";
                    }
                    else if(visitorIdentity.identityCardType==2){
                        cardType="台胞证";
                    }
                    if(visitorIdentity.isBanned!=0){
                        var black="是";
                    }
                    else
                        var black="否";

                    $("#content-table tbody").append('<tr role="row">'
                        +   '<td>' + visitorIdentity.fullName + '</td>'
                        +   '<td>' + visitorIdentity.identityCard + '</td>'
                        +   '<td>' + cardType + '</td>'
                        +   '<td>' + black + '</td>'
                        +   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorIdentityInfo(' + visitorIdentity.id  + ');">'+'查看' +'</a>'
                        +' | ' + '<a href="javascript:defriendVisitorIdentity(' + visitorIdentity.id  + ');">'
                        + '禁用'  +'</a>'
                        +' | ' + '<a href="javascript:refriendVisitorIdentity(' + visitorIdentity.id  + ');">'
                        + '解禁'  +'</a>'+'</td>'
                        +   '</tr>'
                    );
                }
            }
            else{
                alert("查无此人");
            }
        },error:function(){
            alert("返回错误");
        }

    });

}



function filterVisitorIdentity(value,page){
	if(value=="0"||value=="1"){
        $("#page").text("1");
        sumIdentity=getSumIdentity();
		loadPage(1);
	}
	else if(value=="2"){
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
        $("#page").text("1");
        sumIdentity=getSumBannedIdentity();
		$.ajax({
			url:"${pageContext.request.contextPath}/visitor/getAllBannedVisitorIdentity",
			type:"POST",
			data:{page:page},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){
				
				$("#content-table tbody").html("");
				//放入数据
				for(var i in data) 
				{
					var visitorIdentity= data[i];
					//主身份证
					var cardType = "未知";
					if(visitorIdentity.identityCardType==0){
						cardType="中国居民身份证";
					}
					else if(visitorIdentity.identityCardType==1){
						cardType="中国护照";
					}
					else if(visitorIdentity.identityCardType==2){
						cardType="台胞证";
					}
					if(visitorIdentity.isBanned!=0){
						var black="是";
					}
					else
						var black="否";
						
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + visitorIdentity.fullName + '</td>'
							+   '<td>' + visitorIdentity.identityCard + '</td>'
							+   '<td>' + cardType + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorIdentityInfo(' + visitorIdentity.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorIdentity(' + visitorIdentity.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorIdentity(' + visitorIdentity.id  + ');">'
							+ '解禁'  +'</a>'+'</td>'
							+   '</tr>'
					);
				}	
				
			},error:function(){
				alert("数据加载错误");
			}
			
		});
		
	}
	else if(value=="3"){
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
        $("#page").text("1");
        sumIdentity=getSumNoBannedIdentity();
		$.ajax({
			url:"${pageContext.request.contextPath}/visitor/getAllNoBannedVisitorIdentity",
			type:"POST",
			data:{page:page},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){
				
				$("#content-table tbody").html("");
				//放入数据
				for(var i in data) 
				{
					var visitorIdentity= data[i];
					//主身份证
					var cardType = "未知";
					if(visitorIdentity.identityCardType==0){
						cardType="中国居民身份证";
					}
					else if(visitorIdentity.identityCardType==1){
						cardType="中国护照";
					}
					else if(visitorIdentity.identityCardType==2){
						cardType="台胞证";
					}
					if(visitorIdentity.isBanned!=0){
						var black="是";
					}
					else
						var black="否";
						
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + visitorIdentity.fullName + '</td>'
							+   '<td>' + visitorIdentity.identityCard + '</td>'
							+   '<td>' + cardType + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorIdentityInfo(' + visitorIdentity.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorIdentity(' + visitorIdentity.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorIdentity(' + visitorIdentity.id  + ');">'
							+ '解禁'  +'</a>'+'</td>'
							+   '</tr>'
					);
				}	
			},error:function(){
				alert("数据加载错误");
			}
			
		});
		
	}
}
function defriendVisitorIdentity(id)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/defriendVisitorIdentity",
		type:"POST",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("操作成功");
				loadPage($("#page").text());
			}
			else
				alert("操作失败");
		},
		error:function(){
			alert("禁用错误");
		}
	});
	
}
function refriendVisitorIdentity(id)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/refriendVisitorIdentity",
		type:"POST",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("操作成功");
				loadPage($("#page").text());
			}
			else
				alert("操作失败");
		},
		error:function(){
			alert("禁用错误");
		}
	});
	
}
function previouspage(){
	var page=$("#page").text();
	var type=$('#selectbar option:selected').val();
    var name=$("#searchByName").val();
	page=page-1;
	console.log(page);
	if(page<1){
		alert("已是第一页");
	}
	else{

        if(name!="")
        {
            searchVisitorIdentityByName(page)
        }

        else if(type==0||type==1) {
            loadPage(page);
            $("#page").text(page);
        }
        else if(type==2)
        {
            filterVisitorIdentity(2,page);
            $("#page").text(page);
        }
        else if(type==3)
        {
            filterVisitorIdentity(3,page);
            $("#page").text(page);
        }


	}
	
}
function nextpage(){
	var page=$("#page").text();
    var type = $("#selectbar").find("option:selected").get('0').value;
	page=page-0+1;
    var name=$("#searchByName").val();
    console.log(page);
	if(page>pageNum){   // sumIdentity ==2 ,然后sumIdentity的值再也不被更新
		alert("已是最后一页");
	}
	else
	{
        if(name!="")
        {
            searchVisitorIdentityByName(page)
        }


	    else if(type==0||type==1) {
            //sumIdentity=getSumIdentity();
            loadPage(page);
            $("#page").text(page);
        }
        else if(type==2)
        {   // 点下一页之后，这边才更新sumIdentity，应该在选择下拉框时更新sumIdentity
            // sumIdentity=getSumBannedIdentity();
            filterVisitorIdentity(2,page);
            $("#page").text(page);
        }
        else if(type==3)
        {
            // sumIdentity=getSumNoBannedIdentity();
            filterVisitorIdentity(3,page);
            $("#page").text(page);
        }

	}
	
}
function  downloadVisitorIdentityExcelToDisk(){
	var token = $("meta[name='_csrf']").attr("content");  
	var param = $("meta[name='_csrf_param']").attr("content");
	var form = $("<form>");
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","${pageContext.request.contextPath}/visitor/downloadExcelToDisk");
	var input1 = $("<input>");
	input1.attr("type","hidden");
	input1.attr("name","DownloadType");
	input1.attr("value","VisitorIdentity");
	var input2 = $("<input>");
	input2.attr("type","hidden");
	input2.attr("name","page");
	input2.attr("value",$("#page").text());
	var input3 = $("<input>");
	input3.attr("type","hidden");
	input3.attr("name",param);
	input3.attr("value",token);

	$("body").append(form);
	form.append(input3);
	form.append(input1);
	form.append(input2);
	form.submit();
	form.remove();
	}
function getVisitorIdentityInfo(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/getVisitorIdentityById",
		type:"POST",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			var visitorIdentity = data;
            console.log("-------------------");
            console.log(visitorIdentity);
			$("#modalFullName").text(visitorIdentity.fullName);
			$("#modalIdentityCard").text(visitorIdentity.identityCard);
			$("#modalCreateTime").text(visitorIdentity.createTime);
			if(visitorIdentity.isBanned==0){
			    $("#modalIsBanned").text("否");
                $("#showReason").addClass("hidden");
                $("#whyIsBanned").text("");
			}
			else
			    {

                    $("#showReason").removeClass("hidden")
                    $("#modalIsBanned").text("是");
                    if(visitorIdentity.isBanned==1)
                        $("#whyIsBanned").text("累计取消三次");
                    else if(visitorIdentity.isBanned==2)
                        $("#whyIsBanned").text("累计预约了4次");
                    else if(visitorIdentity.isBanned==3)
                        $("#whyIsBanned").text("连续3次未签到");
                    else if(visitorIdentity.isBanned==4)
                        $("#whyIsBanned").text("被管理员封禁");

				}
			var cardType = "未知";
			if(visitorIdentity.identityCardType==0){
				cardType="中国居民身份证";
				}
			else if(visitorIdentity.identityCardType==1){
				cardType="中国护照";
				}
			else if(visitorIdentity.identityCardType==2){
				cardType="台胞证";
				}
			$("#modalIdentityCardType").text(cardType);
			$("#twoButton").html("");
			$("#twoButton").append('<button type="button"  class="btn btn-danger" data-dismiss="modal" onclick="defriendVisitorIdentity(' + id  + ');">'
					+'移入黑名单'
					+'</button>'
					+'<button type="button"  class="btn btn-primary"  data-dismiss="modal" onclick="refriendVisitorIdentity(' + id  + ');">'
					+	'移出黑名单'
					+'</button>'
					);
		},
		error:function(){
			alert("数据加载错误");
			$("#modalFullName").text(" ");
			$("#modalIdentityCard").text(" ");
			$("#modalIsBanned").text(" ");
			$("#modalCardType").text(" ");
			$("#twoButton").html("");
			$("#twoButton").append('<button type="button"  class="btn btn-danger" data-dismiss="modal" >'
			+'移入黑名单'
			+'</button>'
			+'<button type="button"  class="btn btn-primary" >'
			+	'移出黑名单'
			+'</button>'
					);
			}
		
			
	});	
}
</script>
</body>
</html>