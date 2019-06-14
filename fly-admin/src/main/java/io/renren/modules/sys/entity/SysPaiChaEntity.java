package io.renren.modules.sys.entity;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/15.
 */
@Data
@TableName("sys_paicha")
public class SysPaiChaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 项目名称
     */
    private String pname;

    /**
     * 开始时间
     */
    private Date stime;

    /**
     * 结束时间
     */
    private Date etime;

    /**
     * 节点名称
     */
    private String cname;

    /**
     * 导入数据
     */
    private String paths;

    /**
     * 数量
     */
    private Integer number;

    /**排查进度
     */
    private String speed;

    /**
     * 项目状态
     */
    private Integer status;

}
