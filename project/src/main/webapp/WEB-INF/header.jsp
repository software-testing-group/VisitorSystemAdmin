<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar-wrapper">
    <div class="navbar-container">
      <div class="navbar-left-title">访客后台管理系统</div>
      <div class="navbar-option">
        <span class="navbar-right-title"><sec:authentication property="name"/></span>
        <li class="dropdown navbar-right" style="list-style-type:none;">
        <i class="material-icons" data-toggle="dropdown">more_vert</i>
        <ul class="dropdown-menu">
            
        	<%--<li data-toggle="modal" data-target="#modal-password">--%>
        		<%--<a>--%>
        		<%--<i class="material-icons" data-toggle="dropdown">password</i>--%>
        		<%--修改密码--%>
        		<%--</a>--%>
        	<%--</li>--%>
        	 
        	<li onclick="javascript:document.getElementById('logout').click();">
        		<a id="logout" href="${pageContext.request.contextPath}/logout">
        		<i class="material-icons" data-toggle="dropdown">setting</i>
        		退出登录
        		</a>
        	</li>
        </ul>
        </li>
      </div>
    </div>
</nav>

<div class="modal fade" id="modal-password" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content col-md-12">
			<div class="modal-body">
			   <div class="box">
				   <form class="form-horizontal">
				   	   <div class="box-body">
						   <div class="form-group">
						       <label for="oldPassword" class="control-label">旧密码</label>
						       <input type="password" class="form-control" id="oldPassword" placeholder="旧密码" onchange="checkOldPassword();">
						   </div>
						   <div class="form-group">
						       <label for="newPassword" class="control-label">新密码</label>
						       <input type="password" class="form-control" id="newPassword" onchange="checkNewPassword();" placeholder="新密码">
						   </div>
						   <div class="form-group">
						       <label for="newPasswordConfirm" class="control-label">确认新密码</label>
						       <input type="password" class="form-control" id="newPasswordConfirm" onchange="checkNewPassword();" placeholder="确认新密码">
						   </div>
						   <div class="form-group">
						   		<button type="submit" class="btn btn-primary pull-right" onclick="editPasswordSubmit();">确认更改</button>
						   	</div>
					   </div>
				   </form>
			   </div>
			</div>
		</div>
	</div>
</div>
<script src="/resources/js/authority/header.js"></script>