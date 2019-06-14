package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/27.
 */
@Data
@TableName("sys_pay")
public class SysPayEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 操作员ID
     */
    private String pid;

    /**
     * 充值时间
     */
    private Date ptime;

    /**
     * 充值账户
     */
    private String pname;

    /**
     * 充值金额
     */
    private Double pmoney;

    /**
     * 账户余额
     */
    private Double psave;

    /**
     * 状态
     */
    private Integer pstatus;

}
