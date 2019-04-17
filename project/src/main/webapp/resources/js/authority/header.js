/**
 * @author
 */

function checkOldPassword()
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var oldPassword = $("#oldPassword").val();
    var check = false;
	
	$.ajax({
		url:"/authority/checkOldPassword",
		type:"POST",
		async:false,
		data:{oldPassword:oldPassword},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data)
				check = true;
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});	
	
	if(check)
	{
		$("#oldPassword").parent().removeClass("has-error");
		$("#oldPassword").parent().addClass("has-success");
	}
	else
	{
		$("#oldPassword").parent().removeClass("has-success");
		$("#oldPassword").parent().addClass("has-error");
	}
	
	return check;
}

function checkNewPassword()
{
	var newPassword = $("#newPassword").val();
	var newPassword2 = $("#newPasswordConfirm").val();
	
	if(newPassword == newPassword2)
	{
		$("#newPassword").parent().removeClass("has-error");
		$("#newPasswordConfirm").parent().removeClass("has-error");
		$("#newPassword").parent().addClass("has-success");
		$("#newPasswordConfirm").parent().addClass("has-success");
		return true;
	}
	else
	{
		$("#newPassword").parent().removeClass("has-success");
		$("#newPasswordConfirm").parent().removeClass("has-success");
		$("#newPassword").parent().addClass("has-error");
		$("#newPasswordConfirm").parent().addClass("has-error");
		return false;
	}
}

function editPasswordSubmit()
{
   if(!(checkOldPassword() && checkNewPassword()))
   {
	   alert("请正确填写信息!");
	   return ;
   }
   
   var token = $("meta[name='_csrf']").attr("content");  
   var header = $("meta[name='_csrf_header']").attr("content");

   var newPassword = $("#newPassword").val();
   
	$.ajax({
		url:"/authority/editPassword",
		type:"POST",
		async:false,
		data:{password:newPassword},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			
			if(data)
			{
			   alert("修改成功，请重新登录!");
			   $("#logout").click();
			}else
			   alert("修改失败!");
			
		},error:function(){
			alert("数据加载错误");
		}
		
	});
   
}