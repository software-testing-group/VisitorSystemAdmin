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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/authority/select2.min.css">
</head>
<body>
	<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<!--表格-->
  <div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">管理员</span>
        <span class="table-head-right">搜索与筛选</span>
      </div>
      <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">用户名</span>
          <input id="filter-name"  type="text" class="input-box" placeholder="请输入...." />
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">学工号</span>
          <input id="filter-id"  type="text" class="input-box" placeholder="请输入...." />
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">角色</span>
          <select id="filter-selector" class="filter-selector" >
            <option value="0" selected>-请选择-</option>
          </select>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">上次登陆时间</span>
          <input id="filter-tstart" type="text"  class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <span>至</span>
          <input id="filter-tend" type="text"  class="form-control datetimepicker" readonly placeholder="请选择日期" />
          <button onclick="loadPageFilter(1)">搜索</button>
        </div>
      </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>用户名</th>
          <th>学工号</th>
          <th>上次登陆时间</th>
          <th>上次登陆ip</th>
          <th>角色</th>
          <th>是否禁用</th>
          <th>编辑</th>
          <th class="export-wrapper" onclick="exportGraphCSV()">导出</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
          <span id="sumAdminUser">每页展示10条</span>
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
      <div>
      	<button type="button" data-toggle="modal" data-target="#modal-add-manager" class="btn btn-block btn-default">新增
      	</button>
      </div>
    </div>
   </div>
    <div class="modal fade" id="modal-add-manager" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">×</span></button>
                <h4 class="modal-title">新增管理员</h4>
              </div>
              <div class="modal-body">
                <div class="box">
		            <form class="form-horizontal">
		              <div class="box-body">
		                <div class="form-group">
		                  <label for="inputUsername" class="col-sm-2 control-label">用户名</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="username" placeholder="用户名/必填">
		                  </div>
		                </div>
		                <div class="form-group">
		                  <label for="inputPassword" class="col-sm-2 control-label">密码</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="password" placeholder="密码/必填">
		                  </div>
		                </div>
		                <div class="form-group">
		                  <label for="inputStaffId" class="col-sm-2 control-label">学工号</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="staffId" placeholder="学工号/可暂时不填">
		                  </div>
		                </div>
		              </div>
		            </form>
          		</div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addPersonSubmit();">保存更改</button>
              </div>
            </div>
            <!-- /.modal-content -->
      </div>
   </div> 
   
   <div class="modal fade" id="modal-default" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">×</span></button>
                <h4 class="modal-title">编辑管理员</h4>
              </div>
              <input id="editId" value="" hidden/>
              <div class="modal-body">
                <div>
                	<strong>用户名:</strong><b id="showUsername"></b>
                </div>
                <div>
                	<strong>角色:</strong>
                	<div name="tags" id="tags">
                	
                	</div>
                	<select id="selectRoles" class="role-multiple" style="width: 100%;" name="roles" multiple="multiple">
					</select>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="editPersonSubmit();">保存更改</button>
              </div>
            </div>
            <!-- /.modal-content -->
         </div>
         <!-- /.modal-dialog -->
    </div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/authority/manageAdminUser.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/authority/select2.min.js"></script>
<script type="text/javascript">
$('.datetimepicker').datetimepicker({
    format: 'yyyy-mm-dd',
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