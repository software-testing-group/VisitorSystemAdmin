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
</head>
<body>
	<%@include file="/WEB-INF/header.jsp" %>
	<%@include file="/WEB-INF/sidebar.jsp" %>
	<div class="table-wrapper">
    <div class="table-head">
      <div class="title-wrapper">
        <span class="table-head-left">访客预约管理</span>
        <span class="table-head-right">搜索与筛选</span>
      </div>
          <div class="filter-wrapper">
        <div class="close-wrapper">
          <i class="material-icons close-btn">clear</i>
        </div>
        <div class="filter-item-wrapper">
          <span class="filter-title">流水号</span>
          <input id="search" type="text" class="input-box" placeholder="请输入...." />
          <span class="filter-title">操作人</span>
          <input id="search1" type="text" class="input-box" placeholder="请输入...."/>
          <span class="filter-title">添加原因</span>
          <input id="search2" type="text" class="input-box" placeholder="请输入...."/>
          <button onclick="searchReservation(1)">搜索</button>
        </div>
    </div>
    <div class="content-table-wrapper">
      <table class="table table-responsive" id="content-table">
        <thead>
          <th>流水号</th>
          <th>开始时间</th>
          <th>结束时间</th>
          <th>状态</th>
          <th>操作人</th>
          <th>操作时间</th>
          <th>添加原因</th>
          <th>操作</th>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div class="tfoot">
        <div class="tfoot-text">
          <span id="sumReservation">每页展示10条，共1条信息</span>
        </div>
        <div class="tfoot-page">
          <span id="prev">
            <i class="material-icons" onclick="previouspage()">chevron_left</i>
          </span>
          <span id="page">1</span>/
          <span id="pageNumber">1</span>
          <span id="next">
            <i class="material-icons" onclick="nextpage()">chevron_right</i>
          </span>
          <input type="text" value="" style="height: 40px;width: 50px" placeholder="页码" id="getPage"/>
          <button style="height: 40px;width: 60px;text-align: center;line-height: 15px" onclick="searchPage()">跳转</button>
        </div>
      </div>
      <div>
          <button type="button" data-toggle="modal" data-target="#modal-add-reservation" class="btn btn-block btn-default">新增预约</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="modal-add-reservation" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">新增预约</h4>
            </div>
            <div class="modal-body">
                <form class="form-inline">
                    <div class="form-group">
                        <div class="choose">
                            <div class="input-group date form_date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input1" data-link-format="yyyy-mm-dd">
                            <span class="input-group-addon" id="first-day">
                                <i class="material-icons choose-event" id="first">event</i>
                            </span>
                                <input class="form-control clndr-show form-inline" type="text" value="" readonly placeholder="请输入开始日期" id="start" />
                            </div>
                            <input type="hidden" id="dtp_input1" value="" />
                            <span>至</span>
                            <div class="input-group date form_date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                            <span class="input-group-addon" id="last-day">
                                <i class="material-icons choose-event" id="last">event</i>
                            </span>
                                <input class="form-control clndr-show form-inline" type="text" value="" readonly placeholder="请输入结束日期" id="end" />
                            </div>
                            <input type="hidden" id="dtp_input2" value="" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="introduction">添加原因：</label><textarea name="introduction" id="introduction" cols="30" rows="2"></textarea>
                    </div>
                    <div class="parters"></div>
                    <i class="material-icons choose-event addconf">add_circle_outline</i>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addReservationSubmit()">确定预约</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-reservation-detail" tabindex="-1" role="dialog" aria-labelledby=detailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">订单详细</h4>
            </div>
            <div class="modal-body" >
                <div class="item-wrapper">
                    <span class="item-title">流水号</span>
                    <span class="item-content" id="myReservationId"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">开始日期</span>
                    <span class="item-content" id="myDateStart"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">结束日期</span>
                    <span class="item-content" id="myDateEnd"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">添加原因</span>
                    <span class="item-content" id="myIntroduction"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">人员数量</span>
                    <span class="item-content" id="myNumber"></span>
                </div>
                <div class="item-wrapper">
                    <div class="item-title item-title-lines">人员信息</div>
                    <div class="item-content-lines">
                        <table class="table table-bordered" id="identity-table">
                            <thead>
                                <th>姓名</th>
                                <th>证件号</th>
                                <th>证件类型</th>
                                <th>状态</th>
                                <th>操作</th>
                            </thead>
                            <tbody>
                                <tr></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="twoButton">
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-identity-detail" tabindex="-1" role="dialog" aria-labelledby=detailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="identityModalLabel">修改人员信息</h4>
            </div>
            <div class="modal-body" >
                <div class="item-wrapper">
                    <span class="item-title">序号</span>
                    <span class="item-content" id="theId"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">姓名</span>
                    <input type="text" class="item-content" id="theFullName" placeholder="全名/必填"></input>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">证件类型</span>
                    <select class="item-content" id="theIdentityCardType">
                        <option value="0">身份证</option>
                        <option value="1">港澳台通行证</option>
                        <option value="2">护照</option>
                    </select>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">证件号</span>
                    <input type="text" class="item-content" id="theIdentityCard" placeholder="全名/必填"></input>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="editIdentitySubmit()">确定修改</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-reservation-edit" tabindex="-1" role="dialog" aria-labelledby=detailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="reservationModalLabel">修改订单信息</h4>
            </div>
            <div class="modal-body" >
                <div class="item-wrapper">
                    <span class="item-title">流水号</span>
                    <span class="item-content" id="theRId"></span>
                </div>
                <div class="item-wrapper">
                    <span class="item-title">开始日期</span>
                    <div class="input-group date form_date_r" data-date="" data-date-format="dd MM yyyy" data-link-field="input1" data-link-format="yyyy-mm-dd">
                    <span class="input-group-addon" id="first-day-r">
                        <i class="material-icons choose-event" id="first-r">event</i>
                    </span>
                        <input class="form-control clndr-show form-inline" type="text" value="" readonly placeholder="请输入开始日期" id="rStart" />
                    </div>
                    <input type="hidden" id="input1" value="" />
                </div>
                <div class="item-wrapper">
                    <span class="item-title">结束日期</span>
                    <div class="input-group date form_date_r" data-date="" data-date-format="dd MM yyyy" data-link-field="input2" data-link-format="yyyy-mm-dd">
                    <span class="input-group-addon" id="last-day-r">
                        <i class="material-icons choose-event" id="last-r">event</i>
                    </span>
                        <input class="form-control clndr-show form-inline" type="text" value="" readonly placeholder="请输入结束日期" id="rEnd" />
                    </div>
                    <input type="hidden" id="input2" value="" />
                </div>
                <div class="item-wrapper">
                    <span class="item-title">添加原因</span>
                    <textarea id="rIntroduction" cols="30" rows="2"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="editReservationSubmit()">确定修改</button>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/table.js"></script>
<script type="text/javascript">
var pageNum=0
var sumReservation=0
$(function(){
    searchReservation(1);  //加载页面
    addParter();
    $('.table-head-right1').on('click', function() {
        $('.title-wrapper1').hide();
        $('.filter-wrapper1').show();
    });
    $('.close-btn1').on('click', function() {
        $('.title-wrapper1').show();
        $('.filter-wrapper1').hide();
    });
})
$('.form_date').datetimepicker({
    format: 'yyyy年mm月dd日',
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 0,
    startView: 2,
    minView: 2,
    forceParse: 0
}).on('changeDate',function(ev){
    if($("#start").val() && $("#end").val())
    {
        if($("#start").val() > $("#end").val())
        {
            alert("开始时间不能大于结束时间");
            $("#start").val("");
            $("#dtp_input1").val("");
        }
    }
});
$('.form_date_r').datetimepicker({
    format: 'yyyy年mm月dd日',
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 0,
    startView: 2,
    minView: 2,
    forceParse: 0
}).on('changeDate',function(ev){
    if($("#rStart").val() && $("#rEnd").val())
    {
        if($("#rStart").val() > $("#rEnd").val())
        {
            alert("开始时间不能大于结束时间");
            $("#rStart").val("");
            $("#input1").val("");
        }
    }
});
function getSumReservation(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var reservationId=$("#search").val();
    var adminUsername=$("#search1").val();
    var introduction=$("#search2").val();
    $.ajax({
        url:"${pageContext.request.contextPath}/officialReservation/getSumReservations",
        type:"POST",
        async:false,
        data:{reservationId:reservationId,adminUsername:adminUsername,introduction:introduction},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            sumReservation=data;
            $("#sumReservation").text("每页展示10条，共"+sumReservation+"条信息");
            pageNum = Math.ceil(sumReservation/10);
            $("#pageNumber").text(pageNum);
            console.log(pageNum);
        },error:function(){
            alert("数据加载错误");
        }

    });
}
function searchReservation(page)
{
    getSumReservation();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var reservationId=$("#search").val();
    var adminUsername=$("#search1").val();
    var introduction=$("#search2").val();
    $.ajax({
        url:"${pageContext.request.contextPath}/officialReservation/selectReservations",
        type:"POST",
        data:{reservationId:reservationId,adminUsername:adminUsername,introduction:introduction,page:page},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data) {
            $("#page").text(page);
            $("#content-table tbody").html("");
            //放入数据
            for (var i in data) {
                var reservation = data[i];
                if(reservation.isDeleted == 1 ){
                    var status = "被取消";
                }else if(reservation.isFinished == 1){
                    var status = "已完成";
                }else if(reservation.isDeleted == 0 && reservation.isFinished == 0){
                    var status = "正常";
                }else{
                    var status = "未知";
                }
                $("#content-table tbody").append('<tr role="row">'
                    + '<td>' + reservation.id + '</td>'
                    + '<td>' + reservation.dateStart + '</td>'
                    + '<td>' + reservation.dateEnd + '</td>'
                    + '<td>' + status + '</td>'
                    + '<td>' + reservation.adminUser.username + '</td>'
                    + '<td>' + reservation.updateTime + '</td>'
                    + '<td>' + reservation.introduction + '</td>'
                    + '<td>' + '<a  data-toggle="modal" data-target="#modal-reservation-detail" onclick="javascript:reservationDetail(' + reservation.id + ');">' + '详情' + '</a>'
                    + '</td>'
                    + '<td>' + '<a  data-toggle="modal" data-target="#modal-reservation-edit" onclick="javascript:editReservation(' + reservation.id+',\''+reservation.dateStart+'\',\''+reservation.dateEnd+'\',\''+ reservation.introduction + '\');">' + '编辑' + '</a>'
                    + '</td>'
                    + '<td>' + '<a  href="javascript:cancelReservation(' + reservation.id+','+reservation.isDeleted +  ');">' + '取消订单' + '</a>'
                    + '</td>'
                    + '</tr>'
                );
            }
        },error:function(){
            alert("返回错误");
        }
    });
}
function previouspage() {
    var page = $("#page").text();
    page = page - 1;
    console.log(page);
    if (page < 1) {
        alert("已是第一页");
    } else {
        $("#page").text(page);
        searchReservation(page);
    }
}
function nextpage() {
    var page = $("#page").text();
    page = page - 0 + 1;
    if (page > pageNum) {
        alert("已是最后一页");
    } else {
        $("#page").text(page);
        searchReservation(page);
    }
}
function searchPage()
{
    getSumReservation();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var reservationId=$("#search").val();
    var adminUsername=$("#search1").val();
    var introduction=$("#search2").val();
    var page=$('#getPage').val();
    if(page>pageNum || page<1){
        alert("页码错误。");
        return;
    }
    $.ajax({
        url:"${pageContext.request.contextPath}/officialReservation/selectReservations",
        type:"POST",
        data:{reservationId:reservationId,adminUsername:adminUsername,introduction:introduction,page:page},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data) {
            $("#page").text(page);
            $("#content-table tbody").html("");
            //放入数据
            for (var i in data) {
                var reservation = data[i];
                if(reservation.isDeleted == 1 ){
                    var status = "被取消";
                }else if(reservation.isFinished == 1){
                    var status = "已完成";
                }else if(reservation.isDeleted == 0 && reservation.isFinished == 0){
                    var status = "正常";
                }else{
                    var status = "未知";
                }
                $("#content-table tbody").append('<tr role="row">'
                    + '<td>' + reservation.id + '</td>'
                    + '<td>' + reservation.dateStart + '</td>'
                    + '<td>' + reservation.dateEnd + '</td>'
                    + '<td>' + status + '</td>'
                    + '<td>' + reservation.adminUser.username + '</td>'
                    + '<td>' + reservation.updateTime + '</td>'
                    + '<td>' + reservation.introduction + '</td>'
                    + '<td>' + '<a  data-toggle="modal" data-target="#modal-reservation-detail" onclick="javascript:reservationDetail(' + reservation.id + ');">' + '详情' + '</a>'
                    + '</td>'
                    + '<td>' + '<a  data-toggle="modal" data-target="#modal-reservation-edit" onclick="javascript:editReservation(' + reservation.id+',\''+reservation.dateStart+'\',\''+reservation.dateEnd+'\',\''+ reservation.introduction + '\');">' + '编辑' + '</a>'
                    + '</td>'
                    + '<td>' + '<a  href="javascript:cancelReservation(' + reservation.id+','+reservation.isDeleted +  ');">' + '取消订单' + '</a>'
                    + '</td>'
                    + '</tr>'
                );
            }
        },error:function(){
            alert("返回错误");
        }
    });
}
function reservationDetail(id) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url:"${pageContext.request.contextPath}/officialReservation/getReservationDetail",
        type: "POST",
        data:{id:id},
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success:function(data){
            $("#identity-table tbody").html("");
            var reservation = data;
            $("#myReservationId").text(reservation.id);
            $("#myDateStart").text(reservation.dateStart);
            $("#myDateEnd").text(reservation.dateEnd);
            $("#myIntroduction").text(reservation.introduction);
            var bindList = reservation.officialVisitorBindingIdentityList;
            var deletedNumber = 0;
            var finishedNumber = 0;
            var simpleNumber = 0;
            var long = bindList.length;
            for(var i in bindList){
                var bind = bindList[i];
                var identity = bind.officialVisitorIdentity;
                if(bind.isDeleted == 1 ){
                    var status = "被取消";
                    deletedNumber = deletedNumber + 1;
                }else if(bind.isFinished == 1){
                    var status = "已完成";
                    finishedNumber = finishedNumber + 1;
                }else if(bind.isDeleted == 0 && bind.isFinished == 0){
                    var status = "正常";
                    simpleNumber = simpleNumber + 1;
                }else{
                    var status = "未知";
                }
                if(identity.identityCardType == 0){
                    var type = "身份证";
                }else if(identity.identityCardType == 1){
                    var type = "港澳台证件";
                }else if(identity.identityCardType == 2){
                    var type = "护照";
                }
                $("#identity-table tbody").append('<tr>'
                    + '<td>' + identity.fullName + '</td>'
                    + '<td>' + identity.identityCard + '</td>'
                    + '<td>' + type + '</td>'
                    + '<td>' + status + '</td>'
                    + '<td>' + '<a  data-toggle="modal" data-target="#modal-identity-detail" onclick="javascript:editIdentity('+identity.id+',\''+ identity.fullName +'\',\''+identity.identityCard+'\','+identity.identityCardType+')">' + '修改信息' + '</a>'
                    + ' | '+ '<a  href="javascript:cancelIdentity(' + reservation.id+','+identity.id +','+bind.isDeleted+ ');">' + '取消预约' + '</a>'
                    + '</td>'
                    + '</tr>'
                );
            }
            var number = '共'+long+'人(正常：'+simpleNumber+'人 已取消：'+deletedNumber+'人 已完成：'+finishedNumber+'人)';
            $("#myNumber").text(number);
            $("#twoButton").html("");
            $("#twoButton").append('<button type="button"  class="btn btn-danger" data-dismiss="modal" onclick="exportGraphCSV()">'
                +'导出'
                +'</button>'
                +'<button type="button"  class="btn btn-primary"  data-dismiss="modal">'
                +'确认'
                +'</button>'
                +'<button type="button" class="btn btn-default pull-left" data-dismiss="modal">'
                +'取消'
                +'</button>'
            );
        },
        error:function(){
            alert("返回错误");
        }
    });
}
function editIdentity(id,name,card,cardType){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $("#theId").text(id);
    $("#theFullName").val(name);
    $("#theIdentityCard").val(card);
    $("#theIdentityCardType").val(cardType);
}
function editIdentitySubmit(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var id = $("#theId").text();
    var fullName = $("#theFullName").val();
    var identityCard = $("#theIdentityCard").val();
    var identityCardType = $("#theIdentityCardType").val();
    if(fullName == null || identityCard == null || fullName == '' || identityCard == ''){
        alert("请完善人员信息");
        return;
    }
    $.ajax({
        url : "${pageContext.request.contextPath}/officialReservation/editIdentity",
        type : "POST",
        data : {
            id : id,fullName:fullName,identityCard:identityCard,identityCardType:identityCardType
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            if (data) {
                alert("操作成功");
                var reservationId = $("#myReservationId").text();
                reservationDetail(reservationId);
                $("#modal-identity-detail").modal("hide");
            } else
                alert("操作失败");
        },
        error : function() {
            alert("错误");
        }
    });

}
function cancelIdentity(reservationId,identityId,isDeleted){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    if(isDeleted != 0){
        alert("该人员的预约已经被取消，无法重复取消。");
        return;
    }
    if(confirm("是否取消该人员的预约")==false){
        return;
    }
    $.ajax({
        url : "${pageContext.request.contextPath}/officialReservation/deleteVisitorOfReservation",
        type : "POST",
        data : {
            reservationId : reservationId,identityId : identityId
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            if (data) {
                alert("操作成功");
                reservationDetail(reservationId);
            } else
                alert("操作失败");
        },
        error : function() {
            alert("错误");
        }
    });
}
function exportGraphCSV(){

    if (!!window.ActiveXObject || "ActiveXObject" in window){
        alert("IE9及以下浏览器不支持，请使用Chrome或Firefox浏览器");
        return false;
    }

    var str = "全名,证件号,证件类型,状态\n";

    $("#identity-table tr").each(function(){
        $(this).children("td").each(function(i){

            if(i ==1)
                str += $(this).text() +"\t"+ ",";
            else if(i<4)
                str += $(this).text() + ",";
        });
        str = str.substring(0,str.length - 1);
        str += '\n';
    });

    //判断浏览器
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    if (userAgent.indexOf("MSIE") > -1) {
        str = 'sep=,\r\n' + str;
    } //判断是否IE浏览器
    else
    {
        str =  encodeURIComponent(str);
    }

    try{
        var downloadLink = document.createElement("a");
        var uri = 'data:text/csv;charset=utf-8,\uFEFF' + str;
        downloadLink.href = uri;
        var id = $("#myReservationId").text();
        downloadLink.download = "订单"+id+"的人员信息表.csv";
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    }catch(e){
        alert("文件太大，下载失败");
    }
}
function editReservation(id,rStart,rEnd,rIntroduction){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $("#theRId").text(id);
    $("#rStart").val(rStart.replace(/(.+?)\-(.+?)\-(.+)/,"$1年$2月$3日"));
    $("#input1").val(rStart);
    $("#rEnd").val(rEnd.replace(/(.+?)\-(.+?)\-(.+)/,"$1年$2月$3日"));
    $("#input2").val(rEnd);
    $("#rIntroduction").val(rIntroduction);

}
function editReservationSubmit(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var id = $("#theRId").text();
    var rStart = $("#input1").val();
    var rEnd = $("#input2").val();
    var rIntroduction = $("#rIntroduction").val();
    if(rStart == null || rEnd == null || rStart == '' || rEnd == '' || rIntroduction == null || rIntroduction == null){
        alert("请完善订单信息");
        return;
    }
    $.ajax({
        url : "${pageContext.request.contextPath}/officialReservation/editReservation",
        type : "POST",
        data : {
            id : id,dateStart:rStart,dateEnd:rEnd,introduction:rIntroduction
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            if (data) {
                alert("操作成功");
                searchReservation($("#page").text());
                $("#modal-reservation-edit").modal("hide");
            } else
                alert("操作失败");
        },
        error : function() {
            alert("错误");
        }
    });
}
function cancelReservation(id,isDeleted) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    if(isDeleted != 0){
        alert("订单已经被取消，无法重复取消。");
        return;
    }
    if(confirm("是否取消订单")==false){
        return;
    }
    $.ajax({
        url : "${pageContext.request.contextPath}/officialReservation/deleteReservation",
        type : "POST",
        data : {
            id : id
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            if (data) {
                alert("操作成功");
                searchReservation($("#page").text());
            } else
                alert("操作失败");
        },
        error : function() {
            alert("错误");
        }
    });
}
function addParter(){
    $(".addconf").on('click', function(){
        var partersNum = $(".parters").length;
        var n = '<div class="parter"><i class="material-icons choose-event removeconf">remove_circle_outline</i><span>全名</span><input type="text" class="form-control" id="fullName" placeholder="全名/必填"><span>身份证类型</span><select class="form-control" id="inputIdentityCardType"><option value="0">身份证</option><option value="1">港澳台通行证</option><option value="2">护照</option></select><span>证件号</span><input type="text" class="form-control" id="identityCard" placeholder="证件号/必填"></div>';
        $(".parters").append(n);
        $(".removeconf").on('click', function() {
            $(this).parent().remove();
        });
    });
}
function addReservationSubmit(){
    partersInfo = [];
    for(var k = 0; k < $('.parters').children('.parter').length; k++) {
        var t = $(".parters").children('.parter').eq(k);
        if(t.children("input").eq(0).val() !="" && t.children("input").eq(1).val() !="" && t.children("input").eq(0).val() != null && t.children("input").eq(1).val() != null){
            var fullName = t.children("input").eq(0).val();
            var identityCard = t.children("input").eq(1).val();
            var identityCardType = t.children("select").val();
            var parter = {"fullName":fullName,"identityCard":identityCard,"identityCardType":identityCardType};
            partersInfo.push(parter);
        }else {
            alert("人员姓名或证件号不能为空");
            return;
        }
    }
    if(partersInfo.length<1){
        alert("请输入人员信息");
        return;
    }
    var dateStart = $("#dtp_input1").val();
    var dateEnd = $("#dtp_input2").val();
    var introduction = $("#introduction").val();
    if(dateStart == "" || dateStart == null || dateEnd == "" || dateEnd == null){
        alert("开始日期或结束日期不能为空");
        return;
    }
    if(introduction == "" || introduction == null){
        alert("添加原因不能为空");
        return;
    }
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url : "${pageContext.request.contextPath}/officialReservation/addReservation",
        type : "POST",
        data : {dateStart:dateStart,dateEnd:dateEnd,introduction:introduction,identityList:JSON.stringify(partersInfo)},
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            if (data) {
                alert("操作成功");
                $("#modal-add-reservation").modal("hide");
                searchReservation(1);
            } else
                alert("操作失败");
        },
        error : function() {
            alert("错误");
        }
    });
}
</script>
</body>
</html>
