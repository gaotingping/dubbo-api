var setting = {
	callback : {
		beforeClick : function(treeId, treeNode) {
			console.log(treeId);
			console.log(treeNode);
			$("#api_method").show();
			$("#api_readme").hide();
		}
	}
};

$(document).ready(function() {

	// 参数格式
	apiMenuInit();

	// 具体指定方法
	apiMethodInit();

});

function apiMethodInit() {

	$.get("/doc/method", function(data) {

		// 方法头信息
		var result = JSON.parse(data);
		var mInfo = {
			"app" : result.app,
			"service" : result.service,
			"method" : result.method
		}
		$("#m_path").html('<pre>' + api_format(JSON.stringify(mInfo)) + '</pre>');

		// 格式化-请求
		var options = {
			dom : '#m_req'
		};
		var jf = new JsonFormater(options);
		jf.doFormat(JSON.stringify(result.request));

		// 格式化-响应
		var options2 = {
			dom : '#m_resp'
		};
		var jf2 = new JsonFormater(options2);
		jf2.doFormat(JSON.stringify(result.response));
	});
}

function apiMenuInit() {
	$.fn.zTree.init($("#apiTreeId"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("apiTreeId");
	treeObj.expandAll(true);
}

/*
function apiToCopy(type) {
	if (type == 1) {
		alert(api_initTest(api_m));
	} else {
		alert(api_initTest(api_m));
	}
}
*/