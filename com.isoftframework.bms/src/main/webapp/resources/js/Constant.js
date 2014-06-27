var constant=(function(){
	var constants={},
		ownProp=Object.prototype.hasOwnProperty,
		allowed = {
			string: 1,
			number: 1,
			boolean: 1,
		},
		prefix = (Math.random() + "_").slice(2);
	return {
		set:function(name,value){
			if(this.isDefined(name)) {
				return false;
			}
			if(!ownProp.call(allowed,typeof value)) {
				return false;
			}
			constants[prefix + name] = value;
			return true;
		},
		isDefined:function (name) {
			return ownProp.call(constants,prefix + name);
		},
		get : function(name) {
			if(this.isDefined(name)) {
				return constants[prefix + name];
			}
			return null;
		}
	}
}());
/*
 * 测试
 */
console.log('constant.isDefined("maxwidth"):',constant.isDefined("maxwidth"));
console.log('constant.set("maxwidth",480):',constant.set("maxwidth",480));
console.log('constant.isDefined("maxwidth"):',constant.isDefined("maxwidth"));

console.log(constant.set("maxwidth",320));
console.log(constant.get("maxwidth"));