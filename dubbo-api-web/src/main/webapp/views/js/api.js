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

var zNodes = [ {
	name : "服务模块1",
	open : true,
	children : [ {
		name : "接口名称1(类似于mvc的controller)",
		children : [ {
			name : "方法1"
		}, {
			name : "方法2"
		}, {
			name : "方法3"
		}, {
			name : "方法4"
		} ]
	}, {
		name : "接口名称2(类似于mvc的controller)",
		children : [ {
			name : "方法1"
		}, {
			name : "方法2"
		}, {
			name : "方法3"
		}, {
			name : "方法4"
		} ]
	} ]
}, {
	name : "服务模块2",
	open : true,
	children : [ {
		name : "接口名称1(类似于mvc的controller)",
		children : [ {
			name : "方法1"
		}, {
			name : "方法2"
		}, {
			name : "方法3"
		}, {
			name : "方法4"
		} ]
	}, {
		name : "接口名称2(类似于mvc的controller)",
		children : [ {
			name : "方法1"
		}, {
			name : "方法2"
		}, {
			name : "方法3"
		}, {
			name : "方法4"
		} ]
	} ]
} ];