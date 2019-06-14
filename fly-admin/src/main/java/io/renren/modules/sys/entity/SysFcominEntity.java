package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 引流统计
 */
@Data
@TableName("sys_fcomin")
public class SysFcominEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 计划编码
     */
    private Long fplanid;
    /**
     * ip地址
     */
    private String fip;
    /**
     * 来源
     */
    private String fsource;
    /**
     * 日期
     */
    private String fdate;
    /**
     * 时间
     */
    private String ftime;
    /**
     * 浏览次数
     */
    private String fbrowse;
    /**
     * 预计转化率
     */
    private String fconversion;
    /**
     * 访问地址
     */
    private String faddress;
    /**
     * 引流区域
     */
    private String farea;
    /**
     * 是否区域内
     */
    private String fisarea;
    /**
     * 创建时间
     */
    private String cdate;
    /**
     * 更新时间
     */
    private String udate;
}