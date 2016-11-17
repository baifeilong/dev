package com.itrip.cms.entity.main.base;

import java.io.Serializable;
import java.util.Date;

/**
 * This is an object that contains data related to the legislation table. Do not
 * modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class table="legislation"
 */

public abstract class BaseCmsLegislation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String name1;
    private String phone1;
    private String address1;
    private String track;
    private String remark;
    private Date createTime;
    private String wordTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWordTime() {
        return wordTime;
    }

    public void setWordTime(String wordTime) {
        this.wordTime = wordTime;
    }
}