
$(function() {
	//展示，关闭表格筛选栏目
	$('.table-head-right').on('click', function() {
		$('.title-wrapper').hide();
		$('.filter-wrapper').show();
	});
	$('.close-btn').on('click', function() {
		$('.title-wrapper').show();
		$('.filter-wrapper').hide();
	});
});
