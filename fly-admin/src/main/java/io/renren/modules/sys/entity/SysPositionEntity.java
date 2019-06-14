package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by Sy on 2019/5/7.
 */
@Data
@TableName("sys_position")
public class SysPositionEntity {
    @TableId
    private Integer pid;
    private String pname;
}
