var columns;
$(function() {
	$('#test-easyui-datagrid').datagrid({
		method : 'get',
		border : false,
		url : 'getUsers',
		fitColumns : false,
		nowrap : true,
		fit : true,
		pagination : true,
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : '序列号',
			width : 40,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'name',
			title : '姓名',
			width : 180,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 100,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 100,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 300,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 100,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 100,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, {
			field : 'age',
			title : '年龄',
			width : 100,
			align : 'center',
			styler : function(value, row, index) {
				return 'border:1;';
			}
		}, ] ],
		onLoadSuccess : function(data) {
			mergeRows(data);
		}
	});
	// 动态的加载table的列
	getDynamicColumns();
});
// 动态获取列
function getDynamicColumns() {
	$.ajax({
		type : "GET",
		url : "getDynamicColumns",
		dataType : "json",
		async : false,// 同步请求
		success : function(data) {
			columns = data.columns;
			dynamicTable();
		}
	});
}
// 获取数据
function dynamicTable() {
	$('#dynamic_tb').datagrid({
		method : 'get',
		border : false,
		url : 'getDynamicData',
		fitColumns : false,
		nowrap : true,
		fit : true,
		pagination : true,
		singleSelect : true,
		columns : [ columns ],
	});
}
// 合并相同的行
function mergeRows(data) {
	if (data.total > 0) {
		var start = 0;
		var end = 0;
		// 合并相同的行
		var temp = data.rows[0].name.trim();
		for (var i = 1; i < data.rows.length; i++) {
			if (temp == data.rows[i].name.trim()) {
				end++;
			} else {
				if (end > start) {
					$('#test-easyui-datagrid').datagrid('mergeCells', {
						index : start,
						rowspan : end - start + 1,
						field : 'name'
					})
				}
				temp = data.rows[i].name.trim();
				start = i;
				end = i;
			}
		}
		/* 这里是为了判断重复内容出现在最后的情况，如ABCC */
		if (end > start) {
			$('#test-easyui-datagrid').datagrid('mergeCells', {
				index : start,
				rowspan : end - start + 1,
				field : 'name'
			})
		}
		// 根据index合并指定的列
		$('#test-easyui-datagrid').datagrid('mergeCells', {
			index : 0,
			colspan : 1,
			field : 'name'
		})
	}
}
function cancel() {
	$.ajax({
		type : "GET",
		url : "cancel",
		async : false,// 同步请求
		success : function(data) {
			alert("注销返回的数据:	" + data);
		},
		error : function(e) {
			alert(e);
		}
	});
}
function addUser() {
	$.ajax({
		type : "GET",
		url : "addUser",
		async : false,// 同步请求
		success : function(data) {
			$('#test-easyui-datagrid').datagrid('load', {});
		},
		error : function(e) {
			alert(e);
		}
	});
}
function editUser() {
	var row = $("#test-easyui-datagrid").datagrid("getSelected");
	if (row) {
		$('#user_id').val(row.id);
		$('#name').val(row.name);
		$('#age').val(row.age);
		$('#dlg').window('open');

	} else {
		$.messager.alert("提示信息", "请选择要修改的用户!");
	}
}
function saveEditUser() {
	$('#editUser').form({
		type : "GET",
		url : 'updateOne',
		onSubmit : function() {
			if ($("#name").val().trim().length == 0) {
				$.messager.alert("提示信息", "请填写姓名!" + projectName);
				return false;
			}
			if ($("#age").val().trim().length == 0) {
				$.messager.alert("提示信息", "请填写年龄!");
				return false;
			}
		},
		success : function(data) {
			$('#dlg').window('close');
			$('#test-easyui-datagrid').datagrid('load', {});
		}
	});
}
function logout() {
	$.ajax({
		type : "GET",
		url : "logout",
		async : false,// 同步请求
		success : function(data) {
			window.location.href = "login.html";
		},
		error : function(e) {
			window.location.href = "login.html";
		}
	});
}