package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/27.
 */
@Data
@TableName("sys_paySearch")
public class SysPaySearchEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    private Date stime;

    private String sid;

    private String spid;

    private String stype;

    private Double smoney;

    private Double spay;

    private Double smy;

    private Double save;

    @TableField(exist=false)
    private List<SysDetEntity> sysDetEntityList;   //商户集合

}
