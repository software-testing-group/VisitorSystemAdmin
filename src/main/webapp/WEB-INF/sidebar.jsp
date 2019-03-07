<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!--侧边栏-->
  <div class="sidebar-wrapper" id="accordion">
    <div class="dropdown-wrapper">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
        <span class="firstary-left">权限管理</span>
        <i class="material-icons firstary-right">expand_more_copy 2</i>
      </a>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in secondary">
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/authority/manageAdminUser" class="dropdown-item">人员管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/authority/manageRole" class="dropdown-item">角色管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/authority/manageAdminLog" class="dropdown-item">操作记录</a>
      </div>
    </div>
    <div class="dropdown-wrapper">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
        <span class="firstary-left">浏览管理</span>
        <i class="material-icons firstary-right">expand_more_copy 2</i>
      </a>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse in secondary">
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/tour/manageBatch" class="dropdown-item">批次管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/tour/historicalBatch" class="dropdown-item">历史批次</a>
      </div>
    </div>
    <div class="dropdown-wrapper">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
        <span class="firstary-left">预约管理</span>
        <i class="material-icons firstary-right">expand_more_copy 2</i>
      </a>
    </div>
    <div id="collapseFive" class="panel-collapse collapse in secondary">
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/reservation/manageReservation" class="dropdown-item">游客预约</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/officialReservation/manageOfficialReservation" class="dropdown-item">访客预约</a>
      </div>
    </div>
    
        <div class="dropdown-wrapper">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
        <span class="firstary-left">访客管理</span>
        <i class="material-icons firstary-right">expand_more_copy 2</i>
      </a>
    </div>
    <div class="dropdown-wrapper">
      <div id="collapseFour" class="panel-collapse collapse in secondary">
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/visitor/manageVisitorUser" class="dropdown-item">用户管理</a>
      </div>     
       <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/visitor/manageVisitorIdentity" class="dropdown-item">证件管理</a>
      </div>
      </div>
    </div>
    <div class="dropdown-wrapper">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
        <span class="firstary-left">导览系统管理</span>
        <i class="material-icons firstary-right">expand_more_copy 2</i>
      </a>
    </div>
    <div id="collapseThree" class="panel-collapse collapse in secondary">
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/scenery/manageAttraction" class="dropdown-item">景点管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/scenery/manageRoute" class="dropdown-item">路线管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/navigation/infrastructureRoute" class="dropdown-item">基础设施管理</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a href="${pageContext.request.contextPath}/navigation/questionRoute" class="dropdown-item">帮助问题与意见反馈</a>
      </div>
      <div class="dropdown-item-wrapper">
        <a class="dropdown-item">数据展示</a>
      </div>
    </div>
    <div class="dropdown-wrapper">
    <a class="dropdown-item" href="http://datav.aliyuncs.com/share/d4108cc6eff6bd171e8371d7ccf91aa3" target="_blank">
      <span class="firstary-left">预约进度</span>
    </a>
    </div>
    <div class="dropdown-wrapper" onclick="javascript:location.href='${pageContext.request.contextPath}/authority/extraSettings'">
      <a class="dropdown-item" data-toggle="collapse" data-parent="#accordion" href="">
        <span class="firstary-left">其余设置</span>
      </a>
    </div>
  </div>