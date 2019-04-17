<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="_csrf" content="${_csrf.token}"/>  
  <meta name="_csrf_header" content="${_csrf.headerName}"/>  
  <title>访客后台管理系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/table.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/awesome-bootstrap-checkbox.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/build.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/default.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/style.css">
</head>
<body>
	<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<!--表格-->
  <div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">角色列表</span>
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>角色名</th>
          <th>描述</th>
          <th>上次编辑时间</th>
          <th>编辑</th>
          <th class="export-wrapper" onclick="exportGraphCSV()">导出</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
        <!--  <span>共
            <span>8</span>条信息</span> -->
          <span>每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons">chevron_left</i>
          </span>
          <span id="page">1</span>
          <span id="next">
            <i class="material-icons">chevron_right</i>
          </span>
        </div>
      </div>
      <div>
      	<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-block btn-default">新增
      	</button>
      </div>
    </div>
   </div>
   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="demo-content">
					<div class="demo-title">编辑角色</div>
					<div class="form-group">
						<label class="control-label">角色名</label>
						<input class="form-control" id="role-name" onchange="addRoleCheck();"/>
					</div>
					<div class="form-group">
						<label class="control-label">描述</label>
						<input class="form-control" id="description"/>
					</div>
					<input type="hidden" id="editRoleId"/> 
					<div class="modal-body">
					</div>
				<div class="bottom-btn">
					<button class="btn" onclick="submit()">保存</button>
				</div>
			</div>
		</div>
		</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/authority/popwinauthority.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/authority/manageRole.js"></script>
<script type="text/javascript">
$('.datetimepicker').datetimepicker({
    format: 'yyyy年mm月dd日',
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 0,
    startView: 2,
    minView: 2,
    forceParse: 0
});
</script>
</body>
</html>