package io.renren.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 引流渠道
 */
@Data
@TableName("sys_fchannel")
public class SysFchannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     *渠道名称
     */
    private String cname;

    /**
     *渠道图片
     */
    private String cimage;
    /**
     *渠道url
     */
    private String curl;
    /**
     *微盟用户
     */
    private String cwm;
    /**
     *渠道文案
     */
    private String cwenan;
    /**
     * 渠道状态  0：禁用   1：正常
     */
    private String cstatus;
    /**
     * 创建时间
     */
    private String ctime;
    /**
     * 更新时间
     */
    private String utime;
}
