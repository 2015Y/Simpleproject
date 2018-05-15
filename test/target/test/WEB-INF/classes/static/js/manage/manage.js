window.onload = function() {
}
function downloadExcel(){
	$.ajax({
		type : "GET",
		url : "downloadExcel",
		async : false,// 同步请求
		success : function(data) {
		},
		error : function(e) {
			alert(e);
		}
	});
}
