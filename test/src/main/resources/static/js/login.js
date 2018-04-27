document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 27) { // 按 Esc
		// 要做的事情
	}
	if (e && e.keyCode == 113) { // 按 F2
		// 要做的事情
	}
	if (e && e.keyCode == 13) { // enter 键
		// 要做的事情
		var name = $("#logname").val();
		var id = $("#logpass").val();
		if (name.trim().length == 0) {
			return false;
		}
		if (id.trim().length == 0) {
			return false;
		}
		dologin();
	}
};
window.onload = function() {
//	$('#w').window('open');
}
function dologin() {
	var name = $("#logname").val();
	var id = $("#logpass").val();
	if (name.trim().length == 0) {
		$.messager.alert("提示信息", "请输入用户!");
		return false;
	}
	if (id.trim().length == 0) {
		$.messager.alert("提示信息", "请输入密码!");
		return false;
	}
	$.ajax({
		type : "GET",
		url : "doLogin?id=" + id + "&name=" + name,
		async : false,// 同步请求
		success : function(data) {
			if (data == "SUCCESS") {
				window.location.href = "/index.html";
			} else {
				if (data == "FAIL") {
					$.messager.alert("提示信息", "账号密码错误请重新输入!");
				}
			}
		},
		error : function(e) {
		}
	});
}