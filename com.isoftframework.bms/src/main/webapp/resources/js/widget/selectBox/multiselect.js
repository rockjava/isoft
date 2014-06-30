  
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
        __method.call(null,event||window.event,object,newPara);//null直接调用方法，event||window.event,object,newPara为参数
		return null;
    };
};

MOption=function(_name,_value){
	this.name=_name;
	this.value=_value;
}   

MenutiSelect=function(textfieldId,arr,hiddenfieldId){
	
    highlightindex = -1;
	this._splitSign=',';
	this._arr=arr;
	this.targetOption=null;
	this._selectArray=new Array();
	this. wordNodes=$(arr);
	
	var textfield=this.getElement(textfieldId);
	var hiddenfield=this.getElement(hiddenfieldId);
	
	this.textObj=$(textfield);
	$textObj=this.textObj;
	if(hiddenfield){
		this.valueObj=$(hiddenfield);
	}else{
		this.valueObj=$('<input type="hidden" name="selectValue" value=""/>');
	}
	
	$valueObj=this.valueObj;
	this.textObj.css('background', 'transparent url(../images/select_button.gif) no-repeat top right');
	//定义一个下拉按钮层，并配置样式(位置，定位点坐标，大小，背景图片，Z轴)，追加到文本框后面
	/*this.selectButton = $('<div></div>')
	.css('position', 'absolute')
	.css('left', this.textObj.position().left + this.textObj.width() - 15 + 'px')
	.css('top',this.textObj.position().top + 2 + 'px')
	//.css('background', 'transparent url(../images/select_button.gif) no-repeat top left')
	.css('height', '16px').css('width', '15px').css('z-index', '100');
	this.textObj.after(this.selectButton);
	$selectButton=this.selectButton*/
	
	 //定义一个下拉框层，并配置样式(位置，定位点坐标，宽度，Z轴)，先将其隐藏
    this.SELECT = $('<div></div>').css('position', 'absolute')
	.css('border', '1px solid #000000')
	.css('left', this.textObj.position().left + 'px')
	.css('top',this.textObj.position().top +  this.textObj.height() + 7 + 'px')
	.css('width', this.textObj.width() + 'px')
	.css('background','white')
	.css('z-index', '100');
	
    this. textObj.after(this.SELECT);
   	this. SELECT.hide();
	$SELECT=this.SELECT;
	
	this._buildSelectOptoins(this._arr);
	$textObj.bind("click", function(){
		 $SELECT.show();
	});
		
	 //通过点击位置，判断弹出的显示
     $(document).mouseup(function(event){
         //如果是下拉按钮层或下拉框层，则依然显示下拉框层
         if (event.target == $SELECT.get(0) || event.target == $textObj.get(0)
		 	||$(event.target).attr('name') == 'moption'
			||$(event.target).attr('name') == 'option'
			||$(event.target).attr('type') == 'checkbox') {
           // $SELECT.show();
         }
         else {
           
           //如果是其他位置，则将下拉框层
           if ($SELECT.css('display') == 'block') {
               $SELECT.hide();
           }
       }
     
   });
   
    //选项层的鼠标移入移出样式
    $SELECT.mouseover(function(event){
    	if ($(event.target).attr('name') == 'moption') {
	        //移入时背景色变深，字色变白
	        highlightindex= $SELECT.children("div").index($(event.target)) ;
	        $(event.target).css('background-color', '#000077').css('color', 'white');
	        $(event.target).mouseout(function(){
	        	//移出是背景色变白，字色变黑
	       		highlightindex=-1;
	            $(event.target).css('background-color', '#FFFFFF').css('color', '#000000');
	        });
        }
    });
	 
	 
	this._getSelectArray = function(){
		return this._selectArray;
	}
	
	this._setSelectArray = function(array){
		this._selectArray = array;
	}
	
}

MenutiSelect.prototype._buildSelectOptoins=function(arr){
 //定义选项层，并配置样式(宽度，Z轴一定要比下拉框层高)，添加name、value属性，加入下拉框层
    
	var  $SELECT=this.SELECT;
    for(i=0;i<arr.length;i++) {
    	//alert(arr[i].name);
		var val=arr[i];
    	var wordNode = $(val);
    	//alert(wordNode.attr('name') );
		_mcheckbox=$('<input type="checkbox"/>')
		.attr('value', wordNode.attr('value'))
		.attr('name', wordNode.attr('name'));
		 
		_mcheckbox.get(0).checked=wordNode.attr('checked');
		if(wordNode.attr('checked')){
			this._selectArray.push(val);
		}
		//前一个this为selectBox对象，后一个_mcheckbox为方法的参数
		_mcheckbox.get(0).onclick = this._selectBoxCheck.bindEvent(this,_mcheckbox.get(0));
		
       _option = $('<span>' + wordNode.attr('name') + '</span>')		
		.attr('value', wordNode.attr('value'));
		
		_moption= $('<div></div>')
		.attr('name', 'moption')
		.css('width', $SELECT.width())
		.css('z-index', $SELECT.css('z-index') + 1);
		_moption.append(_mcheckbox).append(_option);
       $SELECT.append(_moption);
    } 
}


MenutiSelect.prototype._selectBoxCheck = function(e,obj,para){
	//得到存放文本框显示内容的数组
	var selectArray = obj._getSelectArray();
	 
	if(para.checked == true){
		selectArray.push({name:para.name,value:para.value});		
		obj._showSelectArray();
	}else if(para.checked == false){//将取消选择的值从数组中删除
		obj._deleteFromSelectArray({name:para.name,value:para.value});
		obj._showSelectArray();
	}
	obj._setSelectArray(selectArray);
	 
}

//得到数组中所有的值（选中的值），显示在文本框上
MenutiSelect.prototype._showSelectArray = function(){
	//临时变量，存放要显示在文本框上的内容
	var finalTxt = '';
	var finalValue = '';
	var selectArray = this._selectArray;
	
	var splitSign = this._splitSign;
	if(selectArray.length == 0){
		finalTxt = '请选择';	
		this.textObj.val(finalTxt);
       	this.valueObj.attr('value',finalValue);					 
		return;
	}
	for(var i=0;i<selectArray.length;i++){
		if(i != 0){
			finalTxt += ",";
			finalValue += splitSign;
		}
		finalTxt += selectArray[i].name;
		finalValue += selectArray[i].value;
	}
	this.textObj.val(finalTxt);
    this.valueObj.attr('value',finalValue);	
}

//从数组中删除指定元素
MenutiSelect.prototype._deleteFromSelectArray = function(moption){
	var selectArray = this._getSelectArray();
	 
	for(var i=0;i<selectArray.length;i++){
	
		if(selectArray[i].value == moption.value){
			//删除指定元素，将后面元素向前移动1位
			for(var j=i;j+1<selectArray.length;j++){
				selectArray[j] = selectArray[j+1];
				 
			}
			selectArray.pop();
		 
			return;
		}
	}
	this._setSelectArray(selectArray);
	 
}
MenutiSelect.prototype._changeSelectedOptoins=function(arr){
	this.clear();
	this._buildSelectOptoins(arr);
}
MenutiSelect.prototype.clear=function(){
	this.SELECT.empty();
	this._selectArray=new Array();
	this.textObj.get(0).value='';
	this.valueObj.get(0).value='';
	
}
MenutiSelect.prototype.getValue=function(){
	return this.valueObj.get(0).value;
}

MenutiSelect.prototype.getElement = function (id) { 
	if(id==null){
		return null;
	}
	return "string" == typeof id ? this.getElementById(id) : id; 
}; 
MenutiSelect.prototype.getElementById = function(id, object){
	object = object || document;
	return document.getElementById ? object.getElementById(id) : document.all(id);
};