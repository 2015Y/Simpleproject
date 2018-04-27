$(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var test = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/blog/test/update",
//		data : {
//			testId:$("#testId").val(),
//			testName:$("#testName").val(),
//			testAge:$("#testAge").val(),
//			testAddr:$("#testAddr").val()
//		},
		data :test,
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			testName : {
				required : true
			},
			testAge : {
				required : true
			},
			testAddr : {
				required : true
			}
		},
		messages : {
			testName : {
				required : icon + "请输入姓名"
			},
			testAge : {
				required : icon + "请输入年龄"
			},
			testAddr : {
				required : icon + "请输入地址"
			},
		}
	})
}