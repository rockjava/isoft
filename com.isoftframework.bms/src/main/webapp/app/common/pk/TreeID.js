Ext.define('FM.common.pk.TreeID', {
	singleton: true,
	seperator:"-",
	generateId:function(parentNode){
		return (parentNode.isRoot()?"" : parentNode.get('id')+this.seperator)+this.generateNextBrother(parentNode);
	},
	format :function(n){
		var str=n+'';
		for(var i=2-str.length;i>0;i--){
			str='0'+str;
		}
		return str;
		//return Ext.util.Format.number (n,'00');
	},
	generateNextBrother:function(parentNode){
		
		return this.format(this.generateSubBrotherId(this.getChildSubIds(parentNode)));
	},
	generateSubBrotherId:function( ids){
		if(ids){
			ids.sort(function(a, b) {
			    return a - b;
			});
			console.log(ids);
			var n=1;
			for(var i=0;i<ids.length;n++,i++){
				if(n<ids[i]){
					return n;
				}
			}
			return n++;
		}else{
			return 1;
		}
	},
	 
	getChildSubIds:function(parentNode){
		var nodes=parentNode.childNodes;
		if(nodes){
			var subIds=new Array();
			for(var i=0;i<nodes.length-1;i++){
				subIds[i]=(parseInt(this.getSubChildId(nodes[i], parentNode)));
			}
			return subIds;
		}
		return null;
	},
	getSubChildId:function(  childNode,  parentNode){
		if(!childNode){
			return null;
		}
		if(!parentNode||parentNode.isRoot())
			return childNode.get('id');
		
		return  childNode.get('id').substring((parentNode.get('id')+this.seperator).length,childNode.get('id').length);
	}
	
});