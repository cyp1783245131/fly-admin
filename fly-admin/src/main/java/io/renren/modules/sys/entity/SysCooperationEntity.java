package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/5/21.
 */
@Data
@TableName("sys_cooperation")
public class SysCooperationEntity {
    @TableId
    private Long cooperationId;

    private Long merchantId;        //商户id.
    @TableField(exist=false)
    private SysMerchantEntity merchantEntity;   //商户对象

    @TableField(exist=false)
    private List<SysMerchantEntity> merchantEntityList;   //商户集合

    private String cooperationType;     //合作类型
}
