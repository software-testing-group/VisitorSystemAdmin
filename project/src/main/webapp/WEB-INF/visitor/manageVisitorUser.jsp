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
        <span class="table-head-left">用户管理</span>
        <span class="table-head-right">搜索与筛选</span>
      </div>
      <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">输入身份证</span>
          
          <input id="search" type="text" class="input-box" placeholder="请输入...." />
          <button onclick="searchVisitorUser()">搜索</button>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">选择框标题</span>
          <select id="judge"  class="filter-selector" onchange="filterVisitorUser(this.value,1)">
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
          <th>姓名</th>
          <th>注册时间</th>
          <th>主身份证</th>
          <th>是否黑名单</th>
          <th>操作</th>
          <th onclick="downloadVisitorUserExcelToDisk()" class="export-wrapper">导出</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
 <!--         <span>共
            <span>8</span>条信息</span>-->
          <span id="sumUser">每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons" onclick="previouspage()">chevron_left</i>
          </span>
          <span id="page">1</span>/
		  <span id="pageNumber">1</span>
          <span id="next">
            <i class="material-icons" onclick="nextpage()">chevron_right</i>
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
						用户信息
					</h4>
				</div>
				<div class="modal-body" >
					<div class="item-wrapper">
						<span class="item-title">微信openId</span>
						<span class="item-content" id="modalOpenId"></span>
					</div>
					<div class="item-wrapper">
						<span class="item-title">注册时间</span>
						<span class="item-content" id="modalCreateTime"></span>
					</div>
					<div class="item-wrapper">
						<div class="item-title item-title-lines">身份认证</div>
						<div class="item-content-lines">
							<div>
								<span class="item-title">姓名</span>
								<span class="item-content" id="modalFullName"></span>
							</div>
							<div>
								<span class="item-title">证件类型</span>
								<span class="item-content" id="modalCardType"></span>
							</div>
							<div id="modalAllBindingIdentity">
								<span class="item-title">证件号</span>
								<span class="item-content" id="modalMainIdentity"></span>
							</div>
						</div>
					</div>
					<div class="item-wrapper">
						<div class="item-title item-title-lines">绑定证件</div>
						<div class="item-content-lines">
							<table class="table table-bordered" id="binding-table">
								<thead>
									<th>姓名</th>
									<th>证件</th>
									<th>证件类型</th>
								</thead>
								<tbody>
									<tr>

									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="item-wrapper">
						<div class="item-title item-title-lines">预约记录</div>
						<div class="item-content-lines">
							<table class="table table-bordered" id="reservation-table">
								<thead>
									<th>创建时间</th>
									<th>预约时段</th>
									<th>状态</th>
								</thead>
								<tbody>
									<tr>

									</tr>
								</tbody>
							</table>
						</div>
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
		<!-- /.modal -->
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script type="text/javascript">
var sumVisitor=0
var pageNum=0
$(function(){
	getSumVisitorUser();
	var page=$("#page").text();
	loadPage(page);  //加载页面
})
function getSumVisitorUser(){   // 获得用户总数
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/getSumVisitorUser",
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){		
			sumVisitor=data;
            $("#sumUser").text("每页展示10条，共"+sumVisitor+"条信息");
            pageNum = Math.ceil(sumVisitor/10);
            $("#pageNumber").text(pageNum);
		},error:function(){
			alert("数据加载错误");
		}
		
	});
}

function getSumBannedVisitorUser(){   // 获得黑名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumBannedVisitorUser",
        type:"POST",
        data:{},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumVisitor=data;
            $("#sumUser").text("每页展示10条，共"+sumVisitor+"条信息");
            pageNum = Math.ceil(sumVisitor/10);
            $("#pageNumber").text(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}


function getSumNoBannedVisitorUser(){   // 获得白名单用户总数
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/visitor/getSumNoBannedVisitorUser",
        type:"POST",
        data:{},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumVisitor=data;
            $("#sumUser").text("每页展示10条，共"+sumVisitor+"条信息");
            pageNum = Math.ceil(sumVisitor/10);
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
	$.ajax({      // 获得某一页的用户信息
		url:"${pageContext.request.contextPath}/visitor/getAllVisitorUser",
		type:"POST",
		data:{page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			$("#content-table tbody").html("");

			console.log(data);

			//放入数据
			for(var i in data) 
			{
				var visitorUser = data[i];
				//主身份证
				var mainIdentity = "暂无";

				var username = "暂无";

				if(visitorUser.isBanned==1){
					var black="是";
				}
				else
					var black="否";
				
				for(var j in visitorUser.visitorBindingIdentityList)    
				{
					if(visitorUser.visitorBindingIdentityList[j].isMain==1)
						mainIdentity=visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCard;
                        username=visitorUser.visitorBindingIdentityList[j].visitorIdentity.fullName;
				}
				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + username + '</td>'
						+   '<td>' + visitorUser.createTime + '</td>'
						+   '<td>' + mainIdentity + '</td>'
						+   '<td>' + black + '</td>'
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorUserInfo(' + visitorUser.id  + ');">'+'查看' +'</a>'
						+' | ' + '<a href="javascript:defriendVisitorUser(' + visitorUser.id  + ');">' 
						+ '禁用'  +'</a>'
						+' | ' + '<a href="javascript:refriendVisitorUser(' + visitorUser.id  + ');">'
						+ '解禁'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}	
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});
	
}
function filterVisitorUser(value,page){
	if(value=="0"||value=="1"){
        $("#page").text("1");
        sumVisitor=getSumVisitorUser();
		loadPage(1);
	}
	else if(value=="2"){
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
        $("#page").text("1");
        sumVisitor=getSumBannedVisitorUser();
		$.ajax({
			url:"${pageContext.request.contextPath}/visitor/getAllBannedVisitorUser",
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
					var visitorUser = data[i];
                    console.log(visitorUser);
					//主身份证
					var mainIdentity = "暂无";
                    var username = "暂无";
					if(visitorUser.isBanned!=0){
						var black="是";
					}
					else
						var black="否";
					
					for(var j in visitorUser.visitorBindingIdentityList)    
					{
						if(visitorUser.visitorBindingIdentityList[j].isMain==1)
							mainIdentity=visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCard;
                            username=visitorUser.visitorBindingIdentityList[j].visitorIdentity.fullName;
					}
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + username + '</td>'
							+   '<td>' + visitorUser.createTime + '</td>'
							+   '<td>' + mainIdentity + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorUserInfo(' + visitorUser.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorUser(' + visitorUser.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorUser(' + visitorUser.id  + ');">'
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
        sumVisitor=getSumNoBannedVisitorUser();
		$.ajax({
			url:"${pageContext.request.contextPath}/visitor/getAllNoBannedVisitorUser",
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
					var visitorUser = data[i];
					//主身份证
					var mainIdentity = "暂无";
                    var username = "暂无";
					if(visitorUser.isBanned!=0){
						var black="是";
					}
					else
						var black="否";
					
					for(var j in visitorUser.visitorBindingIdentityList)    
					{
						if(visitorUser.visitorBindingIdentityList[j].isMain==1)
							mainIdentity=visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCard;
                            username=visitorUser.visitorBindingIdentityList[j].visitorIdentity.fullName;
					}
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + username + '</td>'
							+   '<td>' + visitorUser.createTime + '</td>'
							+   '<td>' + mainIdentity + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorUserInfo(' + visitorUser.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorUser(' + visitorUser.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorUser(' + visitorUser.id  + ');">'
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
function searchVisitorUser()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var identity=$("#search").val();
	var page=1;
    pageNum = 1;
    sumVisitor = 1;
    $("#sumUser").text("每页展示10条，共"+sumVisitor+"条信息");
    $("#pageNumber").text(pageNum);
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/searchVisitorUser",
		type:"POST",
		data:{identity:identity,page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data!=''){
				$("#page").text(page);
				$("#content-table tbody").html("");
				//放入数据
				for(var i in data) 
				{
					var visitorUser = data[i];
					//主身份证
					var mainIdentity = "暂无";
					var username = "暂无";
					if(visitorUser.isBanned==0){
						var black="否";
					}
					else
						var black="是";
					
					for(var j in visitorUser.visitorBindingIdentityList)    
					{
						if(visitorUser.visitorBindingIdentityList[j].isMain==1)
                            username=visitorUser.visitorBindingIdentityList[j].visitorIdentity.fullName;
							mainIdentity=visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCard;

					}
					$("#content-table tbody").append('<tr role="row">'
							+   '<td>' + username+ '</td>'
							+   '<td>' + visitorUser.createTime + '</td>'
							+   '<td>' + mainIdentity + '</td>'
							+   '<td>' + black + '</td>'
							+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getVisitorUserInfo(' + visitorUser.id  + ');">'+'查看' +'</a>'
							+' | ' + '<a href="javascript:defriendVisitorUser(' + visitorUser.id  + ');">' 
							+ '禁用'  +'</a>'
							+' | ' + '<a href="javascript:refriendVisitorUser(' + visitorUser.id  + ');">'
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
function getVisitorUserInfo(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/visitor/VisitorUserDetails",
		type:"POST",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){

            $("#binding-table tbody").html("");
            $("#reservation-table tbody").html("");

            var visitorUser = data;

			console.log(data);

			$("#modalOpenId").text(visitorUser.openId);
			$("#modalCreateTime").text(visitorUser.createTime);
			if(visitorUser.isBanned==0)
			{
			    $("#modalIsBanned").text("否");
                $("#showReason").addClass("hidden");
                $("#whyIsBanned").text("");
			}
			else
			{
                $("#showReason").removeClass("hidden")
			    $("#modalIsBanned").text("是");
                if(visitorUser.isBanned==1)
                    $("#whyIsBanned").text("累计取消三次");
                else if(visitorUser.isBanned==2)
                    $("#whyIsBanned").text("累计预约了4次");
                else if(visitorUser.isBanned==3)
                    $("#whyIsBanned").text("连续3次未签到");
                else if(visitorUser.isBanned==4)
                    $("#whyIsBanned").text("被管理员封禁");

			}
			if(visitorUser.visitorBindingIdentityList!=''){
			for(var j in visitorUser.visitorBindingIdentityList)    
			{
				
				if(visitorUser.visitorBindingIdentityList[j].isMain==1){
					$("#modalFullName").text(visitorUser.visitorBindingIdentityList[j].visitorIdentity.fullName);
					var cardType ="未知";
					if(visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCardType==0){
						cardType="中国居民身份证";
					}
					else if(visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCardType==1){
						cardType="中国护照";
					}
					else if(visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCardType==2){
						cardType="台胞证";
					}
					$("#modalCardType").text(cardType);
					$("#modalMainIdentity").text(visitorUser.visitorBindingIdentityList[j].visitorIdentity.identityCard);
				}

			}
			for(var k in visitorUser.visitorBindingIdentityList){
				var bindingCardType ="未知";
				if(visitorUser.visitorBindingIdentityList[k].visitorIdentity.identityCardType==0){
					bindingCardType="中国居民身份证";
				}
				else if(visitorUser.visitorBindingIdentityList[k].visitorIdentity.identityCardType==1){
					bindingCardType="中国护照";
				}
				else if(visitorUser.visitorBindingIdentityList[k].visitorIdentity.identityCardType==2){
					bindingCardType="台胞证";
				}
				//$("#binding-table tbody").html("");       // 这里清空了上一轮循环保存的信息
				$("#binding-table tbody").append('<tr >'
						+   '<td>' + visitorUser.visitorBindingIdentityList[k].visitorIdentity.fullName + '</td>'
						+   '<td>' + visitorUser.visitorBindingIdentityList[k].visitorIdentity.identityCard + '</td>'
						+   '<td>' + bindingCardType + '</td>'
						+   '</tr>'
				);
				}
			}
			else{
				$("#modalFullName").text(" ");
				$("#modalCardType").text(" ");
				$("#modalMainIdentity").text(" ");
				$("#binding-table tbody").html("");
				$("#binding-table tbody").append('<tr >'
							+   '<td>' +   '</td>'
							+   '<td>' +   '</td>'
							+   '<td>' +  '</td>'
							+   '</tr>'
							);
			}
			if(visitorUser.visitorReservationList!=''){
			for(var l in visitorUser.visitorReservationList){
				var status="";
				if(visitorUser.visitorReservationList[l].isDeleted==1){
					status="已取消";
				}
				else if(visitorUser.visitorReservationList[l].isFinished==0){
					status="未完成";
				}
				else if(visitorUser.visitorReservationList[l].isFinished==1){
					status="已完成";
				}
				else if(visitorUser.visitorReservationList[l].isFinished==2){
					status="被取消";
				}
				else if(visitorUser.visitorReservationList[l].isFinished==3){
					status="未到达";
				}
				//$("#reservation-table tbody").html("");           // 这里清空了上次循环保存的结果
				$("#reservation-table tbody").append('<tr >'
						+   '<td>' + visitorUser.visitorReservationList[l].createTime + '</td>'
						+   '<td>' + visitorUser.visitorReservationList[l].visitorReservationTime.date+" "+visitorUser.visitorReservationList[l].visitorReservationTime.timeStart+'-'+ visitorUser.visitorReservationList[l].visitorReservationTime.timeEnd+ '</td>'
						+   '<td>' + status + '</td>'
						+   '</tr>'
				);
			}
			}
			else{
				$("#reservation-table tbody").html("");
			$("#reservation-table tbody").append('<tr >'
					+   '<td>' + '</td>'
					+   '<td>' + '</td>'
					+   '<td>' + '</td>'
					+   '</tr>'
			);
				
			}
			$("#twoButton div").html("");
			$("#twoButton div").append('<button type="button"  class="btn btn-danger" data-dismiss="modal" onclick="defriendVisitorUser(' + visitorUser.id  + ');">'
					+'移入黑名单'
					+'</button>'
					+'<button type="button"  class="btn btn-primary"  data-dismiss="modal" onclick="refriendVisitorUser(' + visitorUser.id  + ');">'
					+	'移出黑名单'
					+'</button>'
					);
		},
		error:function(){
			alert("数据加载错误");
			$("#modalOpenId").text("");
			$("#modalCreateTime").text("");
			$("#modalIsBanned").text("");
			$("#modalFullName").text("");
			$("#modalCardType").text("");
			$("#modalMainIdentity").text("");
			$("#binding-table tbody").html("");
			$("#binding-table tbody").append('<tr >'
						+   '<td>' +   '</td>'
						+   '<td>' +   '</td>'
						+   '<td>' +  '</td>'
						+   '</tr>'
						);

			$("#reservation-table tbody").html("");
			$("#reservation-table tbody").append('<tr >'
						+   '<td>' + '</td>'
						+   '<td>' + '</td>'
						+   '<td>' + '</td>'
						+   '</tr>'
				);
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

function defriendVisitorUser(id) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$
				.ajax({
					url : "${pageContext.request.contextPath}/visitor/defriendVisitorUser",
					type : "POST",
					data : {
						id : id
					},
					beforeSend : function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(data) {
						if (data) {
							alert("操作成功");
							loadPage($("#page").text());
						} else
							alert("操作失败");
					},
					error : function() {
						alert("禁用错误");
					}
				});

	}
	function refriendVisitorUser(id) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$
				.ajax({
					url : "${pageContext.request.contextPath}/visitor/refriendVisitorUser",
					type : "POST",
					data : {
						id : id
					},
					beforeSend : function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(data) {
						if (data) {
							alert("操作成功");
							loadPage($("#page").text());
						} else
							alert("操作失败");
					},
					error : function() {
						alert("禁用错误");
					}
				});

	}
	function previouspage() {
		var page = $("#page").text();
        var type = $("#judge").find("option:selected").get('0').value;
		page = page - 1;
		console.log(page);
		if (page < 1) {
			alert("已是第一页");
		} else {

            if(type==0||type==1) {
                loadPage(page);
                $("#page").text(page);
            }
            else if(type==2)
            {
                filterVisitorUser(2,page);
                $("#page").text(page);
            }
            else if(type==3)
            {
                filterVisitorUser(3,page);
                $("#page").text(page);
            }

		}

	}
	function nextpage() {
		var page = $("#page").text();
        var type = $("#judge").find("option:selected").get('0').value;
        console.log("下拉框类型： "+type);
		page = page - 0 + 1;
		if (page > pageNum) {
			alert("已是最后一页");
		} else {
		    if(type==0||type==1) {
                loadPage(page);
                $("#page").text(page);
            }
            else if(type==2)
			{
                filterVisitorUser(2,page);
                $("#page").text(page);
			}
			else if(type==3)
			{
                filterVisitorUser(3,page);
                $("#page").text(page);
			}
		}

	}
	function downloadVisitorUserExcelToDisk() {
		var token = $("meta[name='_csrf']").attr("content");
		var param = $("meta[name='_csrf_param']").attr("content");
		var form = $("<form>");
		form.attr("style", "display:none");
		form.attr("target", "");
		form.attr("method", "post");
		form.attr("action","${pageContext.request.contextPath}/visitor/downloadExcelToDisk");
		var input1 = $("<input>");
		input1.attr("type", "hidden");
		input1.attr("name", "DownloadType");
		input1.attr("value", "VisitorUser");
		var input2 = $("<input>");
		input2.attr("type", "hidden");
		input2.attr("name", "page");
		input2.attr("value", $("#page").text());
		var input3 = $("<input>");
		input3.attr("type", "hidden");
		input3.attr("name", param);
		input3.attr("value", token);

		$("body").append(form);
		form.append(input3);
		form.append(input1);
		form.append(input2);
		form.submit();
		form.remove();
	}
</script>
</body>
</html>