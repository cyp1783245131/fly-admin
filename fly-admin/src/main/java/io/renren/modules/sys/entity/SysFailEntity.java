package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by xw on 2019/6/4.
 */
@Data
@TableName("sys_fail")
public class SysFailEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    private String pid;

    private String pcat;

    private Double pay;

    private Double pmoney;

    private String rule;

    private String pname;

    private String pbank;

    private String pobank;

    private String pbanknum;

    private String pbeacause;
}
