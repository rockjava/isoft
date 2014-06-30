/**
 * need json2.js
 */
//  var params=new FormElements(formid).getFromParams();
var isIe = (document.all)?true:false;
var $g = function (id) { 
	return "string" == typeof id ? document.getElementById(id) : id; 
}; 
var Class = { 
	create: function() { 
		return function() { 
			this.initialize.apply(this, arguments); 
		} 
	} 
}; 

var FormElements = Class.create(); 
FormElements.prototype={
	initialize: function(id) { 
		 
		this.form = $g(id); 
	 
	}, 
	doReset: function(){
		var oform=this.form;
		var eles=oform.elements;
		 
		for(var i=0;i<eles.length;i++){
			var ele=eles[i];
			if(ele.type=='button' && ele.type=='submit'){
				continue;
			}
			if(eles[i].type=="select-one"){
				eles[i].options[0].selected="selected";
			}else{
				eles[i].value="";
			}
		}
		
	},
	
	loadRecord:function(record){
		var oform=this.form;
		var eles=oform.elements;
		 
		for(var i=0;i<eles.length;i++){
			var ele=eles[i];
			//document.write(ele.type);
			if(ele.type!='button' && ele.type!='submit'){
				ele.value=record[ele.name];
			}
			
			
		}
	},
	getJsonData:function(){
		var oform=this.form;
		var eles=oform.elements;
		var jsonObj={};
		for(var i=0;i<eles.length;i++){
			var ele=eles[i];
			if(ele.type!='button' && ele.type!='submit'){
				jsonObj[ele.name]=ele.value;
			}
			
		}
		return jsonObj;
	},
	submit:function(){
		this.form.submit();
	},
	ajaxSubmit:function(settings){
		settings.data=JSON.stringify(this.getJsonData());
		//settings.data=this.getJsonData().toJSONString(),
		this.ajax(settings );
	},
	getFromParams: function(){
		var oform=this.form;
		var eles=oform.elements;
		var params="";
		for(var i=0;i<eles.length;i++){
			if(i!=0){
			params+="&";
			}
			params+= eles[i].name+"="+eles[i].value;
		}
		return params;
		
	},
	attachElesEvent:function(){
		var oform=this.form;
		var eles=oform.elements;
		 
		for(var i=0;i<eles.length;i++){
			if(eles[i].type=="text"||eles[i].type=="select-one"){
				if(isIe){
				 eles[i].attachEvent("onkeypress",function( ){
				 	var event =  window.event;
				 	var keyCode=event.keyCode||event.which;
				 	if(keyCode==13){
				 		oform.submit();
				 	}
				   	 
				 }); 
				}
				else{
				
				//boole :该参数是一个布尔值：false或true必须填写．false代表支持浏览器事件捕获功能，true代表支持浏览事件冒泡功能．
				
				 eles[i].addEventListener("keypress",function(e){
				 	//event = event || window.event;
				   	var event=e;
				   	var keyCode=event.keyCode||event.which;
				   	//alert(keyCode);
				 	if(keyCode==13){
				 		oform.submit();
				 	}
				 },false); 
				}
				
			}
			 
		}
	},
	/**
	 * post json
	 * @param settings.url
	 * @param settings.data
	 * @param settings.success
	 * @param settings.failture
	 * @param contentType
	 */
	ajax:function(settings){
	//alert(callback);
		var createXMLHttpRequest=function(){
			var xmlhttp=null;
			if (window.XMLHttpRequest) {
				
				//需要针对IE和其他类型的浏览器建立这个对象的不同方式写不同的代码
				//针对FireFox，Mozillar，Opera，Safari，IE7，IE8
				xmlhttp = new XMLHttpRequest();
				//针对某些特定版本的mozillar浏览器的BUG进行修正
				if (xmlhttp.overrideMimeType) {
					xmlhttp.overrideMimeType("text/xml");
				}
			} else if (window.ActiveXObject) {
				//针对IE6，IE5.5，IE5
				//两个可以用于创建XMLHTTPRequest对象的控件名称，保存在一个js的数组中
				//排在前面的版本较新
				var activexName = [ "MSXML2.XMLHTTP", "Microsoft.XMLHTTP" ];
				for ( var i = 0; i < activexName.length; i++) {
					try {
						//取出一个控件名进行创建，如果创建成功就终止循环
						//如果创建失败，回抛出异常，然后可以继续循环，继续尝试创建
						xmlhttp = new ActiveXObject(activexName[i]);
						break;
					} catch (e) {
					}
				}
			}
			return xmlhttp;
		}
		//1.创建XMLHttpRequest对象
		var xmlhttp=createXMLHttpRequest();
		//确认XMLHTtpRequest对象创建成功
		if (!xmlhttp) {
			alert("XMLHttpRequest对象创建失败!!");
			return;
		} 
		//2.注册回调函数
		//注册回调函数时，之需要函数名，不要加括号
		//我们需要将函数名注册，如果加上括号，就会把函数的返回值注册上，这是错误的
		xmlhttp.onreadystatechange = function(){
			//alert(xmlhttp.readyState);
			//5。接收响应数据
			//判断对象的状态是交互完成
			if (xmlhttp.readyState == 4) {
				//判断http的交互是否成功
				if (xmlhttp.status == 200) {
					//使用responseXML的方式来接收XML数据对象的DOM对象
					if(settings.success){
						//xmlhttp.responseText or xmlhttp.response
						settings.success(JSON.parse(xmlhttp.responseText));
						return;
					}
				} 
			}
			if(settings.failture){
				settings.failture(xmlhttp);
			}
			
		};
	
		//3。设置连接信息
		//第一个参数表示http的请求方式，支持所有http的请求方式，主要使用get和post
		//第二个参数表示请求的url地址，get方式请求的参数也在url中
		//第三个参数表示采用异步还是同步方式交互，true表示异步
		//POST方式请求的代码
		xmlhttp.open("POST", settings.url+"?time="+new Date().toLocaleString(), true);
		//POST方式需要自己设置http的请求头
		//application/x-www-form-urlencoded or multipart/form-data or application/json
		var contentType='application/json; charset=UTF-8';
		if(settings.contentType){
			contentType=settings.contentType;
		}
		xmlhttp.setRequestHeader('Content-Type',contentType);		
	
		//4.发送数据，开始和服务器端进行交互
		//同步方式下，send这句话会在服务器段数据回来后才执行完
		//异步方式下，send这句话会立即完成执行
		xmlhttp.send(settings.data);
	} 
	
	
}