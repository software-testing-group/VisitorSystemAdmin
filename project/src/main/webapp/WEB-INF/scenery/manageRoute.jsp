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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fontsgoogleapisicon.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navigation/manageRoute.css">
</head>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">路线管理</span>
       <!-- <span class="table-head-right">搜索与筛选</span> -->
      </div>
      <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">输入标题</span>
          <input type="text" class="input-box" placeholder="请输入...." />
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">选择框标题</span>
          <select class="filter-selector">
            <option value="0" selected>请选择.....</option>
          </select>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">时间</span>
          <input type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <span>至</span>
          <input type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
        </div>
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>路线名称</th>
          <th>行走路线</th>
          <th>操作</th>
          <th data-toggle="modal" data-target="#myModal" class="export-wrapper" onclick="clearModal()">新建</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
            <span>每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons" onclick="previouspage()">chevron_left</i>
          </span>
          <span id="page">1</span>
          <span id="next">
            <i class="material-icons" onclick="nextpage()">chevron_right</i>
          </span>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="demo-content">
			<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="demo-title" id="myModalLabel">景点详情</h4>
            </div>
            <span class="new-form-span">路线名称：</span>
        	<input id="routeName" name="routeName"/><br>
      	    <span class="new-form-span">路线游览时长：</span>
        	<input id="routeTime" name="routeTime"/><span>小时</span><br>
			<span class="new-form-span">路线特色介绍：</span>
			<textarea id="routeIntro"></textarea>
					<div class="hr1"></div>
					<button id="newitem">新建行走路线</button>
					<div class="hr2"></div>
					<div type="none" class="ulStyle" id="ulStyle"></div>
			<div class="modal-footer" id="twoButton">
				<button type="button" class="quitBtn" data-dismiss="modal">取消</button>
				<button type="button" class="sureBtn" data-dismiss="modal">保存</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script type="text/javascript">

var sumRoute=0;
$(function(){
    getSumRoute();
    var page=$("#page").text();
    loadPage(page);  //加载页面
});
function previouspage(){
    var page=$("#page").text();
    page=page-1;
    if(page<1){
        alert("已是第一页");
    }
    else{
        loadPage(page);
        $("#page").text(page);
    }
}
function nextpage() {
    var page=$("#page").text();
    page=page-0+1;
    if((page-1)*10>=sumRoute){
        alert("已是最后一页");
    }
    else{
        loadPage(page);
        $("#page").text(page);
    }
}
function getSumRoute(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/scenery/getSumOfRoutes",
        type:"GET",
        data:{},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumRoute=data;
        },error:function(){
            alert("数据加载错误");
        }
    });
}
function loadPage(page) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/getRoutes",
		type:"GET",
		data:{page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			$("#content-table tbody").html("");
			//放入数据
			for(var i in data) 
			{
				var route = data[i];
				var attractionList=getRouteText(route,"->");
				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + route.routeName + '</td>'
						+   '<td>' + attractionList + '</td>'
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getRouteInfo(' + route.id  + ');">查看' +'</a>'
						+' | ' + '<a href="javascript:deleteRoute(' + route.id  + ');">'
						+ '删除'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}
		},error:function(){
			alert("数据加载错误");
		}
	});	
}
function getRouteText(route,separator){
    var attractionList="";
    //对景点进行排序
    route.routeBindingAttractionList.sort(
        function(a,b){
            return a.sequence-b.sequence;
        }
    );
    for(var j in route.routeBindingAttractionList){
        var attraction=route.routeBindingAttractionList[j];
        if(attractionList==""){
            attractionList+=attraction.attraction.name;
        }
        else{
            attractionList+=separator;
            attractionList+=attraction.attraction.name;
        }
    }
    return attractionList;
}
function deleteRoute(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/deleteRoute",
		type:"GET",
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
function clearModal(){
	$("#myModalLabel").text("新增路线");
	$("#routeName").val("");
    $("#routeTime").val("");
    $("#specificRouteDiv").remove();
	$("#routeIntro").val("");
    $("#newitem").show();
	$("#twoButton").html("");
	$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
			+'取消'
			+'</button>'
			+'<button type="button" class="sureBtn" onclick="addRoute()" data-dismiss="modal">'
			+'保存'
			+'</button>'
			);
}
function addRoute(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var routeName = $("#routeName").val();
	var routeTime = $("#routeTime").val();
    var routeIntro = $("#routeIntro").val();
    var routeIdList = new Array();
	routeIdList=$("#specificRouteId").text().split(",");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/addRoute",
		type:"POST",
        dataType:"json",
		data:{
		    routeName:routeName,
            time:routeTime,
            content:routeIntro,
            attractionIds:JSON.stringify(routeIdList)
        },
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("保存成功");
                loadPage($("#page").text());
			}
			else
				alert("操作失败");
		},
		error:function(){
			alert("添加错误");
		}
	});
	
}
function updateRoute(id){
	<%--var token = $("meta[name='_csrf']").attr("content");  --%>
	<%--var header = $("meta[name='_csrf_header']").attr("content");--%>
	<%--var routeName=$("#placeNumber").val();--%>
	<%--var specificRoute=$("#specificRoute").text();--%>
	<%--var textIntroduction=$("#textIntro").val();--%>
	<%--$.ajax({--%>
		<%--url:"${pageContext.request.contextPath}/scenery/updateRoute",--%>
		<%--type:"POST",--%>
		<%--data:{id:id,routeName:routeName,specificRoute:specificRoute,textIntroduction:textIntroduction},--%>
		<%--beforeSend:function(xhr){--%>
			<%--xhr.setRequestHeader(header, token);--%>
		<%--},--%>
		<%--success:function(data){--%>
			<%--if(data)--%>
			<%--{--%>
				<%--alert("保存成功");--%>
                <%--loadPage($("#page").text());--%>
			<%--}--%>
			<%--else--%>
				<%--alert("操作失败");--%>
		<%--},--%>
		<%--error:function(){--%>
			<%--alert("更新错误");--%>
		<%--}--%>
	<%--});--%>
}
function getRouteInfo(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/getRouteDetail",
		type:"GET",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			var route = data;
			var allscenes="";
			$("#myModalLabel").text("路线详情");
			$("#routeName").val(route.routeName);
            $("#routeTime").val(route.time);
            $("#routeIntro").val(route.content);
			$("#specificRouteDiv").remove();
			$("#newitem").hide();
			allscenes ='<span id="specificRoute">'+ getRouteText(route,",") + '</span>';
			//allscenes = '<div class="inputDiv" id="specificRouteDiv">' + allscenes + '<span class="button"><span class="yuBtn">删除</span></span>' + '</div>';
            $("#ulStyle").append('<div class="inputDiv" id="specificRouteDiv">' +"路线上的景点：" + allscenes + '</div>');
            allscenes = '<div class="inputDiv" id="specificRouteDiv">' + allscenes + '</div>';
            //$("#ulStyle").append(allscenes);
			$("#twoButton").html("");
			// $("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
			// 		+'取消'
			// 		+'</button>'
			// 		+'<button type="button" class="sureBtn" onclick=updateRoute(' + route.id  + ') data-dismiss="modal">'
			// 		+	'保存'
			// 		+'</button>'
			// 		);
		},
		error:function(){
			alert("数据加载错误");
			$("#myModalLabel").text("新增景点");
			$("#placeNumber").val(" ");
			$("#specificRouteDiv").remove();
			$("#textIntro").val(" ");
			$("#twoButton").html("");
			$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
					+'取消'
					+'</button>'
					+'<button type="button" class="sureBtn" data-dismiss="modal">'
					+	'保存'
					+'</button>'
					);
}
	});	
}
$(document).ready(function() {
	$("#newitem").click(function() {
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:"${pageContext.request.contextPath}/scenery/getAllAttractionName",
			type:"POST",
			data:{},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){	
				var n =' <div class="scenechoose"> <ul class="list-group">';
				for(var i in data) 
				{
					var attraction = data[i];
					n+='<li class="list-group-item">'+attraction.name+'</li><li class="list-group-item-id">'+attraction.id+'</li>';
				}
				n+='</ul> </div>';
				$(".ulStyle").append("<div class='cell' id='specificRouteDiv'><div class='inputDiv'><span class='button'><span class='yuBtn'>删除</span><span class='confirm'>确认</span></span></div>" + n + "</div>");
				
			},error:function(){
				alert("数据加载错误");
			}
		});					
	});
	//删除路线
	$(document).on('click', '.yuBtn', function(e) {
//		var evt = e || window.event;
//		var target = evt.target || evt.srcElement;
//		$(target).parent().parent().parent().remove();
		$("#specificRouteDiv").remove();
	});
	//选择景点
	$(document).on('click', '.list-group-item', function(e) {
		var evt = e || window.event;
		var target = evt.target || evt.srcElement;
		var scene = $(target).text();
        var sceneId = $($(target).next()).text();
		var n = '<span class="scene">' + scene + '<i class="material-icons delete">close</i></span>'+'<span class="scene-id">' + sceneId + '</span>';
		$(target).parent().parent().parent().find(".button").before(n);
		$(target).remove();
	});
	//删除选择景点
	$(document).on('click', '.delete', function() {
		var scene = $(this).parent().html();
        var sceneId = $($($(this).parent()).next()).text();
		var s = scene.replace(/(.*)<[^>]*>.*<\/[^>]*>(.*)/, "$1$2");
        $($($(this).parent()).next()).remove();
		$(this).parent().remove();
		var n = '<li class="list-group-item">' + s + '</li><li class="list-group-item-id">'+ sceneId + '</li>';
		$(".list-group").before(n);
	});
	//确认
	$(document).on('click', '.confirm', function() {
		var scenes = $(this).parent().parent().find(".scene");
		var allscenes = "";
		scenes.each(function() {
			var s = $(this).html();
			s = s.replace(/(.*)<[^>]*>.*<\/[^>]*>(.*)/, "$1$2");
			if(allscenes == "") {
				var n = '<span id="specificRoute">' + s;
			} else {
				var n = ',' + s;
			}
			allscenes = allscenes + n;
		});
        var sceneIdList = $(this).parent().parent().find(".scene-id");
        var allscenesId = "";
        sceneIdList.each(function() {
            var s = $(this).text();
            if(allscenesId == "") {
                var n = '<span id="specificRouteId">' + s;
            } else {
                var n = ',' + s;
            }
            allscenesId = allscenesId + n;
        });
		allscenes = allscenes+ '</span>';
        allscenesId = allscenesId+ '</span>';
		allscenes = '<div class="inputDiv" id="specificRouteDiv">' + allscenes + allscenesId + '<span class="button"><span class="yuBtn">删除</span></span>' + '</div>';
		$(this).parent().parent().parent().html(allscenes);
	})
});
</script>
</body>
</html>