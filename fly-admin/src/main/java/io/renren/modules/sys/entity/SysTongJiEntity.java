package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/17.
 */
@Data
@TableName("sys_tongji")
public class SysTongJiEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    private String tcar;

    /**
     * 开始时间
     */
    private Date stime;

    /**
     * 结束时间
     */
    private Date etime;

    private String tlevel;

    private String tlast;


}
