<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="_csrf" content="${_csrf.token}"/>  
  <meta name="_csrf_header" content="${_csrf.headerName}"/>  
  <title>后台管理系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body class="hold-transition login-page">
<div class="login-box" id="rrapp">
  <div class="login-logo">
    <b>访客预约</b>后台管理系统
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
      <p class="login-box-msg">管理员登录</p>
    
      <c:choose>
      <c:when test="${message!=null}"> 
	      <div class="alert alert-danger alert-dismissible">
	        <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle">${message}</i></h4>
	      </div>
      </c:when>
      <c:when test="${! empty SPRING_SECURITY_LAST_EXCEPTION.message}"> 
	      <div class="alert alert-danger alert-dismissible">
	        <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle">${SPRING_SECURITY_LAST_EXCEPTION.message}</i></h4>
	      </div>
      </c:when>
      </c:choose>
      <form name="loginForm">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="username" placeholder="账号">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="captcha" placeholder="验证码" onkeydown='if(event.keyCode==13){login();}'>
        <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <img id="captchaImage" alt="如果看不清楚，请单击图片刷新！" class="pointer" src="captcha.form" onclick="refreshCode()">
        &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="refreshCode()">点击刷新</a>
      </div>
      </form>
      
      <div class="row">
        <!-- /.col -->
        <div class="btn-group col-xs-12">
        <div class="col-xs-6">
          <button type="button" class="btn btn-primary btn-block btn-flat" onclick="xmulogin()">学工登陆</button>
        </div>
        <div class="col-xs-6">
          <button type="button" class="btn btn-default btn-block btn-flat" onclick="login()">登录</button>
        </div>
        </div>
        <!-- /.col -->
      </div>
    <!-- /.social-auth-links -->
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
function refreshCode()
{
	$('#captchaImage').attr("src", "captcha.form?timestamp=" + (new Date()).valueOf());
}

function checkCaptcha()
{
	var canSubmit = false;
	var captcha = $("[name='captcha']").val();
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		
		url:"${pageContext.request.contextPath}/checkCaptcha",
		type:"post",
		data:{captcha:captcha},
		async:false,
	    beforeSend:function(xhr)
	    {
	    	xhr.setRequestHeader(header, token); 
	    },
		success:function(data)
		{
			canSubmit = data;
		},error:function(){
			alert("内部错误，请联系开发人员");
		}
	});
	
	return canSubmit;
}

function login()
{
    var result = checkCaptcha();

    if (result !== "true") {
        alert("验证码错误");
        refreshCode();
        return ;
    }

    var username = $("[name='usernane']").val();
    var password = $("[name='password']").val();

    $("[name='loginForm']").attr("action","${pageContext.request.contextPath}/login");
    $("[name='loginForm']").attr("method","POST");
    $("[name='loginForm']").submit();

}

function xmulogin()
{	
	$("[name='loginForm']").attr("action","${pageContext.request.contextPath}/xmulogin");
	$("[name='loginForm']").attr("method","POST");
	$("[name='loginForm']").submit();	
}

</script>
</body>
</html>