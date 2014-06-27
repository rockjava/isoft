//FM.ux.window.SlideWindow
Ext.define('FM.ux.window.SlideWindow', {
	extend : "Ext.panel.Panel",
	//extend : "Ext.window.Window",
	floating: true,
	header:false,
	/*split: true,
	collapsible : true,
	
	collapseDirection:'right',*/
	
	//buttonAlign:'left',
	//autoScroll:true,
	slideable:true,
	layout:'fit',
	//closable: false,
	//modal:false,
	//floatable : false,
	 
	initComponent : function(){
		
		var me=this;
		me.dockedItems=[{
            xtype: 'toolbar',
            dock: 'top',
            items: [{
            	xtype:'tool',
            	type: 'right',
                text: 'Docked to the top',
                handler: Ext.Function.bind(me.slideOut, me, [])
            },{
               xtype: 'tbtext', 
               text: (this.title?this.title:"")
            }]
            
        }];
		this.callParent(arguments);
		if (me.modal) {
           // me.ariaRole = 'dialog';
        }
		  // clickToRaise
        if (me.floating) {
            me.on({
                element: 'el',
               // mousedown: me.onMouseDown,
                scope: me
            });
        }
       
		/*me.addEvents(
            'mouseover',
            'mouseout',
            'menutriggerout'
        );*/
	},
	onMouseDown: function (e) {
        var preventFocus;
            
        if (this.floating) {
            if (Ext.fly(e.getTarget()).focusable()) {
                preventFocus = true;
            }
            this.toFront(preventFocus);
        }
    },
	
	onRender: function() {
		
        var me = this,
	        mouseListeners ,
		    panelEl;
       
        // Add whatever button listeners we need
       // panelEl=me.el;
        mouseListeners = {
	        scope: me,
	        mouseover: me.onMouseOver,
	        mouseout: me.onMouseOut
	    };
        me.mon(me.el, mouseListeners);
        me.callParent(arguments);
        me.doc = Ext.getDoc();
	},
	/**
	 * 从slideTarget的右侧向左滑出
	 */
	slideShow : function(slideTarget){
		var me=this;
		
		this.slideIn(slideTarget);
	},
	slideIn : function(slideTarget,duration) {
		var me=this,myDuration=1000;
		if(duration){
			myDuration=duration;
		}
		
		if(slideTarget && (!me.slideTarget || me.slideTarget != slideTarget)){
			me.slideTarget=slideTarget;
			var fromBox = slideTarget.getBox();
			me.showAt(fromBox.x + fromBox.width - this.width-1, fromBox.y+1, true);
			me.hide();
		}
		
		if (me.isVisible()) {
			me.hide();
			// this.myPanel.el.slideOut('r', { duration: 2000 });
		}
		
		if(me.isHidden() ){
			me.getEl().slideIn('r', {
				duration : myDuration,
				listeners: {
	                afteranimate: function() {
	                   // me.isSliding = false;
	                	//me.closeAfterATime(5000);
	                	console.log("--afteranimate");
	                	me.mon(me.doc, 'mousedown', me.mimicBlur, me, {
	        	            delay: 10
	        	        });
	                }
	            }
			});
			
		}
		
		
		
	},
	slideOut:function(duration){
		var me=this,
			myDuration=1000;
		if(duration){
			myDuration=duration;
		}
		me.mun(me.doc, 'mousedown', me.mimicBlur, me);
		me.getEl().slideOut('r', { duration: myDuration});
	},
	
	mimicBlur: function(e) {
        if (!this.isDestroyed && !this.getEl().contains(e.target) ) {
            this.triggerBlur(e);
        }
    },
    
    triggerBlur: function(e) {
        var me = this;
       
        me.slideOut();
        console.log('------doc clicked---');
    },
	closeAfterATime:function(timeOut){
		var me=this,
		  	myTimeOut=2000;
		if(timeOut){
			myTimeOut=timeOut;
		}
		var closeWindow = function () {
		   me.slideOut();
		  
		};
		if(this.timeOut){
			window.clearTimeout(this.timeOut);
		}
		this.timeOut=window.setTimeout(closeWindow,myTimeOut);
		 
		
	},
	closeAfterATime_:function(){
		var me=this,
		task=null;
		if(!me.runner){
			me.runner = new Ext.util.TaskRunner();
		}
		var myRunner=me.runner;
		var closeWindow = function () {
		   me.slideOut();
		   myRunner.stop(task);
		};
		task = myRunner.start({
		     run: closeWindow,
		     interval: 5000,
		     repeat: 1
		});
		
	},
	
	
	
	 /**
     * @private mouseover handler called when a mouseover event occurs anywhere within the encapsulating element.
     * The targets are interrogated to see what is being entered from where.
     * @param e
     */
    onMouseOver: function(e) {
    	console.log('-------onMouseOver');
        var me = this;
        //me.slideIn();
    },
    
   
    
    onMouseOut: function(e) {
    	//console.log('-------onMouseOut');
        var me = this;
        //me.slideOut();
    }
    
   
	
});