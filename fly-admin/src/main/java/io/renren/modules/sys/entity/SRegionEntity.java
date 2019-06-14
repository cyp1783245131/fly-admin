package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 区域规则
 */
@Data
@TableName("s_region")
public class SRegionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 规则名称
     */
    private String rname;
    /**
     * 符合区域比例
     */
    private String rsure;
    /**
     * 不符合区域比例
     */
    private String rnsure;
}
