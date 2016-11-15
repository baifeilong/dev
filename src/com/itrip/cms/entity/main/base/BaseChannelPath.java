package com.itrip.cms.entity.main.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the jc_channel table. Do not
 * modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 * 
 * @hibernate.class table="jc_channel"
 */

public abstract class BaseChannelPath implements Serializable {

	private static final long serialVersionUID = 1L;

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String oldPath;
	private java.lang.Integer newPath;

	public void setOldPath(java.lang.String oldPath) {
		this.oldPath = oldPath;
	}

	public java.lang.String getOldPath() {
		return oldPath;
	}

	public void setNewPath(java.lang.Integer newPath) {
		this.newPath = newPath;
	}

	public java.lang.Integer getNewPath() {
		return newPath;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.itrip.cms.entity.main.ChannelPath))
			return false;
		else {
			com.itrip.cms.entity.main.ChannelPath channel = (com.itrip.cms.entity.main.ChannelPath) obj;
			if (null == this.getId() || null == channel.getId())
				return false;
			else
				return (this.getId().equals(channel.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}
}