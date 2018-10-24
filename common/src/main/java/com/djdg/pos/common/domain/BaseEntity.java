/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */
package com.djdg.pos.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "version")
    protected Integer version;//版本号，乐观锁防止并发修改问题

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createTime;//创建eng

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateTime;//更新时间

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    void createdAt() {
        this.createTime = this.updateTime = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updateTime = new Date();
    }
}