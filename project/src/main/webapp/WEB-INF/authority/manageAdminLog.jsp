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
</head>
<body >
	<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<!--表格-->
  <div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">管理员历史记录</span>
        <span class="table-head-right">搜索与筛选</span>
      </div>
      <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">管理员用户名</span>
          <input id="username" type="text" class="input-box" placeholder="请输入...." />
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">操作内容</span>
          <input id="content" type="text" class="input-box" placeholder="请输入...." />
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">创建时间区间</span>
          <input id="start" type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <span>至</span>
          <input id="end" type="text" class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <button onclick="loadPage(1)">搜索</button>
        </div>
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive " style="width:1050px" id="content-table">
        <thead>
          <th>用户名</th>
          <th>创建时间</th>
          <th>上次登陆IP</th>
          <th>操作描述</th>
          <th class="export-wrapper" onclick="exportGraphCSV()">导出</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
          <span id="sumLogs">每页展示10条</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons">chevron_left</i>
          </span>
          <span id="page">1</span>/
          <span id="pageNumber">1</span>
          <span id="next">
            <i class="material-icons">chevron_right</i>
          </span>
        </div>
      </div>
    </div>
   </div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/authority/manageLog.js"></script>
<script type="text/javascript">

$('.datetimepicker').datetimepicker({
    format: 'yyyy-mm-dd hh:mm:ss',
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