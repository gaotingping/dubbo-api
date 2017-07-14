var setting = {
	callback : {
		beforeClick : function(treeId, treeNode) {
			if(treeNode.isM != null && treeNode.isM == true){
				apiMethodInit(treeNode.app,treeNode.service,treeNode.name);
			}
		}
	}
};

$(document).ready(function() {

	// 参数格式
	apiMenuInit();

	// 具体指定方法
	//apiMethodInit();
});

function apiMethodInit(app,service,method) {

	$.get("/doc/method?app="+app+"&service="+service+"&method="+method, function(data) {

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
		jf.doFormat(result.request);

		// 格式化-响应
		var options2 = {
			dom : '#m_resp'
		};
		var jf2 = new JsonFormater(options2);
		jf2.doFormat(result.response);
	});
}

function apiMenuInit() {
	$.get("/doc/menu", function(data) {
		$.fn.zTree.init($("#apiTreeId"), setting, JSON.parse(data));
		var treeObj = $.fn.zTree.getZTreeObj("apiTreeId");
		treeObj.expandAll(true);
	});
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