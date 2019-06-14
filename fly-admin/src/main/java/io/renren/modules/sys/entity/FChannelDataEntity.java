package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 计划渠道数据表
 *
 */
@Data
@TableName("sys_fchannel_data")
public class FChannelDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据编号
	 */
	@TableId
	private Long id;
	/**
	 * 渠道编号
	 */
	private Long cid;
	/**
	 * 计划编号
	 */
	private Long cplanid;
	/**
	 * 渠道图片
	 */
	private String cname;
	/**
	 * 渠道链接
	 */
	private String cimage;
	/**
	 * 渠道链接
	 */
	private String curl;
	/**
	 * 微盟推送
	 */
	private String cwm;
	/**
	 * 渠道文案
	 */
	private String cwenan;
	/**
	 * 创建时间
	 */
	private String ctime;
	/**
	 * 修改时间
	 */
	private String utime;

}
