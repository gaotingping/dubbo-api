var xmlhttp = null;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else if (window.ActiveXObject) {
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

var fastAJax = function(args) {
	if (xmlhttp == null) {
		alert("xmlhttp is null");
	} else {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					args.success(xmlhttp.responseText);
				} else {
					args.error(xmlhttp.responseText);
				}
			}
		};
		xmlhttp.open(args.type,args.url, true);
		xmlhttp.send(args.data);
	}
};

function test(args){
	fastAJax({
		type:'post/get',
	    url:'http',
		data:'data',
		success:function(data){
			
		},
		error:function(data){
			
		}
	});
}