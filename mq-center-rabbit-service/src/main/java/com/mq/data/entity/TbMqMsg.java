package com.mq.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mq.common.data.base.BaseDataIdLong;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 功能：消息表
 * @author sunpeng
 * @date 2019
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tb_mq_msg")
public class TbMqMsg extends BaseDataIdLong {

	// 要推送的消息内容
	@Column(name = "request_push_msg_content", columnDefinition = "varchar(500) COMMENT '要推送的消息内容'")
	private String requestPushMsgContent;

	// 请求推送方平台
	// 0：（管理人员）手动构建消息 1：xxx平台 2：yyy平台 3：zzz平台
	@Column(name = "request_push_platform", columnDefinition = "INT COMMENT '请求推送方平台'")
	private Integer requestPushPlatform;

	@Transient
	private String requestPushPlatformStr;

	// 主动构建消息的管理员id
	// 备注：当请求推送方平台是 0，表示 管理人员主动新建消息时，使用
	@Column(name = "active_build_mq_msg_user_id", columnDefinition = "INT COMMENT '主动构建消息的管理员id'")
	private Long activeBuildMqMsgUserId;

	// 请求推送方备注
	@Column(name = "request_push_remark", columnDefinition = "varchar(255) COMMENT '请求推送方备注'")
	private String requestPushRemark;

	// 请求送达地址
	@Column(name = "request_push_dest_addr", columnDefinition = "varchar(255) COMMENT '请求送达地址'")
	private String requestPushDestAddr;

	// 请求间隔多少秒后送达
	@Column(name = "request_push_interval_second", columnDefinition = "varchar(255) DEFAULT 0 COMMENT '请求间隔多少秒后送达'")
	private Long requestPushIntervalSecond;

	// 推送状态
	// 0：未推送 1：推送失败 2：推送成功
	@Column(name = "status", columnDefinition = "TINYINT DEFAULT 0 COMMENT '推送状态'")
	private Integer status;

	// 比如：2019-04-10 12:12:12，推送成功 或 推送失败
	@Transient
	private String statusStr;

	// 送达时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deliver_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date deliverDate;

	// 累计推送次数
	@Column(name = "total_push_count", columnDefinition = "INT DEFAULT 0 COMMENT '累计推送次数'")
	private Integer totalPushCount;

	@Transient
	private List<TbMqMsgPushReleation> pushRecordList;
}