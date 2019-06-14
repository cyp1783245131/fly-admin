package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * Created by Administrator on 2019/5/20.
 */
@Data
@TableName("sys_calculation")
public class SysCalculationEntity {
    @TableId
    private Long calculationId;

    private String rule;

    private Date createTime;

    private Date endTime;

    private Double usableQuota;

    private Double company1;

    private Double company2;

    private Double thirdparty;
}
