package com.isoftframework.demo.model.page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.isoftframework.model.DtoSupport;

 
@Entity(name="TClubChannelDTO")
@Table(name="demo_club_channel")
public class TClubChannelDTO extends DtoSupport{
	
	@Id
	private java.lang.String id;

	@Column(name = "scode")
	private java.lang.String scode;

	@Column(name = "sname")
	private java.lang.String sname;

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


	public java.lang.String getSname() {
		return sname;
	}


	public void setSname(java.lang.String sname) {
		this.sname = sname;
	}


	public  TClubChannelDTO()
	{
		super();
	}


	public  TClubChannelDTO(java.lang.String id)
	{
		this.id=id;
	}


	public boolean equals(Object other)
	{		if ( !(other instanceof TClubChannelDTO) )
			return false;
		TClubChannelDTO castOther = (TClubChannelDTO) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}


	public int hashCode()
	{
		return new HashCodeBuilder().append(getId()).toHashCode();
	}


}