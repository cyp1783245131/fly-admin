package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xw on 2019/6/5.
 */
@Data
@TableName("sys_pmoney")
public class SysPmoneyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    private String monid;

    private Integer pcount;

    private Double pmoney;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ptime;

    private Double pay;

    private String pname;

}
