 
///////////////////////////////////// 
//////////////////////////////// 
/**
 * need util.js
 */
var TableOrder = Class.create(); 
TableOrder.prototype = { 
    initialize: function(tbody) { 
        var oThis = this; 
         
        this.Body = $g(tbody);//tbody对象 
        this.Rows = [];//行集合 
        this.columnHeaders = [];
        Each(this.Body.rows, function(o){ oThis.Rows.push(o); }) 
    }, 
    //排序并显示 
    Sort: function(order) { 
        //排序 
        this.Rows.sort(this.Compare(order)); 
        order.Down && this.Rows.reverse(); 
        //显示表格 
        var oFragment = document.createDocumentFragment(); 
        Each(this.Rows, function(o){ oFragment.appendChild(o); }); 
        this.Body.appendChild(oFragment); 
    }, 
    //比较函数 
    Compare: function(order) { 
        var oThis = this; 
        return function(o1, o2) { 
            var value1 = oThis.GetValue(o1, order), value2 = oThis.GetValue(o2, order); 
            return value1 < value2 ? -1 : value1 > value2 ? 1 : 0; 
        }; 
    }, 
    //获取比较值 
    GetValue: function(tr, order) { 
        
       var data =""; 
       if(order.Attribute=="innerHTML"){
        data=tr.getElementsByTagName("td")[order.Index].innerHTML;
       }
       else{
       data = tr.getElementsByTagName("td")[order.Index].getAttribute(order.Attribute); 
       }
      
        //数据转换 
        switch (order.DataType.toLowerCase()) { 
            case "int": 
                return parseInt(data) || 0; 
            case "float": 
                return parseFloat(data) || 0; 
            case "date": 
                return Date.parse(data) || 0; 
            case "string": 
            default: 
                return data.toString() || ""; 
        } 
    }, 
    //添加并返回一个排序对象 
    Add: function(index, options) { 
        var oThis = this; 
        return new function(){ 
            //默认属性 
            this.Attribute = "innerHTML";//获取数据的属性 
            this.DataType = "string";//数据类型 
            this.Down = false;//是否按顺序 
            Object.extend(this, options || {}); 
            //排序对象的属性 
            this.Index = index; 
            this.Sort = function(){ oThis.Sort(this); }; 
        }; 
    } ,
    addOrderColumn:function(columnHeader_, index, options){
    	var oThis = this; 
    	var columnHeader = $g(columnHeader_);
    	var order = oThis.Add(index, options); 
    	columnHeader.onclick = function(){ 
			//取相反排序 
	    	 
	        order.Down = !order.Down; 
	        //设置样式 
	        Each(oThis.columnHeaders, function(colHeader){ colHeader.className = ""; }) ;
	        columnHeader.className = order.Down ? "down" : "up"; 
	        //排序显示 
	        order.Sort(); 
	        return false; 
	        
	    };
	    //_arr是记录排序项目（这里主要用来设置样式） 
	    oThis.columnHeaders ? oThis.columnHeaders.push(columnHeader) : oThis.columnHeaders = []; 
    }
} 

 