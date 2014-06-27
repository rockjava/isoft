
/**
 * need util.js
 * 全选所有的checkbox 
 * @param:checkallObj_ 全选按钮id或Object
 * @param:checkdataName_ 副选按钮名称

*/

var SellectAllCheckboxes = Class.create(); 

SellectAllCheckboxes.prototype = { 
	    initialize: function(checkallObj_,checkdataName_) { 
	        var oThis = this; 
	         
	        this.checkallObj = $g(checkallObj_);//tbody对象 
	        this.checkdata = document.getElementsByName(checkdataName_); 
	        
	        var checkallObj=oThis.checkallObj;
        	var checkdata=oThis.checkdata;
	        /**
	         * 添加全选按钮多选事件
	         */
	        checkallObj.onclick = function(){
	        	
	        	if (checkallObj.checked) {
	        		if (checkdata.length == undefined) {
	        			//checkdata.status = true;
	        			checkdata.checked = true;
	        		} else {
	        			for (i = 0; i < checkdata.length; i++) {
	        			//alert((checkdata[i].disabled));
	        				if(!checkdata[i].disabled )
	        					checkdata[i].checked = true;
	        			}
	        		}
	        	} else {
	        		if (checkdata.length == undefined) {
	        			checkdata.checked = false;
	        		} else {
	        			for (i = 0; i < checkdata.length; i++) {
	        				if(!checkdata[i].disabled )
	        					checkdata[i].checked = false;
	        			}
	        		}
	        	}
	        };
	        //添加副选按钮
	        for (i = 0; i < checkdata.length; i++) {
					checkdata[i].onclick =function(){
					var allChecked=true;
					for (i = 0; i < checkdata.length; i++) {
						if(!checkdata[i].checked){
							allChecked=false;
						}
					}
					if(checkallObj.checked!=allChecked){
						checkallObj.checked=allChecked;
					}
					
					
				}
			}
	    },
	    /**
	     *获取选择的checkbox的值，多值之间已逗号分隔 
	     */
	    getValue:function(){
	    	var oThis = this; 
	    	if (oThis.checkdata.length == undefined ||oThis.checkdata.length==0) {
	    		return null;
	    	}
	    	var len=0;
	    	var value="'";
	    	for (i = 0; i < oThis.checkdata.length; i++) {
	    		//alert(checkbox[i].checked);
	    		
	    		if (oThis.checkdata[i].checked) {
	    			if(len>0){
		    			value+="','";
		    		}
	    			value+=oThis.checkdata[i].value;
	    			len++;
	    		}
	    		
	    	}
	    	value+="'";
	    	if(len>0)
	    		return value;
	    	else
	    		return null;
	    }
};
 
/**
检查Form的checkbox选定状态
@param:form对象或id 
@param:checkdataName 副选按钮名称

*/

function checkHaveSelected(form_,checkboxName) {
	f=$g(form_);
	var checkbox = document.getElementsByName(checkboxName);
	//alert(checkbox.length);
	var flag = 0;
	if (checkbox.length == undefined) {
		flag++;
	}
	for (i = 0; i < checkbox.length; i++) {
		//alert(checkbox[i].checked);
		if (checkbox[i].checked == true) {
			flag++;
		}
	}
	return flag > 0;
}

