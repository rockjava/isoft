package com.isoftframework.bms.model;

import com.isoftframework.model.DtoSupport;


public class TBmsUserDTO extends DtoSupport {

	
	public TBmsUserDTO(){}
	 
	private java.lang.String id;
	
	private java.lang.String username;
	
	private java.lang.String password;
	
	private java.lang.String salt;
	
	private char enabled;
	
	private char sex;
	
	private java.lang.String realName;
	
	private java.lang.String mobile;
	
	private java.lang.String email;
	
	private java.lang.String fax;
	
	private java.lang.String phone;
	
	private java.lang.String address;
	
	private java.lang.String remark;

	private java.lang.String orgid;

	private String orgName;
	
	private   TBmsOrgDTO org;
	
	public TBmsOrgDTO getOrg() {
		return org;
	}
	public void setOrg(TBmsOrgDTO org) {
		this.org = org;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getSalt() {
		return salt;
	}
	public void setSalt(java.lang.String salt) {
		this.salt = salt;
	}
	public char getEnabled() {
		return enabled;
	}
	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getFax() {
		return fax;
	}
	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getOrgid() {
		return orgid;
	}
	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	



}