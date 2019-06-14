package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by Administrator on 2019/5/22.
 */
@Data
@ToString
@TableName("sys_ledger_list")
public class SysLedgerEntity {
    @TableId
    private Long ledgerId;          //主键id

    private String ledgerName;      //台账名称

    private Long ledgerMoney;       //台账金额

    @TableField(exist=false)
    private Double ksyzj;       //可使用资金

    @TableField(exist=false)
    private Double a;

    @TableField(exist=false)
    private Double aAll;

    @TableField(exist=false)
    private Double bAll;

    @TableField(exist=false)
    private Double cAll;

    @TableField(exist=false)
    private Double dAll;

    @TableField(exist=false)
    private Double xry;       //新锐营

    @TableField(exist=false)
    private Double yd;       //益斗

    @TableField(exist=false)
    private Double dsf;       //第三方


    private String ledgerType;      //台账状态

    private Date ledgerTime;        //时间

    private String ledgerRemarks;   //备注

    private Long calculationId;     //计算规则id
    @TableField(exist=false)
    private SysCalculationEntity calculation;      //计算规则对象

    private Long merchantId;        //商户id
    @TableField(exist=false)
    private SysMerchantEntity merchant;            //商户对象

    private String merchantType;            //商户类型
}
