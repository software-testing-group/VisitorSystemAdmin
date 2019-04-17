/**
 * @author wyp
 */
$(function(){
	loadAllSettings();  //加载页面
})
function loadAllSettings()
{
	
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		url:"/authority/getSettings",
		type:"POST",
		data:{},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
		  $("#content-table tbody").html("");
				
		  for(var i in data)
		  {
			 var item = data[i];

			 $("#content-table tbody").append('<tr><td><div class="form-group"><label class="control-label col-sm-2">'
				      + item.settingName + '</label></td><td><input id="setting_' + item.id + '" size="26" type="text" placeholder="' + item.settingCode + '" class="input-box" value="' + item.value + '" />'
				      + '<button type="button" class="setting-button" onclick="editSetting(' + item.id + ')">设定</button></div></td></tr>'
					);
		   }	
		},
		error:function(){
			alert("数据加载错误");
		}
		
	});
}

function editSetting(id)
{
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		url:"/authority/editSettings",
		type:"POST",
		data:{id:id,value:$("#setting_" + id).val()},
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success:function(data){
			if(data)
				alert("修改成功");
			else
				alert("修改失败");
		},
		error:function(){
			alert("修改失败");
		}
	});
}