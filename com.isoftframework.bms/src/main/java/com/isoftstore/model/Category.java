package com.isoftstore.model;

import java.util.ArrayList;
import java.util.List;

import com.isoftframework.model.DtoSupport;

public class Category extends DtoSupport{

	public static String ROOT_ID="root";
	private String id;
	private String text;
	private String parentId;
	private Integer sort;
	private boolean leaf;
	private String img;
	
	private List<Category> children = new ArrayList<Category>();
	
	
	private String columnId;
	
	private String columnName;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public int getLevel() {
    	
    	if(this.id.equalsIgnoreCase(ROOT_ID)){
    		return 0;
    	}
    	int templevel=1;
    	int idx=-1;
    	while((idx=id.indexOf("-",idx+1))!=-1){
    		templevel++;
    	}
    	return templevel; 
    }
    public synchronized boolean add(Category child) {
    	int meLevlel=this.getLevel();
    	int cLevel=child.getLevel();
        if(meLevlel>=cLevel)
            return false;
        if((cLevel - meLevlel) == 1) {
            if((child.getParentId()).equals(this.id) ) {
                // direct child:
                this.children.add(child);
                return true;
            }
            return false;
        }
        for(Category c : this.children) {
            if(c.add(child))
                return true;
        }
        return false;
    }
    public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
    public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
