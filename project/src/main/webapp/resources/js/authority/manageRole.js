/**
 * @author wyp
 */
$(function(){
	var page = $("#page").text();
	loadPage(page);  //加载页面
	loadAllAction();
})
$('#myModal').on('hide.bs.modal', function () {
	$("#role-name").attr("value","");
	$("#description").attr("value","");
	$("#editRoleId").attr("value","");
	loadAllAction();
});

$("#next").click(function(){
	var page = parseInt($("#page").text()) + 1;
	$("#page").text(page);
	loadPage(page);
});

$("#prev").click(function(){
	var page = parseInt($("#page").text()) - 1;
	
	if(page < 1)
	{
		alert("已到最前页");
		page = 1;
	}
	$("#page").text(page);
	loadPage(page);
});

function loadPage(page)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/getRoleList",
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
			}
			else{
				
				$("#content-table tbody").html("");
				
				//放入数据
				for(var i in data)
				{
					var role = data[i];
					
					var str_u = '<tr role="row">'
						+   '<td>' + role.roleName + '</td>'
						+   '<td>' + role.roleDescription + '</td>'
						+   '<td>' + role.updateTime + '</td>'
						+   '<td>' + '<a href="javascript:loadRole(' + role.id + ')">编辑    | '
						+ '<a href="javascript:deleteRole(' + role.id + ')">删除</a></td>'
						+   '</tr>';
					var str_su = '<tr role="row">'
						+   '<td>' + role.roleName + '</td>'
						+   '<td>' + role.roleDescription + '</td>'
						+   '<td>' + role.updateTime + '</td>'
						+   '<td></td>'
						+   '</tr>';
					
					if(role.roleName == "admin")
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
function loadAllAction()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/getAdminActionListAll",
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			$("#myModal .modal-body").html("");
			
			var length = data.length;
			for(var i in data)
			{
				var action = data[i];
				
				if(action.parentId == 0)
				{
					$("#myModal .modal-body").append('<div class="radios-wapper1">'
							+ '<div class="checkbox checkbox-info checkbox-circle">'
							+ '<input id="action_' + action.id + '" class="styled" type="checkbox">'
							+ '<label for="action_' + action.id + '">' + action.actionName + '</label>'
							+ '</div></div>');
				}else
				{
					if($("#action_" + action.parentId).parent().parent().hasClass("radios"))
					{
						$("#action_" + action.parentId).parent().parent().append('<div class="radiochild">'
							
							+ '<div class="checkbox checkbox-info checkbox-circle">'
							+ '<input source="action_' + action.parentId + '" id="action_' + action.id + '" class="styled" type="checkbox">'
							+ '<label for="action_' + action.id + '" >' + action.actionName + '</label>'
							+ '</div></div>');	
					}else
					{
						$("#action_" + action.parentId).parent().parent().append('<div class="radios">'
								
								+ '<div class="checkbox checkbox-info checkbox-circle">'
								+ '<input source="action_' + action.parentId + '" id="action_' + action.id + '" class="styled" type="checkbox">'
								+ '<label for="action_' + action.id + '" >' + action.actionName + '</label>'
								+ '</div></div>');		
					}
				}
				
			}
			
		},error:function(){
			alert("权限加载失败");
		}
	});
}
function deleteRole(id)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/deleteRole",
		type:"POST",
		data:{roleId:parseInt(id)},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			alert(data);
			if(data == "删除成功")
			{
				var page = $("#page").text();
				loadPage(page);
			}
		},
		error:function(){
			alert("数据加载错误");
		}
		
	});
	
}

function addRoleCheck()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var roleName = $("#role-name").val();
	
	$.ajax({
		url:"/authority/checkRoleName",
		type:"POST",
		data:{roleName:roleName},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data)
			{
				alert("已有角色为该英文名（注：此信息只作提示作用，不是强制限制！）");
			}
		},
		error:function(){
			alert("数据加载错误");
		}
		
	});
}

function addRole()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var roleName = $("#role-name").val();
	var description = $("#description").val();
	var actionList = "";
	
	if(roleName == "" || description == "")
	{
		alert("请正确填写信息");
		return ;
	}
	
	$("input[type=checkbox][checked=checked]").each(function(){
		var id = $(this).attr("id").split("_")[1];
		actionList += id + ",";
	});
	
	if(actionList == "")
	{
		alert("请至少勾选一项权限");
		return ;
	}
	
	actionList = actionList.substring(0,actionList.length-1);
	
	$.ajax({
		url:"/authority/addRole",
		type:"POST",
		data:{roleName:roleName,description:description,actionList:actionList},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data == true)
			{
				alert("新增成功");
				$("#myModal").modal("hide");
				var page = $("#page").text();
				loadPage(page);
			}
		},
		error:function(){
			alert("数据加载错误");
		}
		
	});
	
}

function loadRole(id)
{
	$("#myModal").modal("show");
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url:"/authority/getRoleByRoleId",
		type:"POST",
		data:{roleId:parseInt(id)},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			$("#editRoleId").attr("value",data.id);
			$("#role-name").attr("value",data.roleName);
			$("#description").attr("value",data.roleDescription);
			
			var n = "<span class='get'><i class='fa fa-check'></i></span>";
			for(var i in data.actionList)
			{
				$("#action_" + data.actionList[i].id).attr("checked",true);
				$("#action_" + data.actionList[i].id).next().append(n);
			}
		},
		error:function(){
			alert("数据加载错误");
		}
		
	});
}
function editRole()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var roleName = $("#role-name").val();
	var description = $("#description").val();
	var actionList = "";
	var editRoleId = $("#editRoleId").val();
	
	if(roleName == "" || description == "")
	{
		alert("请正确填写信息");
		return ;
	}
	
	$("input[type=checkbox][checked=checked]").each(function(){
		var id = $(this).attr("id").split("_")[1];
		actionList += id + ",";
	});
	
	if(actionList == "")
	{
		alert("请至少勾选一项权限");
		return ;
	}
	
	actionList = actionList.substring(0,actionList.length-1);
	
	$.ajax({
		url:"/authority/editRole",
		type:"POST",
		data:{roleId:parseInt(editRoleId),roleName:roleName,roleDescription:description,list:actionList},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			alert("修改成功");
			$("#myModal").modal("hide");
			var page = $("#page").text();
			loadPage(page);
		},
		error:function(){
			alert("数据加载错误");
		}
	});
	
}

function submit()
{
	if($("#editRoleId").val() != "")
		editRole();
	else
		addRole();
}

//导出CSV
function exportGraphCSV()
{
    if (!!window.ActiveXObject || "ActiveXObject" in window){
    	alert("IE9及以下浏览器不支持，请使用Chrome或Firefox浏览器");
    	return false;
    }
	
	var str = "角色名,角色描述,上次编辑时间\n";
	
	$("#content-table tr").each(function(){
		$(this).children("td").each(function(i){
			
			if(i < 3)
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