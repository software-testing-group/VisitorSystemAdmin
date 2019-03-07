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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navigation/manageAttraction.css">
</head>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">景点管理</span>
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
          <th>景点编号</th>
          <th>景点名称</th>
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
		<div class="demo-content add-popup">
			<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="demo-title" id="myModalLabel">景点详情</h4>
            </div>

            <span class="new-form-span">景点编号：</span>
        	<input class="new-form-input" id="attractionNumber" name="attractionNumber"/><br>
        	<span class="new-form-span">景点名称：</span>
        	<input class="new-form-input" id="attractionName" name="attractionName"/><br>
            <span class="new-form-span">开放时间：</span>
            <%--<input class="new-form-input" id="openTime" type="time" name="openTime"/>--%>
            <%---<input class="new-form-input" id="closeTime" type="time" name="closeTime"/><br>--%>
            <input class="new-form-input" id="openTime" name="openTime"/><br>
            <span class="new-form-span">经度：</span>
            <input class="new-form-input" id="longitude" name="longitude"/><br>
            <span class="new-form-span">纬度：</span>
            <input class="new-form-input" id="latitude" name="latitude"/><br>
			<span class="new-form-span">文字介绍：</span>
			<textarea id="attractionIntro" name="attractionIntro" th:field="*{attractionIntro}"></textarea>

            <form id="attractionForm" action="${pageContext.request.contextPath}/scenery/fileUpload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="attractionId" name="attractionId" value="" />
            <input type="hidden" id="imageId" name="imageId" value="" />
            <table class="table table-responsive" id="pic-table">
                <thead>
                    <th style="width: 235px">图片文件地址</th>
                    <th>操作</th>
                    <%--<th><a href="javascript:addPic();">新增</a></th>--%>
                </thead>
                <tbody>
                </tbody>
            </table>
     		<br>
            <table class="table table-responsive" id="audio-table">
                <thead>
                    <th>音频类型</th>
                    <th>文件地址</th>
                    <th>操作</th>
                    <%--<th><a href="javascript:addAudio();">新增</a></th>--%>
                </thead>
                <tbody>
                </tbody>
            </table>
			</form>

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
var sumAttraction=0;
var picDeleteList=[];
var audioDeleteList=[];
$(function(){
	getSumAttraction();
	var page=$("#page").text();
	loadPage(page);  //加载页面
});
function clearModal(){
	$("#myModalLabel").text("新增景点");
    $("#attractionId").val("");
    $("#attractionNumber").val("");
    $("#attractionName").val("");
    $("#attractionIntro").val("");
    $("#openTime").val("");
    $("#longitude").val("");
    $("#latitude").val("");
    $("#pic-table td").remove();
    $("#audio-table td").remove();
    addPic();
    addAudio();
	$("#twoButton").html("");
	$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
			+'取消'
			+'</button>'
			+'<button type="button" class="sureBtn" onclick=addAttraction() value="保存" data-dismiss="modal" >'
			+'保存'
			+'</button>'
			);
}
function fileUpload(){
    $("#attractionForm").submit();
}
function pictureUpload(id) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var elementList=document.getElementsByClassName("imageFile");
    var fd = new FormData();
    for(var i=0;i<elementList.length;i++){
        if(elementList[i].files[0]!=undefined){
            fd.append('files',elementList[i].files[0]);
        }
    }
    fd.append('attractionId',id);
    $.ajax({
        url:"${pageContext.request.contextPath}/scenery/imagesUpload",
        type:"POST",
        dataType:"json",
        data:fd,
        contentType: false,
        processData: false,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
        },
        error:function(){
            alert("添加失败");
        }
    });
}
function audioUpload(id) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var elementList=document.getElementsByClassName("audioFile");
    var elementListId=document.getElementsByClassName("audio-type-input");
    var fd = new FormData();
    for(var i=0;i<elementList.length;i++){
        if(elementList[i].files[0]!=undefined&&elementListId[i].value!="") {
            fd.append('files', elementList[i].files[0]);
            fd.append('types', elementListId[i].value);
        }
    }
    fd.append('attractionId', id);
    $.ajax({
        url:"${pageContext.request.contextPath}/scenery/audiosUpload",
        type:"POST",
        dataType:"json",
        data:fd,
        contentType: false,
        processData: false,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
        },
        error:function(){
            alert("添加失败");
        }
    });
}
function addAttraction(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
    var attractionNumber=$("#attractionNumber").val();
    var attractionName=$("#attractionName").val();
    var attractionIntro=$("#attractionIntro").val();
    var openTime=$("#openTime").val();
    var longitude=$("#longitude").val();
    var latitude=$("#latitude").val();
    $.ajax({
		url:"${pageContext.request.contextPath}/scenery/addAttraction",
		type:"GET",
		data:{
		    orderNumber:attractionNumber,
            name:attractionName,
            textIntroduction:attractionIntro,
            openTime:openTime,
            longitude:longitude,
            latitude:latitude
        },
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			var attraction=data;
			pictureUpload(attraction.id);
            audioUpload(attraction.id);
            loadPage($("#page").text());
		},
		error:function(){
			alert("添加失败");
		}
	});
}
function updateAttraction(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var attractionId=$("#attractionId").val();   // 根据ID来对相应的景点数据进行更新
    var attractionNumber=$("#attractionNumber").val();
    var attractionName=$("#attractionName").val();
    var attractionIntro=$("#attractionIntro").val();
    var openTime=$("#openTime").val();
    var longitude=$("#longitude").val();
    var latitude=$("#latitude").val();
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/updateAttraction",
		type:"GET",
		data:{
		    id:attractionId,
            orderNumber:attractionNumber,
            name:attractionName,
            textIntroduction:attractionIntro,
            openTime:openTime,
            longitude:longitude,
            latitude:latitude
        },
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){  // ！！！！！！把涉及URL的更新放到文件上传过程中
            if(data)
            {
                addPictureList();
                deletePictureList();
                addAudioList();
                deleteAudioList();
                alert("操作成功");
                loadPage($("#page").text());
            }
            else
                alert("操作失败");
		},
		error:function(){
			alert("更新失败");
		}
	});
}
function getSumAttraction(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/getSumOfAttractions",
		type:"GET",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){		
			sumAttraction=data;
		},error:function(){
			alert("数据加载错误");
		}
		
	});
}
function loadPage(page) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/getAllAttraction",
		type:"POST",
		data:{page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){    // data 是所有景点的数组
			$("#content-table tbody").html("");
			//放入数据
			for(var i in data) 
			{
				var attraction = data[i];
				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + attraction.orderNumber + '</td>'     // 景点编号
						+   '<td>' + attraction.name + '</td>'                           // 获得某一景点的详细数据                  此时用的是景点ID
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getAttractionInfo(' + attraction.id  + ');">编辑' +'</a>'
						+' | ' + '<a href="javascript:deleteAttraction(' + attraction.id  + ');">'
						+ '删除'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}
		},
		error:function(){
			alert("数据加载错误");
		}
	});
}
function deleteAttraction(id) {
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/deleteAttraction",
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
	if((page-1)*10>=sumAttraction){
		alert("已是最后一页");
	}
	else{
	loadPage(page);
	 $("#page").text(page);
	}
}
function getFileUrl(sourceId) {
	var url;
	if(navigator.userAgent.indexOf("MSIE") >= 1) { // IE
		url = document.getElementById(sourceId).value;
	} else if(navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	} else if(navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	}
	return url;
}
function preImg(sourceId, filepathId) {
	// var filepath = $("input[id='imageFile']").val();   // 文件路径
	// var arr = filepath.split('\\');
	// var fileName = arr[arr.length - 1];       // 文件名称
	// var path = document.getElementById(filepathId);
	// path.innerHTML = fileName;
}
function preAudio(sourceId, filepathId) {
    // var filepath = $("input[id='audioFile']").val();
    // var arr = filepath.split('\\');
    // var fileName = arr[arr.length - 1];
    // var path = document.getElementById(filepathId);
    // path.innerHTML = fileName;
}
function getAttractionInfo(id) {
    picDeleteList=[];
    audioDeleteList=[];
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $("#pic-table td").remove();
    $("#audio-table td").remove();
	$.ajax({
		url:"${pageContext.request.contextPath}/scenery/getAttractionById",
        type:"GET",
        data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			var attraction = data;
			$("#myModalLabel").text("景点详情");
            $("#attractionId").val(attraction.id);
			$("#attractionNumber").val(attraction.orderNumber);
			$("#attractionName").val(attraction.name);
			$("#attractionIntro").val(attraction.textIntroduction);
            $("#openTime").val(attraction.openTime);
            $("#longitude").val(attraction.longitude);
            $("#latitude").val(attraction.latitude);
            //置放图片列表
            if(attraction.images.length==0){
                addPic();
            }
            else{
                for(var j in attraction.images){
                    $("#pic-table tbody").append(
                        '<tr role="row">'
                        +   '<td>' + '<input type="hidden" class="picId" name="picId" value='+attraction.images[j].id+'>'
                        +   "<a class='picUrl' target='_blank' href="+ attraction.images[j].imageUrl + ">图片"+((parseInt(j)+1).toString())+"</a><br>" + '</td>'
                        +   '<td>' + '<i class="material-icons new-position" onclick="addPic(this)">add_circle_outline</i><i class="material-icons remove-position pic-remove" onclick="removePic(this)">remove_circle_outline</i>'+'</td>'
                        +   '</tr>'
                    );
                }
            }

            //置放音频列表
            if(attraction.audios.length==0){
                addAudio();
            }
            else{
                for(var k in attraction.audios){
                    $("#audio-table tbody").append(
                        '<tr role="row">'
                        +   '<td>' + attraction.audios[k].type + '</td>'
                        +   '<td>' + '<input type="hidden" class="audioId" name="audioId" value='+attraction.audios[k].id+'>'
                        +   "<a class='picUrl' target='_blank' href="+ attraction.audios[k].audioUrl + ">音频"+((parseInt(k)+1).toString())+"</a><br></td>"
                        +   '<td><i class="material-icons new-position" onclick="addAudio(this)">add_circle_outline</i><i class="material-icons remove-position audio-remove" onclick="removeAudio(this)">remove_circle_outline</i></td>'
                        +   '</tr>'
                    );
                }
            }
			$("#myModalLabel").text("景点详情");
			$("#twoButton").html("");
			$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
					+'取消'
					+'</button>'
					+'<button type="button" class="sureBtn" onclick= updateAttraction()  value="保存"  data-dismiss="modal" >'
					+'保存'
					+'</button>'
				);
		},
		error:function(){
			alert("数据加载错误");
			$("#myModalLabel").text("景点详情");
            $("#attractionId").val("");
            $("#attractionNumber").val("");
            $("#attractionName").val("");
            $("#attractionIntro").val("");
            $("#openTime").val("");
            $("#longitude").val("");
            $("#latitude").val("");
            $("#pic-table td").remove();
            $("#audio-table td").remove();
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
function addAudio() {
    $("#audio-table tbody").append(
        '<tr role="row">'
        +   '<td>' + "<input class='new-table-input audio-type-input' name='audioType'/>" + '</td>'
        +   '<td>' + '<input type="hidden" class="audioId" name="audioId" value=""/>'
        +   '<input type="file" class="audioFile" name="audioFile" style="position:absolute;width:105px" accept="audio/*" value="" onchange="preAudio(this.id,\'filepath\');"/>'+ '</td>'
        +   '<td>' + '<i class="material-icons new-position" onclick="addAudio(this)">add_circle_outline</i>'
        +   '<i class="material-icons remove-position audio-remove" onclick="removeAudio(this)">remove_circle_outline</i>'+'</td>'
        +   '<td>' +'</td>'
        +   '</tr>'
    )
}
function addPic() {
    $("#pic-table tbody").append(
        '<tr role="row">'
        +   '<td>' + '<input type="hidden" class="picId" name="picId" value=""/>'
        +   '<input type="file" class="imageFile" name="file" style="position:absolute;width:190px" accept="image/png, image/jpeg, image/gif, image/jpg" value=""/>'+ '</td>'
        +   '<td>' + '<i class="material-icons new-position" onclick="addPic(this)">add_circle_outline</i>'
        +   '<i class="material-icons remove-position pic-remove" onclick="removePic(this)">remove_circle_outline</i>'+'</td>'
        +   '<td>' + '</td>'
        +   '</tr>'
    )
}
function removeAudio(element) {
    var length=document.getElementsByClassName("audio-remove").length;
    var target=$($(element).parent()).parent();
    var id=$(target).find(".audioId").get(0).value;
    if(id!=""){
        audioDeleteList.push(id);
    }
    $(target).remove();
    if(length==1){
        addAudio();
    }
}
function removePic(element) {
    var length=document.getElementsByClassName("pic-remove").length;
    var target=$($(element).parent()).parent();
    var id=$(target).find(".picId").get(0).value;
    if(id!=""){
        picDeleteList.push(id);
    }
    $(target).remove();
    if(length==1){
        addPic();
    }
}
function addPictureList() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var attractionId=$("#attractionId").val();
    var fd = new FormData();
    $("input[class='imageFile']").each(
        function(index){
            if($("input[class='imageFile']")[index].files[0]!=undefined){
                fd.append('files',$("input[class='imageFile']")[index].files[0]);
            }
        }
    );
    fd.append('attractionId',attractionId);
    $.ajax({
        url:"${pageContext.request.contextPath}/scenery/imagesUpload",
        type:"POST",
        dataType:"json",
        data:fd,
        contentType: false,
        processData: false,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){

        },
        error:function(){
            alert("添加图片失败");
        }
    });
}
function deletePictureList() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    for(var i in picDeleteList){
        $.ajax({
            url:"${pageContext.request.contextPath}/scenery/deleteImage",
            type:"GET",
            data:{
                id:picDeleteList[i]
            },
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:function(data){

            },
            error:function(){
                alert("删除图片失败!");
            }
        });
    }
}
function addAudioList() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var attractionId=$("#attractionId").val();
    var elementList=document.getElementsByClassName("audioFile");
    var elementListId=document.getElementsByClassName("audio-type-input");
    var fd = new FormData();
    for(var i=0;i<elementList.length;i++){
        if(elementList[i].files[0]!=undefined&&elementListId[i].value!=""){
            fd.append('files',elementList[i].files[0]);
            fd.append('types',elementListId[i].value);
        }
    }
    fd.append('attractionId', attractionId);
    $.ajax({
        url:"${pageContext.request.contextPath}/scenery/audiosUpload",
        type:"POST",
        dataType:"json",
        data:fd,
        contentType: false,
        processData: false,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){

        },
        error:function(){
            alert("添加音频失败");
        }
    });
}
function deleteAudioList() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    for(var i in audioDeleteList){
        $.ajax({
            url:"${pageContext.request.contextPath}/scenery/deleteAudio",
            type:"GET",
            data:{
                id:audioDeleteList[i]
            },
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success:function(data){

            },
            error:function(){
                alert("删除音频失败!");
            }
        });
    }
}
</script>
</body>
</html>