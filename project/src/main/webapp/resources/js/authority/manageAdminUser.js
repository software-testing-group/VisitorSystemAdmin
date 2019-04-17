/**
 * @author WYP
 */
var totalRoleList = {};

var pageNum=0;
var sumAdminUser=0;

$(function(){
	var page = $("#page").text();
	loadPageFilter(page);  //加载页面
	loadAllRole();
})


function getSumAdminUser()
{
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var filter_name = $("#filter-name").val();
    var staff_id = $("#filter-id").val();
    var role = $("#filter-selector").val();
    var start = $("#filter-tstart").val();
    var end = $("#filter-tend").val();

    console.log(filter_name + " " + staff_id + " " + role + " " + start + " " + end );

    $.ajax({
        url:"/authority/getSumAdminUser",
        type:"POST",
        async:false,
        data:{username:filter_name,staffId:staff_id ,roleId:role,start:start,end:end},
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success:function(data){

            sumAdminUser=data;
            $("#sumAdminUser").text("每页展示10条，共"+sumAdminUser+"条信息");
            pageNum = Math.ceil(sumAdminUser/10);
            $("#pageNumber").text(pageNum);
            console.log(pageNum);

        },error:function(){
            alert("数据加载错误");
        }

    });
}


$("#next").click(function(){
    var page = $("#page").text();
    page = page - 0 + 1;
    if (page > pageNum) {
        alert("已是最后一页");
    } else {
        $("#page").text(page);
       loadPageFilter(page);
    }
});

$("#prev").click(function(){

    var page = $("#page").text();
    page = page - 1;
    console.log(page);
    if (page < 1) {
        alert("已是第一页");
    } else {
        $("#page").text(page);
        loadPageFilter(page);
    }

});

$(".close-btn").click(function(){
    $("#filter-name").val("");
    $("#filter-id").val("");
    $("#filter-selector").val("");
    $("#filter-tstart").val("");
    $("#filter-tend").val("");
	$("#page").text(1);
	loadPageFilter(1);
});

function loadAllRole()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		url:"/authority/getRoleListAll",
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			totalRoleList = {};
		    for(var i in data)
			{	
		    	//角色列表{roleName:id}放入totalRoleList
		    	var name = data[i].roleName;
		    	var id = data[i].id;
		    	var description = data[i].roleDescription
		    	totalRoleList[name] = id;
		    	
		    	$(".filter-selector").append('<option value="' + id + '">' + name + '</option>');
			}
		},
		error:function(){
			alert("数据加载错误");
		}
	});
}

function loadPage(page)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/getPersonList",
		type:"POST",
		data:{page:page},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data.length == 0)
			{
				if(parseInt($("#page").text()) == 1)
					alert("没有数据！");
				else
				{
					alert("已是最后一页!");
					var page = parseInt($("#page").text()) - 1;
					$("#page").text(page);
				}
			}else
			{
				$("#content-table tbody").html("");
				//放入数据
				for(var i in data) 
				{
					var person = data[i];
					//存入角色字符串
					var roleListString = ""; 
					//存入角色id
					var roleList = [];  
					//循环得到该人的所有角色
					for(var j in person.roleList)    
					{
						roleList.push(person.roleList[j].id);
						if(roleListString == "")
							roleListString = person.roleList[j].roleName;
						else
							roleListString += "," + person.roleList[j].roleName;
					}
					
					var str_u = '<tr role="row">'
						+   '<td>' + person.username + '</td>'
						+   '<td>' + person.staffId + '</td>'
						+   '<td>' + person.lastLogin + '</td>'
						+   '<td>' + person.lastIp + '</td>'
						+   '<td>' + roleListString + '</td>'
						+   '<td>' + person.isBanned + '</td>'
						+   '<td>' + '<a href="javascript:editPerson(' + person.id + ',\'' + person.username + '\',' + JSON.stringify(roleList) + ')">编辑' 
						+' | ' + '<a href="javascript:banPerson(' + person.id.toString() + ',' + person.isBanned + ');">' 
						+ judgeBanned(person.isBanned) + '</a>' + '</td>'
						+   '</tr>';
					var str_su = '<tr role="row">'
						+   '<td>' + person.username + '</td>'
						+   '<td>' + person.staffId + '</td>'
						+   '<td>' + person.lastLogin + '</td>'
						+   '<td>' + person.lastIp + '</td>'
						+   '<td>' + roleListString + '</td>'
						+   '<td>' + person.isBanned + '</td>'
						+   '<td></td>'
						+   '</tr>';
					
					if(roleListString.indexOf("admin") != -1 )
						$("#content-table tbody").append(str_su);
					else
						$("#content-table tbody").append(str_u);
				}	
			}
		},error:function(){
			alert("数据加载错误");
		}
		
	});
	
}

function editPerson(id,name,list)
{
	var personId = parseInt(id);
	var username = name;
	//存放已有角色的id
	var existRoleList = list ;
	
	//清除上次调用的遗留
	$("#selectRoles").html("");
	$("#showUsername").html("");
	$("#editId").attr("value",personId);

	$('.role-multiple').select2();
	
	for(item in totalRoleList)
	{
		var roleName = item;
		var id = totalRoleList[item];
		
		$("#selectRoles").append('<option value=' + id + '>' + roleName + '</option>');
	}
	
	$('.role-multiple').val(existRoleList);
	$('.role-multiple').trigger('change');
	
	$("#showUsername").html(username);
	$("#modal-default").modal("show");
}

function editPersonSubmit()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var roleList = $(".role-multiple").val();
	var editAdminId = parseInt($("#editId").val());
	var editRoleList = "";
	
	if(roleList.length <= 0)
	{
		alert("请选择角色");
		return;
	}
	
	for(var i in roleList)
	{
		editRoleList += roleList[i] + ",";
	}
	
	editRoleList = editRoleList.substring(0,editRoleList.length - 1);
	

	$.ajax({
		url:"/authority/editAdminUserRole",
		type:"POST",
		data:{adminUserId:parseInt(editAdminId),list:editRoleList}, 
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("修改成功");
				var page = parseInt($("#page").text());
				loadPage(page);
			}
			else
				alert("修改失败");
		},
		error:function(){
			alert("数据加载错误");
		}
	});
	
	$("#modal-default").modal("hide");
}

function banPerson(id,ban)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/banAdminUser",
		type:"POST",
		data:{id:parseInt(id),isBanned:1 - parseInt(ban)},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
			{
				alert("操作成功");
				var page = parseInt($("#page").text());
				loadPage(page);
			}
			else
				alert("操作失败");
		},
		error:function(){
			alert("禁用错误");
		}
	});
	
}

function addPersonSubmit()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var username = $("#username").val();
	var password = $("#password").val();
	var staffId = $("#staffId").val();
	
	//用户名密码校验
	var patrn=/^([a-z]|[A-Z]|[0-9]|[\.@#$%^&*!]){6,30}$/;
	if (!patrn.exec(username)){
		alert("用户名     只能输入字母/数字/./@/#/$/%/^/&/*/! 长度(6,30)!");
		return;
	}
	if(!patrn.exec(password)){
		alert("密码     只能输入字母/数字/./@/#/$/%/^/&/*/! 长度(6,30)!");
		return;
	}
	
	patrn = /^([0-9]){0,50}$/;
	if(!patrn.exec(staffId))
	{
		alert("学工号格式有误");
		return;
	}
	
	$.ajax({
		
		url:"/authority/register",
		type:"POST",
		data:{username:username,password:password,staffId:staffId},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){		
			
			if(data == "新增成功")
			{
				$("#modal-add-manager").modal("hide");
				var page = parseInt($("#page").text());
				loadPage(page);
			}
			else
				alert(data);
		}
		
	});
}

function judgeBanned(ban)
{
	if(ban > 0) return "启用"; else return "禁用";	
}

function isExist(item,list)
{
    var test = false;
    for(var i in list)
    {
    	if(list[i] == item)
    		test = true;
    }
    return test;
}

function filter()
{
	$("#page").text("1");
	loadPageFilter();
}

function loadPageFilter(page)
{

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

	$("#page").text(page);
    getSumAdminUser();
    if(sumAdminUser==0)
    {
        $("#content-table tbody").html("");
        $("#pageNumber").text(1);
    }

	var filter_name = $("#filter-name").val();
    var staff_id = $("#filter-id").val();
	var role = $("#filter-selector").val();
	var start = $("#filter-tstart").val();
	var end = $("#filter-tend").val();

	console.log(filter_name + " " + staff_id + " " + role + " " + start + " " + end );

	
	$.ajax({
		url:"/authority/getPersonListFilter",
		type:"POST",
		data:{page:page,username:filter_name,staffId:staff_id ,roleId:role,start:start,end:end},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){

            if(sumAdminUser==0)
            {
                alert("不存在满足条件的结果");
            }
            else {

                $("#content-table tbody").html("");
                //放入数据
                for (var i in data) {
                    var person = data[i];
                    //存入角色字符串
                    var roleListString = "";
                    //存入角色id
                    var roleList = [];
                    //循环得到该人的所有角色
                    for (var j in person.roleList) {
                        roleList.push(person.roleList[j].id);
                        if (roleListString == "")
                            roleListString = person.roleList[j].roleName;
                        else
                            roleListString += "," + person.roleList[j].roleName;
                    }

                    $("#content-table tbody").append('<tr role="row">'
                        + '<td>' + person.username + '</td>'
                        + '<td>' + person.staffId + '</td>'
                        + '<td>' + person.lastLogin + '</td>'
                        + '<td>' + person.lastIp + '</td>'
                        + '<td>' + roleListString + '</td>'
                        + '<td>' + person.isBanned + '</td>'
                        + '<td>' + '<a href="javascript:editPerson(' + person.id + ',\'' + person.username + '\',' + JSON.stringify(roleList) + ')">编辑'
                        + ' | ' + '<a href="javascript:banPerson(' + person.id.toString() + ',' + person.isBanned + ');">'
                        + judgeBanned(person.isBanned) + '</a>' + '</td>'
                        + '</tr>'
                    );
                }
            }

		},error:function(){
			alert("数据加载错误");
		}
		
	});
}

//导出
function exportGraphCSV()
{
    if (!!window.ActiveXObject || "ActiveXObject" in window){
    	alert("IE9及以下浏览器不支持，请使用Chrome或Firefox浏览器");
    	return false;
    }
	
	var str = "用户名,学工号,上次登录时间,上次登录ip,角色,是否禁用\n";
	
	$("#content-table tr").each(function(){
		$(this).children("td").each(function(i){
			
			if(i < 6)
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
		downloadLink.download = "export.csv";
		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
    }catch(e){
    	alert("文件太大，下载失败");
    }
}