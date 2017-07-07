/*方法信息格式化*/
function api_initTest(obj){
	var jn={};
	for(var k in obj){
		var fields=obj[k].fields;
		if(fields!=null){/*有字段*/
			if(obj[k].type == 'java.util.List'){/*集合*/
				jn[k]=api_innerFields(fields[0]);
			}else{
				if(isString(fields)){/*是循环引用*/
					jn[k]=obj[k].desc;
				}else{
					jn[k]=api_innerFields(fields);
				}
			}
		}else{
			jn[k]=obj[k].desc;
		}
	}
	return JSON.stringify(jn);
}

function api_innerFields(obj){
	var v={};
	for(var k in obj){
		var fields=obj[k].fields;
		if(fields!=null){/*有字段*/
			if(obj[k].type == 'java.util.List'){/*集合*/
				v[k]=api_innerFields(fields[0]);
			}else{
				if(isString(fields)){
					v[k]=obj[k].desc;
				}else{
					v[k]=api_innerFields(fields);
				}
			}
		}else{
			v[k]=obj[k].desc;
		}
	}
	return v;
}

function isString(str){
	return (typeof str=='string') && str.constructor==String;
}

/*js格式化输出*/
function api_format(txt, compress) {
	var indentChar = '    ';
	if (/^\s*$/.test(txt)) {
		return;
	}
	try {
		var data = eval('(' + txt + ')');
	} catch (e) {
		return;
	};
	var draw = [], last = false, This = this, line = compress ? '' : '\n', nodeCount = 0, maxDepth = 0;

	var notify = function(name, value, isLast, indent, formObj) {
		nodeCount++;
		for (var i = 0, tab = ''; i < indent; i++)
			tab += indentChar;
		tab = compress ? '' : tab;
		maxDepth = ++indent;
		if (value && value.constructor == Array) {
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + '[' + line);
			for (var i = 0; i < value.length; i++)
				notify(i, value[i], i == value.length - 1, indent, false);
			draw.push(tab + ']' + (isLast ? line : (',' + line)));
		} else if (value && typeof value == 'object') {
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + '{' + line);
			var len = 0, i = 0;
			for ( var key in value)
				len++;
			for ( var key in value)
				notify(key, value[key], ++i == len, indent, true);
			draw.push(tab + '}' + (isLast ? line : (',' + line)));
		} else {
			if (typeof value == 'string')
				value = '"' + value + '"';
			draw.push(tab + (formObj ? ('"' + name + '":') : '') + value + (isLast ? '' : ',') + line);
		};
	};
	var isLast = true, indent = 0;
	notify('', data, isLast, indent, false);
	return draw.join('');
}