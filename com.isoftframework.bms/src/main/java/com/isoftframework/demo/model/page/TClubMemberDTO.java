package com.isoftframework.demo.model.page;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.isoftframework.model.DtoSupport;
 
@Entity(name="TClubMemberDTO")
@Table(name="demo_club_member")
public class TClubMemberDTO extends DtoSupport{
	
	@Id
	private java.lang.String id;

	@Column(name = "scode")
	private java.lang.String scode;

	@Column(name = "spass")
	private java.lang.String spass;

	@Column(name = "sname")
	private java.lang.String sname;
	
	@Column(name = "create_time")
	private java.util.Date createTime;
	
	@Column(name = "is_locked")
	private Integer isLocked;
	
	private String clubChannelDTOId;
	
	private  TClubChannelDTO clubChannelDTO;

	
	
	public java.lang.String getId() {
		return id;
	}


	public void setId(java.lang.String id) {
		this.id = id;
	}


	public java.lang.String getScode() {
		return scode;
	}


	public void setScode(java.lang.String scode) {
		this.scode = scode;
	}


 


	public java.lang.String getSpass() {
		return spass;
	}

	public void setSpass(java.lang.String spass) {
		this.spass = spass;
	}

	public java.lang.String getSname() {
		return sname;
	}


	public void setSname(java.lang.String sname) {
		this.sname = sname;
	}


	public java.util.Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


	public String getClubChannelDTOId() {
		return clubChannelDTOId;
	}


	public void setClubChannelDTOId(String clubChannelDTOId) {
		this.clubChannelDTOId = clubChannelDTOId;
	}


	public TClubChannelDTO getClubChannelDTO() {
		return clubChannelDTO;
	}


	public void setClubChannelDTO(TClubChannelDTO clubChannelDTO) {
		this.clubChannelDTO = clubChannelDTO;
	}


	public Integer getIsLocked() {
		return isLocked;
	}


	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}


	public  TClubMemberDTO()
	{
		super();
	}


	public  TClubMemberDTO(java.lang.String id)
	{
		this.id=id;
	}
	
	


	public boolean equals(Object other)
	{		if ( !(other instanceof TClubMemberDTO) )
			return false;
		TClubMemberDTO castOther = (TClubMemberDTO) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getId()).toHashCode();
	}


}