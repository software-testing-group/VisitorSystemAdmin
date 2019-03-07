<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navigation/infrastructure.css">
</head>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">基础设施管理</span>
      </div>

    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>设施类编号</th>
          <th>类型名称</th>
          <th>操作</th>
          <th data-toggle="modal" data-target="#myModal" class="export-wrapper" onclick="clearModal()">新增</th> 
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
		<div class="add-content">
			<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="demo-title" id="myModalLabel">设施详情</h4>
            </div>

            <div class="infrastructure-name new-form-div">
                <span class="new-form-span name">类型名称：</span>
                <input id="infrastructureTypeName" name="infrastructureTypeName"/><br>
				<input type="hidden" id="infrastructureId" name="infrastructureId" value="" />
            </div>
            <div class="position new-form-div">
				<input type="hidden" class="single-infrastructureId" name="single-infrastructureId" value="" />
				<span class="new-form-span">具体设施：</span>
				<input class="infrastructure-name-input" name="infrastructureName" placeholder="设施名称"/>
				<span class="new-form-span-titude">经度：</span>
                <input class="infrastructure-input longitude" name="longitude" placeholder="经度"/>
				<span class="new-form-span-titude">纬度：</span>
				<input class="infrastructure-input latitude" name="latitude" placeholder="纬度"/>
                <i class="material-icons new-position" onclick="newPosition(this)">add_circle_outline</i>
                <i class="material-icons remove-position" onclick="removePosition(this)">remove_circle_outline</i>
            </div>
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
var sumInfrastructure=0;
var initialList=[];
var deleteList=[];
$(function(){
	getSumInfrastructure();
	var page=$("#page").text();
	loadPage(page);  //加载页面
})
function clearModal(){
	$("#myModalLabel").text("新增设施");
	$("#infrastructureTypeName").val("");
	$("#infrastructureId").val("");
	$(".position.new-form-div").remove();
    newPosition();
	$("#twoButton").html("");
	$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
		+'取消'
		+'</button>'
		+'<button type="button" class="sureBtn" onclick=addInfrastructure() value="保存"  data-dismiss="modal" >'
		+'保存'
		+'</button>'
	);
}
function addInfrastructure(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var infrastructureTypeId=$("#infrastructureId").val();
	var infrastructureTypeName=$("#infrastructureTypeName").val();
	var infrastructures=[];
	$("input[class='infrastructure-name-input']").each(
	    function(index){
	        var name=$(this).val();
	        var longitude=$("input[class='infrastructure-input longitude']")[index].value;
	        var latitude=$("input[class='infrastructure-input latitude']")[index].value;
			if(name!==""&&longitude!==""&&latitude!==""){
				infrastructures.push({
					'name':name,
					'typeId':infrastructureTypeId,
					'longitude':longitude,
					'latitude':latitude
				})
			}
		}
	);
    $.ajax({
		url:"${pageContext.request.contextPath}/infrastructure/addInfrastructureType",
		type:"POST",
		data:{
			infrastructureTypeName:infrastructureTypeName,
            infrastructures:JSON.stringify(infrastructures)
		},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
            alert("操作成功");
            loadPage($("#page").text());
		},
		error:function(){
			alert("添加失败");
		}
    });
}

function updateInfrastructure(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var infrastructureId=$("#infrastructureId").val();
    var infrastructureTypeName=$("#infrastructureTypeName").val();
    var infrastructures=[];
    var addList=[];
    var modifyList=[];
	$("input[class='infrastructure-name-input']").each(
		function(index){
			infrastructures.push({
				'id':$("input[class='single-infrastructureId']")[index].value,
				'name':$(this).val(),
				"typeId":infrastructureId,
				'longitude':$("input[class='infrastructure-input longitude']")[index].value,
				'latitude':$("input[class='infrastructure-input latitude']")[index].value
			});
		}
	);
	//判断添加/修改的设施
	for(var i in infrastructures){
		var inf = infrastructures[i];
		if(inf.id===""&&inf.name!==""&&inf.longitude!==""&&inf.latitude!==""){
			addList.push({
			'name':inf.name,
			'typeId':inf.typeId,
			'longitude':inf.longitude,
			'latitude':inf.latitude
			});
			continue;
		}
		modifyList.push({
			'id':inf.id,
			'name':inf.name,
			'typeId':inf.typeId,
			'longitude':inf.longitude,
			'latitude':inf.latitude
		});
	}
	//修改设施类的信息
	$.ajax({
		url:"${pageContext.request.contextPath}/infrastructure/editInfrastructureType?id="+infrastructureId+"&name="+infrastructureTypeName,
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
		    addSingleInfrastructure(addList);
		    modifySingleInfrastructure(modifyList);
			deleteSingleInfrastructure();
			alert("修改设施类成功");
            loadPage($("#page").text());
		},
		error:function(){
			alert("添加失败");
		}
	});
}

//添加设施
function addSingleInfrastructure(addList) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
    for(var i in addList){
        // if(addList[i].name!==""&&addList[i].longitude!==""&&addList[i].latitude!==""){
         //    continue;
		// }
		$.ajax({
			url:"${pageContext.request.contextPath}/infrastructure/addInfrastructure",
			type:"POST",
			data:{
			    name:addList[i].name,
			    typeId:addList[i].typeId,
				longitude:addList[i].longitude,
				latitude:addList[i].latitude
			},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){

			},
			error:function(){

			}
		});
	}
}

//修改设施
function modifySingleInfrastructure(modifyList) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	for(var i in modifyList){
		$.ajax({
			url:"${pageContext.request.contextPath}/infrastructure/updateInfrastructure",
			type:"GET",
			data:{
				id:modifyList[i].id,
				name:modifyList[i].name,
				typeId:modifyList[i].typeId,
				longitude:modifyList[i].longitude,
				latitude:modifyList[i].latitude,
			},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){

			},
			error:function(){
				alert("删除设施失败!");
			}
		});
	}
}

//删除设施
function deleteSingleInfrastructure() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	for(var i in deleteList){
		$.ajax({
			url:"${pageContext.request.contextPath}/infrastructure/deleteInfrastructure",
			type:"GET",
			data:{
				id:deleteList[i]
			},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){

			},
			error:function(){
				alert("删除设施失败!");
			}
		});
	}
}

function getSumInfrastructure(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/infrastructure/getSumOfInfrastructureTypes",
		type:"GET",
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){		
			sumInfrastructure=data;
		},error:function(){
			alert("数据加载错误");
		}
		
	});
}
function loadPage(page)
{
	getSumInfrastructure();
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/infrastructure/getInfrastructureTypes",
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
				var infrastructure = data[i];
				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + infrastructure.id + '</td>'
						+   '<td>' + infrastructure.name + '</td>'
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getInfrastructureInfo(' + infrastructure.id  + ');">编辑' +'</a>'
						+' | ' + '<a href="javascript:deleteInfrastructure(' + infrastructure.id  + ');">'
						+ '删除'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}	
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});
	
}

function deleteInfrastructure(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/infrastructure/deleteInfrastructureType",
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
	page=page-1;
	if(page<1){
		alert("已是第一页");
	}
	else{
	loadPage(page);
	 $("#page").text(page);
	}
	
}
function nextpage(){
	var page=$("#page").text();
	page=page-0+1;
	if((page-1)*10>=sumInfrastructure){
		alert("已是最后一页");
	}
	else{
	loadPage(page);
	 $("#page").text(page);
	}
	
}
function getInfrastructureInfo(id) {
    	clearModal();
    	initialList=[];
    	deleteList=[];
        $(".position.new-form-div").remove();
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url:"${pageContext.request.contextPath}/infrastructure/getInfrastructureTypeDetailById",
			type:"POST",
			data:{id:id},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){
				var infrastructure = data;
				$("#myModalLabel").text("设施类详情");
				$("#infrastructureTypeName").val(infrastructure.name);
				$("#infrastructureId").val(infrastructure.id);
				if(infrastructure.infrastructureList.length==0){
				    newPosition();
				}
				else{
					for(var i in infrastructure.infrastructureList){
						var inf=infrastructure.infrastructureList[i];
						newPosition();
						$($(".single-infrastructureId").get(i)).val(inf.id);
						$($(".infrastructure-name-input").get(i)).val(inf.name);
						$($(".longitude").get(i)).val(inf.longitude);
						$($(".latitude").get(i)).val(inf.latitude);
						initialList.push({
							'id':inf.id,
							'name':inf.name,
							"typeId":inf.typeId,
							'longitude':inf.longitude,
							'latitude':inf.latitude
						});
					}
				}
				$("#twoButton").html("");
				$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
						+'取消'
						+'</button>'
						+'<button type="button" class="sureBtn" onclick=updateInfrastructure() value="保存"  data-dismiss="modal" >'
						+'保存'
						+'</button>'
						);
			},
			error:function(){
				alert("数据加载错误");
				$("#myModalLabel").text("设施类详情");
				$("#infrastructureTypeName").val("");
				$("#infrastructureId").val("");
				$("#twoButton").html("");
				$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
						+'取消'
						+'</button>'
						+'<button type="button" class="sureBtn" value="保存"  data-dismiss="modal">'
						+'保存'
						+'</button>'
						);
			
	}
		});	
}
function removePosition(element) {
    var length=document.getElementsByClassName("new-position").length;
	var target=$(element).parent();
	var id=$(target).children(".single-infrastructureId").get(0).value;
	if(id!=""){
	deleteList.push(id);
	}
	$(target).remove();
    if(length==1){
        newPosition();
    }
}

function newPosition() {
    $("#twoButton").before("<div class=\"position new-form-div\">\n"+
	"<input type=\"hidden\" class=\"single-infrastructureId\" name=\"single-infrastructureId\" value=\"\" />\n"+
    "<span class=\"new-form-span\">具体设施：</span>\n"+
    "<input class=\"infrastructure-name-input\" name=\"infrastructureName\" placeholder=\"设施名称\"/>\n"+
    "<span class=\"new-form-span-titude\">经度：</span>"+
    " <input class=\"infrastructure-input longitude\" name=\"longitude\" placeholder=\"经度\"/>\n"+
    "<span class=\"new-form-span-titude\">纬度：</span>\n"+
    "<input class=\"infrastructure-input latitude\" name=\"latitude\" placeholder=\"纬度\"/>\n"+
    "                <i class=\"material-icons new-position\" onclick=\"newPosition(this)\">add_circle_outline</i>\n"+
    "                <i class=\"material-icons remove-position\" onclick=\"removePosition(this)\">remove_circle_outline</i>\n"+
    "            </div>"
    );
}

</script>
</body>
</html>