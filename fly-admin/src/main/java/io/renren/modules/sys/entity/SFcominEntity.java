package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 引流统计
 */
@Data
@TableName("s_fcomin")
public class SFcominEntity {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 计划编号
     */
    private Long fplanid;
    /**
     * 生成规则id
     */
    private Long rule;
    /**
     * ip地址
     */
    private String fip;

    /**
     * 服务日期
     */
    private String fdate;
    /**
     * 服务时间
     */
    private String ftime;
    /**
     * 产品总体服务次数
     */
    private String fbrowses;
    /**
     * 产品有效服务次数
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
