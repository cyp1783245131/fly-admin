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
@TableName("sys_payStatus")
public class SysPayStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    private String tid;

    private Integer tnum;

    private Double tpay;

    private Date stime;

    private Date ptime;

    private Double pay;

    private String pstatus;

    private String remarks;

}
