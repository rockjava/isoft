var klass = function(Parent , props){
	var Child, F, i;
	
	//1.新构造函数
	Child = function () {
		if (Child.uber && Child.uber.hasOwnProperty("_construct")){
			Child.uber._construct.apply(this, arguments);
		}
		if(Child.prototype.hasOwnProperty("_construct")){
			Child.prototype._construct.apply(this, arguments);
		}
	};
	//2.继承
	Parent = Parent || Object;
	F = function(){};
	F.prototype=Parent.prototype;
	Child.prototype=new F();
	Child.uber=Parent.prototype;
	Child.prototype.constructor=Child;
	//3.添加实现方法 
	for(i in props){
		if(props.hasOwnProperty(i)){
			Child.prototype[i]= props[i];
		}
	}
	
	return Child;
}

function extendDeep(parent,child){
	var i,
		toStr=Object.prototype.toString,
		astr="[object Array]";
	child = child || {};
	for(i in parent){
		if(parent.hasOwnProperty(i)){
			if(typeof parent[i] === "object"){
				child[i] = (toStr.call(parent[i]) === astr) ? [] : {};
				extendDeep(parent[i],child[i]);
			} else {
				child[i] = parent[i];
			}
		}
	}
	return child;
}

function extend(parent,child){
	var i;
	child = child || {};
	for(i in parent){
		if(parent.hasOwnProperty(i)){
			child[i]=parent[i];
		}
	}
	return child;
}

function mix(){
	var arg, prop, child = {};
	for(arg = 0; arg < arguments.length;arg++){
		for(prop in arguments[arg]){
			if(arguments[arg].hasOwnProperty(prop)){
				child[prop] = arguments[arg][prop];
			}					
		}
	}
	return child;
}

function bind(o, m){
	return function(){
		return m.apply(o, Array.prototype.slice.call(arguments));
	};
}

if(typeof Function.prototype.find === "undefined"){
	Function.prototype.bind = function(thisArg){
		var fn = this,
			slice = Array.prototype.slice,
			args = slice.call(arguments, 1);
		return function(){
			//console.log('inndre fn :', args.concat(slice.call(arguments)));
			return fn.apply(thisArg, args.concat(slice.call(arguments)));
		
		};
	};
}

var $g = function (id) { 
	return "string" == typeof id ? document.getElementById(id) : id; 
}; 
var Class = { 
	create: function() { 
		return function() { 
			this.initialize.apply(this, arguments); 
		}; 
	} 
}; 

/**
 * 事件绑定
 * 例：this.onClick.bindEvent(this,{foo:123});
 * 
 *    function onClick(e,me,param){
 * 			
 * 	  }
 */
Function.prototype.bindEvent=function(object,newPara){
    var __method=this;
    return function(event){
    	//null直接调用方法，event||window.event,object,newPara为参数
        __method.call(null,event||window.event,object,newPara);
		return null;
    };
};



if(typeof Function.prototype.method !=="function"){
	Function.prototype.method = function (name, implementation){
		return this.prototype[name] = implementation;
		return this;
	}
}

function Each(list, fun){ 
    for (var i = 0, len = list.length; i < len; i++)
    { fun(list[i], i); } 
}; 

Object.extend = function(destination, source) { 
    for (var property in source) { 
    //alert("destination property="+property+"="+destination[property]+" ;source property="+property+"="+source[property]);
        destination[property] = source[property]; 
    } 
    return destination; 
};