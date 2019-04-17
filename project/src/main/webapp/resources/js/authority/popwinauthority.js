$(document).on("change", "input[type=checkbox]", function(e) {
	
	if($(this).attr("checked"))
	{
		$(this).removeAttr("checked");
		$(this).next().children(".get").remove();
	}else
	{
		$(this).attr("checked",true);
		var n = "<span class='get'><i class='fa fa-check'></i></span>";
		$(this).next().append(n);
	}
	
	if($(this).attr("source") !== typeof undefined)
	{
		
		if($(this).attr("checked"))
		{
			$("#" + $(this).attr("source")).attr("checked",true);
			var n = "<span class='get'><i class='fa fa-check'></i></span>";
			$("#" + $(this).attr("source")).next().append(n);
			
			if($("#" + $(this).attr("source")).attr("source") !== typeof undefined)
			{
				$("#" + $("#" + $(this).attr("source")).attr("source")).attr("checked",true);
				$("#" + $("#" + $(this).attr("source")).attr("source")).next().append(n);
			}
		}else
		{
			var boolean = false;
			$("input[source='" + $(this).attr("source") + "']").each(function(){
				if($(this).attr("checked"))
					boolean = true;
			});
			
			if(!boolean)
			{
				$("#" + $(this).attr("source")).removeAttr("checked");
				$("#" + $(this).attr("source")).next().children(".get").remove();
			}
			
			if($("#" + $(this).attr("source")).attr("source") !== typeof undefined)
			{
				boolean = false;
				$("input[source='" + $("#" + $(this).attr("source")).attr("source") + "']").each(function(){
					if($(this).attr("checked"))
						boolean = true;
				});
				
				if(!boolean)
				{
					$("#" + $("#" + $(this).attr("source")).attr("source")).removeAttr("checked");
					$("#" + $("#" + $(this).attr("source")).attr("source")).next().children(".get").remove();
				}
			}
		}
//		if($(this).attr("checked"))
//		{
//			$(this).parents("input[type=checkbox]").each(function(){
//				var n = "<span class='get'><i class='fa fa-check'></i></span>";
//				$(this).attr("checked",true);
//				$(this).next().append(n);
//			});
//		}else
//		{
//			$(this).parents("input[type=checkbox]").each(function(){
//				var boolean = false;
//				$("input[source='" + $(this).attr("id") + "']").each(function(){
//					if($(this).attr("checked"))
//						boolean = true;
//				});
//				
//				if(!boolean)
//				{
//					$("#" + $(this).attr("id")).removeAttr("checked");
//					$("#" + $(this).attr("id")).next().children(".get").remove();
//				}
//			});
//		}
	}
	
	if($(this).attr("checked"))
	{
		$(this).parent().parent().find("input[type='checkbox']").each(function(){
			$(this).attr("checked",true);
			var n = "<span class='get'><i class='fa fa-check'></i></span>";
			$(this).next().append(n);
		});
	}else
	{
		$(this).parent().parent().find("input[type='checkbox']").each(function(){
			$(this).removeAttr("checked");
			$(this).next().children(".get").remove();
		});
		
	}
});