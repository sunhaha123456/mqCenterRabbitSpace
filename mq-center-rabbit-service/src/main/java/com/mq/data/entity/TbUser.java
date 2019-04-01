package com.mq.data.entity;

import com.mq.common.data.base.BaseDataIdLong;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_user")
public class TbUser extends BaseDataIdLong {
	@Column(name = "uname", columnDefinition = "varchar(255) binary COMMENT '用户名'")
	private String uname;
	@Column(name = "upwd", columnDefinition = "varchar(255) binary COMMENT '密码'")
	private String upwd;
}