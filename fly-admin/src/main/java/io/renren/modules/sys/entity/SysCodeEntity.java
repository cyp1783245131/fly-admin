package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/15.
 */
@Data
@TableName("sys_code")
public class SysCodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 节点名称
     */
    private String cname;

    /**
     * 节点状态
     */
    private Integer status;

}
