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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navigation/questionAndResponse.css">
</head>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">帮助问题</span>
        <!-- <span class="table-head-right">搜索与筛选</span> -->
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>问题内容</th>
          <th>回答</th>
          <th>操作</th>
          <th data-toggle="modal" data-target="#myModal" class="export-wrapper" onclick="clearModal()">新增</th> 
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
 <!--         <span>共
            <span>8</span>条信息</span>-->
          <span>每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons" onclick="previousQuestionPage()">chevron_left</i>
          </span>
          <span id="page-question">1</span>
          <span id="next">
            <i class="material-icons" onclick="nextQuestionPage()">chevron_right</i>
          </span>
        </div>
      </div>
    </div>
</div>

<div class="table-wrapper">
    <div class="table-head">
        <div class="title-wrapper">
            <span class="table-head-left">用户意见反馈</span>
        </div>
    </div>
    <div class="content-table-wrapper">
        <table class="table table-responsive" id="content-table-suggestion">
            <thead>
                <th>反馈内容</th>
                <th>用户ID</th>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div class="tfoot">
            <div class="tfoot-text">
                <span>每页展示10条</span>
            </div>
            <div class="tfoot-page">
                <span id="prev-suggestion">
                    <i class="material-icons" onclick="previousCommentPage()">chevron_left</i>
                </span>
                <span id="page-suggestion">1</span>
                <span id="next-suggestion">
                    <i class="material-icons" onclick="nextCommentPage()">chevron_right</i>
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
				<h4 class="demo-title" id="myModalLabel">问题详情</h4>
            </div>
	        <input type="hidden" id="questionId" name="questionId" value="" />
			<p class="place">问题内容</p>
			<textarea id="question-content" name="textIntroduction" th:field="*{textIntroduction}"></textarea>
            <p class="place-response">回答</p>
            <textarea id="response-content" name="textIntroduction" th:field="*{textIntroduction}"></textarea>
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
var sumQuestion=0;
var sumComment=0;

$(function(){
	getSumQuestion();
	getSumComment();
	var questionPage=$("#page-question").text();
	var commentPage=$("#page-suggestion").text();
	loadQuestionPage(questionPage);  //加载问题
	loadCommentPage(commentPage); //加载用户反馈
})
function clearModal(){
	$("#myModalLabel").text("新增问题");
	$("#question-content").val("");
	$("#response-content").val("");
	$("#questionId").val("");
	$("#twoButton").html("");
	$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
			+'取消'
			+'</button>'
			+'<button type="button" class="sureBtn" onclick=addQuestion() value="保存" data-dismiss="modal" >'
			+'保存'
			+'</button>'
			);
}

function addQuestion(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
    var question=$("#question-content").val();
    var answer=$("#response-content").val();
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/addHelp",
		type:"POST",
		data:{
            question:question,
            answer:answer
        },
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
            if(data)
            {
                alert("操作成功");
                loadQuestionPage($("#page-question").text());
            }
            else
                alert("操作失败");
		},
		error:function(){
			alert("添加失败");
		}
	});
}
function updateQuestion(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	var question=$("#question-content").val();
	var answer=$("#response-content").val();
	var questionId=$("#questionId").val();
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/updateHelp",
		type:"POST",
		data:{
		    id:questionId,
            question:question,
            answer:answer
        },
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
            if(data)
            {
                alert("操作成功");
                loadQuestionPage($("#page-question").text());
            }
            else
                alert("操作失败");
		},
		error:function(){
			alert("修改失败");
		}
	});
}
function getSumQuestion(){
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/getSumOfHelps",
		type:"GET",
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			sumQuestion=data;
		},error:function(){
			alert("数据加载错误");
		}
	});
}
function getSumComment(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/getSumOfFeedbacks",
		type:"GET",
		data:{
		  userId:"",
	      content:""
		},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			sumComment=data;
		},error:function(){
			alert("数据加载错误");
		}
	});
}
function loadQuestionPage(page)
{
	getSumQuestion();
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/getHelps",
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
				var quesitonInfo = data[i];

				$("#content-table tbody").append('<tr role="row">'
						+   '<td>' + quesitonInfo.question + '</td>'
						+   '<td>' + quesitonInfo.answer + '</td>'
						+   '<td>' + '<a data-toggle="modal" data-target="#myModal" onclick="javascript:getQuestionInfo(' + quesitonInfo.id  + ');">编辑' +'</a>'
						+' | ' + '<a href="javascript:deleteQuestion(' + quesitonInfo.id  + ');">'
						+ '删除'  +'</a>'+'</td>'
						+   '</tr>'
				);
			}	
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});
};

function loadCommentPage(page)
{
	getSumComment();
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/getFeedbacks",
		type:"GET",
		data:{
		    page:page,
			id:"",
			content:""
		},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			$("#content-table-suggestion tbody").html("");
			//放入数据
			for(var i in data)
			{
				var commentInfo = data[i];
				$("#content-table-suggestion tbody").append('<tr role="row">'
					+   '<td>' + commentInfo.content + '</td>'
					+   '<td>' + commentInfo.userId + '</td>'
					+   '</tr>'
				);
			}
		},error:function(){
			alert("数据加载错误");
		}
	});
}

function deleteQuestion(id)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url:"${pageContext.request.contextPath}/helpAndFeedback/deleteHelp",
		type:"POST",
		data:{id:id},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("操作成功");
				loadQuestionPage($("#page-question").text());
			}
			else
				alert("操作失败");
		},
		error:function(){
			alert("禁用错误");
		}
	});
	
}
function previousQuestionPage(){
	var page=$("#page-question").text();
	page=page-1;
	if(page<1){
		alert("已是第一页");
	}
	else{
	loadQuestionPage(page);
	 $("#page-question").text(page);
	}
}
function nextQuestionPage(){
	var page=$("#page-question").text();
	page=page-0+1;
	if((page-1)*10>=sumQuestion){
		alert("已是最后一页");
	}
	else{
	loadQuestionPage(page);
	 $("#page-question").text(page);
	}
}
function previousCommentPage(){
	var page=$("#page-suggestion").text();
	page=page-1;
	if(page<1){
		alert("已是第一页");
	}
	else{
		loadCommentPage(page);
		$("#page-suggestion").text(page);
	}
}
function nextCommentPage(){
	var page=$("#page-suggestion").text();
	page=page-0+1;
	if((page-1)*10>sumComment){
		alert("已是最后一页");
	}
	else{
		loadCommentPage(page);
		$("#page-suggestion").text(page);
	}
}
function getQuestionInfo(id) {
		var token = $("meta[name='_csrf']").attr("content");  
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url:"${pageContext.request.contextPath}/helpAndFeedback/getHelpDetailById",
			type:"POST",
			data:{id:id},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data){
				var questionInfo = data;
				$("#myModalLabel").text("问题详情");
				$("#question-content").val(questionInfo.question);
				$("#response-content").val(questionInfo.answer);
				$("#questionId").val(questionInfo.id);
				$("#twoButton").html("");
				$("#twoButton").append('<button type="button" class="quitBtn" data-dismiss="modal">'
						+'取消'
						+'</button>'
						+'<button type="button" class="sureBtn" onclick=updateQuestion() value="保存" data-dismiss="modal" >'
						+'保存'
						+'</button>'
						);
			},
			error:function(){
				alert("数据加载错误");
                $("#myModalLabel").text("问题详情");
                $("#question-content").val("");
                $("#response-content").val("");
                $("#questionId").val("");
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
</script>
</body>
</html>