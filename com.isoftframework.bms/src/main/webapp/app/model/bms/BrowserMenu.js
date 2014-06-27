Ext.define('FM.model.bms.BrowserMenu', {
    extend: 'Ext.data.Model',
    requires:['FM.model.Menu'],
    fields: [
        { name: 'menuId', type: 'string' },
        { name: 'sort', type: 'int' } ,
        { name:'menu',type:Ext.data.Types.MENU}
    ],
    idProperty: 'menuId'
   
    /*belongsTo : {
			model : 'Ext.data.Model',
			primaryKey : 'menuId',
			foreignKey : 'menuId'
		}*/
    /*
		associations: [
       { type: 'belongsTo', model: 'FM.model.Menu',primaryKey : 'menuId',foreignKey : 'menuId' }
    ],
    getMenu:function(){
    	
    		 var menu=Ext.create(FM.model.Menu);
			   this.getMenu(function(menu_, operation) {
		      // do something with the category object
		      console.log(menu_); // alerts 20
		      menu.set('id',menu_.get('id'));
		      menu.set('icon',menu_.get('icon'));
		      menu.set('text',menu_.get('text'));
		  	}, this);
			  return menu;
		}*/
   
});