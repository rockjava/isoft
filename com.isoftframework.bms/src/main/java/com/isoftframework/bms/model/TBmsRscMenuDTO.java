package com.isoftframework.bms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.isoftframework.model.DtoSupport;
//@JsonIgnoreProperties( value={"hibernateLazyInitializer","handler"})
public class TBmsRscMenuDTO extends DtoSupport
{
	
	private java.lang.String id;


	private String parentId;
	//路径
	private String url;

	// 名称
	private java.lang.String text;
	
	// 显示图标
	private java.lang.String icon;
	

	// 描述
	private java.lang.String remark;

	// 类型（1：菜单；2：功能；3：资源）
	private char type;

	// 是否末级菜单（0：否；1：是）
	private Boolean menuLeaf=false;

	//排序字段:排序算法: 父级排序编号 * 100  + (本级最大级+1)，注：排序号为uniqun约束
	private Long sortid;
	
	//适配器类
	private String adaptClass;
	
	//是否叶子节点 
	private Boolean leaf=false;
	
	private String targetContainer;



	private List childNodes;

	// 关联的资源或链接
	private List tbTBmsRscResourceDTOList;

	// 关联的角色权限对应关系
	private List tbTBmsRoleFuncDTOList;

	public TBmsRscMenuDTO()
	{
	}

	public TBmsRscMenuDTO(java.lang.String id)
	{
		 this.id=id;
	}
	
	public List getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List childNodes) {
		this.childNodes = childNodes;
	}
	public List getTbTBmsRscResourceDTOList() {
		return tbTBmsRscResourceDTOList;
	}

	public void setTbTBmsRscResourceDTOList(List tbTBmsRscResourceDTOList) {
		this.tbTBmsRscResourceDTOList = tbTBmsRscResourceDTOList;
	}

	public List getTbTBmsRoleFuncDTOList() {
		return tbTBmsRoleFuncDTOList;
	}

	public void setTbTBmsRoleFuncDTOList(List tbTBmsRoleFuncDTOList) {
		this.tbTBmsRoleFuncDTOList = tbTBmsRoleFuncDTOList;
	}

 

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public java.lang.String getText() {
		return text;
	}

	public void setText(java.lang.String text) {
		this.text = text;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getIcon() {
		return icon;
	}

	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	 
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public Boolean isMenuLeaf() {
		return menuLeaf;
	}

	public void setMenuLeaf(Boolean menuLeaf) {
		this.menuLeaf = menuLeaf;
	}

	public Long getSortid() {
		return sortid;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

	public String getAdaptClass() {
		return adaptClass;
	}

	public void setAdaptClass(String adaptClass) {
		this.adaptClass = adaptClass;
	}
	public Boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	 
	public String getTargetContainer() {
		return targetContainer;
	}

	public void setTargetContainer(String targetContainer) {
		this.targetContainer = targetContainer;
	}
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getMenuId() {
		return id;
	}

	public void setMenuId(java.lang.String menuId) {
		this.id = menuId;
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof TBmsRscMenuDTO))
			return false;
		TBmsRscMenuDTO castOther = (TBmsRscMenuDTO) other;
		if (this.getMenuId() == null)
		{
			if (castOther.getMenuId() == null)
				return true;
			else
				return false;
		}
		return this.getMenuId().equals(castOther.getMenuId());
	}

	

}