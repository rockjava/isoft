
/////////////////////////调用实例
//        <div>
//                <span>交易查询：</span> <span>从
//                    <input name="control_date" type="text" id="control_date" size="10"
//                        maxlength="10" onclick="new CalendarQuarter().show(this);" readonly="readonly" />
//                    <input type="button" name="button" id="button" value="button" onclick="new CalendarQuarter().show(this.form.control_date);" /></span>
//                <span>至
//                    <input name="control_date2" type="text" id="control_date2" size="10"
//                        maxlength="10" onclick="new CalendarQuarter().show(this);" readonly="readonly" />
//                    <input type="button" name="button" id="button1" value="button" onclick="new CalendarQuarter().show(this.form.control_date2);" /></span>

//        </div>


/**
 * CalendarQuarter
 * @param   beginYear           1990
 * @param   endYear             2010
 * @param   language            0(zh_cn)|1(en_us)|2(en_en)|3(zh_tw)
 * @param   patternDelimiter    "Q"
 * @version 1.0 build 2013-06-06
 * @author  wzq 
 * NOTE!    you can use it free, but keep the copyright please
 * IMPORTANT:you must include this script file inner html body elment 

 */
function CalendarQuarter(initObj) {	
    if(initObj==null)
		initObj=new Object();
	this.initObj=initObj;
	this.beginYear = initObj.beginYear || 1990;
	this.endYear   = initObj.endYear   || 2020;
	this.language  = initObj.language  || 0;
	this.patternDelimiter = initObj.patternDelimiter     || "Q";
 
	this.onpicked=initObj.onpicked;
	 
	this.dateControl = null;
	this.panel  = this.getElementById("__calendarPanel");
	this.iframe = window.frames["__calendarIframe"];
	this.form   = null;
	
	this.date = new Date();
	this.year = this.date.getFullYear();
	this.month = this.date.getMonth();
	this.quarter=this.compQuarterByMonth( this.date.getMonth());
	this.colors = {"bg_cur_day":"#00CC33","bg_over":"#EFEFEF","bg_out":"#FFCC00"};
	this.showLine=3;
};


CalendarQuarter.language = {
	"year"   : ["\u5e74", "", "", "\u5e74"],
	"months" : [
				["\u4e00\u6708","\u4e8c\u6708","\u4e09\u6708","\u56db\u6708","\u4e94\u6708","\u516d\u6708","\u4e03\u6708","\u516b\u6708","\u4e5d\u6708","\u5341\u6708","\u5341\u4e00\u6708","\u5341\u4e8c\u6708"],
				["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"],
				["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"],
				["\u4e00\u6708","\u4e8c\u6708","\u4e09\u6708","\u56db\u6708","\u4e94\u6708","\u516d\u6708","\u4e03\u6708","\u516b\u6708","\u4e5d\u6708","\u5341\u6708","\u5341\u4e00\u6708","\u5341\u4e8c\u6708"]
				],
	"weeks"  : [["\u65e5","\u4e00","\u4e8c","\u4e09","\u56db","\u4e94","\u516d"],
				["Sun","Mon","Tur","Wed","Thu","Fri","Sat"],
				["Sun","Mon","Tur","Wed","Thu","Fri","Sat"],
				["\u65e5","\u4e00","\u4e8c","\u4e09","\u56db","\u4e94","\u516d"]
		],
	"clear"  : ["\u6e05\u7a7a", "Clear", "Clear", "\u6e05\u7a7a"],
	"today"  : ["\u4eca\u5929", "Today", "Today", "\u4eca\u5929"],
	"close"  : ["\u5173\u95ed", "Close", "Close", "\u95dc\u9589"],
	"date2StringPattern" : ["yyyy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd"],
	"string2DatePattern" : ["ymd","ymd", "ymd", "ymd"]
};

CalendarQuarter.prototype.draw = function() {
	 calendar = this;
	
	var _cs = [];
	_cs[_cs.length] = '<form id="__calendarForm" name="__calendarForm" method="post">';
	_cs[_cs.length] = '<table id="__calendarTable" width="100%" border="0" cellpadding="3" cellspacing="1" align="center">';
	
	_cs[_cs.length] = ' <tr>';
	_cs[_cs.length] = ' <td></td>';
	_cs[_cs.length] = '  <td colspan="5"><select class="year" name="yearSelect" id="yearSelect"><\/select><select class="quarter" name="quarterSelect" id="quarterSelect"><\/select><\/td>';
	_cs[_cs.length] = ' <td></td>';
	_cs[_cs.length] = ' <\/tr>';
	
	for(var i=0;i<this.showLine;i++){
		_cs[_cs.length] = ' <tr>';
		if(i==0)
			_cs[_cs.length] = ' <td rowspan="'+this.showLine+'"><input class="l" name="goPrevYearButton" type="button" id="goPrevYearButton" value="&lt;" \/><\/td>';
		_cs[_cs.length]='<th class="theader">2013年</th>';
		for(var j=0;j<4;j++){
			_cs[_cs.length]='<td class="quarter">'+(j+1)+'季</td>';
		}
		if(i==0)
			_cs[_cs.length] = ' <td rowspan="'+this.showLine+'"><input class="r" name="goNextYearButton" type="button" id="goNextYearButton" value="&gt;" \/><\/td>';
		_cs[_cs.length] = ' </tr>';
	}	
	
	
	
	 
	_cs[_cs.length] = '<\/table>';
	_cs[_cs.length] = '<\/form>';
	 
	this.iframe.document.body.innerHTML = _cs.join("");
	this.form = this.iframe.document.forms["__calendarForm"];
	this.form.goPrevYearButton.onclick = function () {calendar.goPrevYear(this);}
	this.form.goNextYearButton.onclick = function () {calendar.goNextYear(this);}
	this.form.yearSelect.onchange = function () {calendar.update(this);}
	this.form.quarterSelect.onchange = function () {calendar.update(this);}
	
	document.onmouseup=function(){
		calendar.onDocumentMouseup(this);
	}

};
CalendarQuarter.prototype.onDocumentMouseup=function(e){
	calendar.hide();
}

CalendarQuarter.prototype.bindYear = function() {
	var ys = this.form.yearSelect;
	ys.length = 0;
	for (var i = this.beginYear; i <= this.endYear; i++){
		ys.options[ys.length] = new Option(i + CalendarQuarter.language["year"][this.language], i);
	}
}; 

CalendarQuarter.prototype.goPrevYear = function(e){
	this.year-=this.showLine;	
	this.changeSelect();
	this.bindData();
};

CalendarQuarter.prototype.goNextYear = function(e){
	this.year+=this.showLine;	
	this.changeSelect();
	this.bindData();
};

CalendarQuarter.prototype.update = function (e){
	this.year  =parseInt( e.form.yearSelect.options[e.form.yearSelect.selectedIndex].value);
	this.quarter =parseInt( e.form.quarterSelect.options[e.form.quarterSelect.selectedIndex].value);

	this.changeSelect();
	this.bindData();
};
CalendarQuarter.prototype.changeSelect = function() {
	
	var ys = this.form.yearSelect;
	var qs = this.form.quarterSelect;
	for (var i= 0; i < ys.length; i++){
		if (ys.options[i].value == this.year){
			ys[i].selected = true;
			break;
		}
	}
	for (var i= 0; i < qs.length; i++){
		if (qs.options[i].value == this.quarter){
			qs[i].selected = true;
			break;
		}
	}
};
CalendarQuarter.prototype.bindQuarter = function() {
	
	var qs = this.form.quarterSelect;
	qs.length = 0;
	for (var i = 0; i < 4; i++){
		qs.options[qs.length] = new Option((i+1)+'季', i+1);
	}
};
 CalendarQuarter.prototype.show = function (dateControl, popuControl) {
	if (this.panel.style.visibility == "visible") {
		this.panel.style.visibility = "hidden";
	}
	if (!dateControl){
		throw new Error("arguments[0] is necessary!")
	}
	dateControl=this.getElement(dateControl);
	this.dateControl =dateControl;
	popuControl = this.getElement(popuControl) || dateControl;
	this.popuControl=popuControl;

	this.draw();
	this.bindYear();
	this.bindQuarter();
	if (this.dateControl.value.length > 0){
		var arr=this.dateControl.value.split(this.patternDelimiter);
		this.year=parseInt(arr[0]);
		this.quarter=parseInt(arr[1]);

	}
	this.changeSelect();
	this.bindData();
	//alert(this.popuControl.offsetHeight);
	var xy = this.getAbsPoint(this.popuControl);
	this.panel.style.left = xy.x + "px";
	this.panel.style.top = (xy.y +this.popuControl.offsetHeight) + "px";
	this.panel.style.visibility = "visible";
}

CalendarQuarter.prototype.hide = function() {
	this.panel.style.visibility = "hidden";
};

CalendarQuarter.prototype.bindData = function () {
	var calendar = this;	
	var ths = this.getElementsByTagName("th", this.getElementById("__calendarTable", this.iframe.document));
	for(var i=0; i<ths.length;i++){
		var year=this.year-parseInt(this.showLine/2)+i;
		ths[i].innerHTML=year+'年';
		ths[i].setAttribute("value",year);
	}
	
	var tds_quarter = this.getElementsByTagNameAndClassName("td","quarter", this.getElementById("__calendarTable", this.iframe.document));
	var j=1;
	var h=0;
	for(var i = 0; i < tds_quarter.length; i++) {
  		tds_quarter[i].style.backgroundColor = calendar.colors["bg_over"];
		tds_quarter[i].onclick = null;
		tds_quarter[i].onmouseover = null;
		tds_quarter[i].onmouseout = null;
	
		if(j>4) {
			j=1;
			h++;
		}
		tds_quarter[i].innerHTML =j+"季";
		tds_quarter[i].setAttribute("value",ths[h].getAttribute("value") +this.patternDelimiter+j);
		
		j++;
		
	 
		tds_quarter[i].onclick = function () {
				if (calendar.dateControl){					 
					
					var arr=this.getAttribute("value").split(calendar.patternDelimiter);
					 
					if(arr==null && arr.length<2){
						throw new Error("data pattern wrong !arr="+arr)
					}
					
					this.year=parseInt(arr[0]);
					this.quarter=parseInt(arr[1]);
					
					calendar.popuControl.value=this.year+"年"+this.quarter+"季";
					calendar.dateControl.value=this.getAttribute("value");
					if(calendar.onpicked)
						calendar.onpicked();
				}
				calendar.hide();
		}
		tds_quarter[i].onmouseover = function () {this.style.backgroundColor = calendar.colors["bg_out"];}
		tds_quarter[i].onmouseout  = function () {this.style.backgroundColor = calendar.colors["bg_over"];}
		
	
		if(tds_quarter[i].getAttribute("value")==this.year+this.patternDelimiter+this.quarter){			
			tds_quarter[i].style.backgroundColor = calendar.colors["bg_cur_day"];
			tds_quarter[i].onmouseover = function () {this.style.backgroundColor = calendar.colors["bg_out"];}
			tds_quarter[i].onmouseout  = function () {this.style.backgroundColor = calendar.colors["bg_cur_day"];}
		}
		
		
		
	}//end for
}
CalendarQuarter.prototype.compQuarterByMonth = function(month){
	return parseInt((month-1)/3)+1;
}
CalendarQuarter.prototype.getElement = function (id) { 
	if(id==null){
		return null;
	}
	return "string" == typeof id ? this.getElementById(id) : id; 
}; 
CalendarQuarter.prototype.getElementById = function(id, object){
	object = object || document;
	return document.getElementById ? object.getElementById(id) : document.all(id);
};

CalendarQuarter.prototype.getElementsByTagName = function(tagName, object){
	object = object || document;
	return document.getElementsByTagName ? object.getElementsByTagName(tagName) : document.all.tags(tagName);
};

CalendarQuarter.prototype.getElementsByTagNameAndClassName = function(tagName,className, object){
	object = object || document;
	var arr=new Array();
	var tagObjArr= document.getElementsByTagName ? object.getElementsByTagName(tagName) : document.all.tags(tagName);
	if(tagObjArr!=null && tagObjArr.length>0){
		for(var i=0;i<tagObjArr.length;i++){			
		//alert(tagObjArr[i].getAttribute('class'));
			if(tagObjArr[i].className=='quarter'){
				arr.push(tagObjArr[i]);
			}
		}
	}
	return arr;
};
CalendarQuarter.prototype.getAbsPoint = function (e){
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
};
document.writeln('<div id="__calendarPanel" style="position:absolute;visibility:hidden;z-index:9999;background-color:#FFFFFF;border:1px solid #666666;width:200px;height:120px;">');
document.writeln('<iframe name="__calendarIframe" id="__calendarIframe" width="100%" height="100%" scrolling="no" frameborder="0" style="margin:0px;"><\/iframe>');
var __ci = window.frames['__calendarIframe'];
__ci.document.writeln('<!DOCTYPE html PUBLIC "-\/\/W3C\/\/DTD XHTML 1.0 Transitional\/\/EN" "http:\/\/www.w3.org\/TR\/xhtml1\/DTD\/xhtml1-transitional.dtd">');
__ci.document.writeln('<html xmlns="http:\/\/www.w3.org\/1999\/xhtml">');
__ci.document.writeln('<head>');
__ci.document.writeln('<meta http-equiv="Content-Type" content="text\/html; charset=utf-8" \/>');
__ci.document.writeln('<title>Web CalendarQuarter(UTF-8) Written By KimSoft<\/title>');
__ci.document.writeln('<style type="text\/css">');
__ci.document.writeln('<!--');
__ci.document.writeln('body {font-size:12px;margin:0px;text-align:center;}');
__ci.document.writeln('form {margin:0px;}');
__ci.document.writeln('select {font-size:12px;background-color:#EFEFEF;}');
__ci.document.writeln('table {border:0px solid #CCCCCC;background-color:#FFFFFF}');
__ci.document.writeln('th {font-size:12px;font-weight:normal;background-color:#FFFFFF;}');
__ci.document.writeln('th.theader {font-weight:normal;background-color:#666666;color:#FFFFFF;}');
__ci.document.writeln('select.year {width:64px;}');
__ci.document.writeln('select.quarter {width:60px;}');
__ci.document.writeln('td {font-size:12px;text-align:center;}');
__ci.document.writeln('td.quarter {cursor:pointer;}');
__ci.document.writeln('td.sat {color:#0000FF;background-color:#EFEFEF;}');
__ci.document.writeln('td.sun {color:#FF0000;background-color:#EFEFEF;}');
__ci.document.writeln('td.normal {background-color:#EFEFEF;}');
__ci.document.writeln('input.l {border: 1px solid #CCCCCC;background-color:#EFEFEF;width:20px;height:20px;}');
__ci.document.writeln('input.r {border: 1px solid #CCCCCC;background-color:#EFEFEF;width:20px;height:20px;}');
__ci.document.writeln('input.b {border: 1px solid #CCCCCC;background-color:#EFEFEF;width:100%;height:20px;}');
__ci.document.writeln('-->');
__ci.document.writeln('<\/style>');
__ci.document.writeln('<\/head>');
__ci.document.writeln('<body>');
__ci.document.writeln('<\/body>');
__ci.document.writeln('<\/html>');
__ci.document.close();
document.writeln('<\/div>');
//var calendar = new CalendarQuarter();
