
var fastAJax = function(args) {

	var xmlhttp = null;

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	if (xmlhttp == null) {
		args.handler("notSupport");
	} else {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				console.log(xmlhttp);
				if (xmlhttp.status == 200) {
					args.handler(xmlhttp.responseText);
				} else {
					args.handler(xmlhttp.statusText);
				}
			}
		};
		xmlhttp.open(args.type,args.url, true);
		xmlhttp.send(args.data);
	}
};


function test(){
	var tmpData=get("args").value;
	fastAJax({
		type:'post',
	    url:get("http").value,
		data:tmpData,
		handler:function(data){
			var rTxt=data;
			try{
				var json1=JSON.parse(rTxt);
				if(json1.opFlag == "true"){
					var json2=JSON.parse(json1.serviceResult);
					set("show",json2);
				}else{
					set("show",rTxt);
				}
			}catch(e){
				set("show",rTxt);
			}
		}
	});
}

function set(id,txt){
   var options = {
			dom : '#'+id,
			imgCollapsed:"../jsonFormat/images/Collapsed.gif",
			imgExpanded:"../jsonFormat/images/Expanded.gif",
			quoteKeys:false
	};
	var jf = new JsonFormater(options);
	jf.doFormat(txt);
}

function get(id){
	return  document.getElementById(id);
}