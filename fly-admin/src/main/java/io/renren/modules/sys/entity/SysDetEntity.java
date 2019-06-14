package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/29.
 */
@Data
@TableName("sys_det")
public class SysDetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    private String dsid;

    private String dspid;

    private Double dpay;

    private Date dtime;

    private String dusername;

    private String dbank;

    private String dbankname;

    private String dbanknum;

    private Double dmoney;

    private Long psid;


}
