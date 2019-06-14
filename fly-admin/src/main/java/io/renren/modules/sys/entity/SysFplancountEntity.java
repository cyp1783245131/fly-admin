package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 引流计划
 */
@Data
@TableName("sys_fplancount")
public class SysFplancountEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 计划名称
     */
    private String dname;

    /**
     * 开始时间
     */
    private String kaishi;
    /**
     * 结束时间
     */
    private String jieshu;
    /**
     * 范围id
     * 部门id
     */
    private String fid;
    private Long deptid;
    /**
     * 范围
     */
    private String fanwei;
    /**
     * 引流位置
     */
    private String location;
    /**
     * 计划流量(万)
     */
    private String flow;
    /**
     * 实际流量(万)
     */
//    private String actual;
    @TableField(exist = false)
    private String actual;

    @TableField(exist = false)
    private List<FChannelDataEntity> fChannelDataList;
    /**
     * 计划状态  0：禁用   1：正常
     */
    private Integer state;
    /**
     * 微盟用户
     */
    private String planwms;
    /**
     * 创建用户
     */
    private Long cuser;
    /**
     * 创建时间
     */
    private String ctime;
    /**
     * 修改时间
     */
    private String utime;


}
