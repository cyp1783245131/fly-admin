package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/6.
 */
@Data
@TableName("sys_yinliu")
public class SysYinLiuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 开始时间
     */
    private Date stime;

    /**
     * 结束时间
     */
    private Date etime;

    /**
     * 引流范围
     */
    private String ranges;

    /**
     * 引流位置
     */
    private String adds;

    /**
     * 引流计划（万）
     */
    private Integer meth;

    /**
     * 引流状态
     */
    private Integer status;
}
