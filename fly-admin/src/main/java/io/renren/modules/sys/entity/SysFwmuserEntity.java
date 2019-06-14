package io.renren.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 引流计划
 */
@Data
@TableName("sys_fwmuser")
public class SysFwmuserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 手机号ID
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 部门编码
     */
    private Long deptid;
    /**
     * 身份证号码
     */
    private String certifynum;
    /**
     * 城市编码
     */
    private Integer cityid;
    /**
     * 城市名称
     */
    private String cityname;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    @TableField(exist = false)
    private String cpassword;
    /**
     * 银行卡号
     */
    private String banknum;
    /**
     * 银行名称
     */
    private String bankname;
    /**
     * 银行开户行
     */
    private String openbank;
    /**
     * 创建时间
     */
    private String ctime;
    /**
     * 修改时间
     */
    private String utime;

}
