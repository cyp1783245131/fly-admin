package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 引流列表
 * Created by Sy on 2019/5/7.
 */
@Data
@TableName("sys_drainage")
public class SysDrainageEntity {
    @TableId
    private Integer cid;
    @NotBlank(message="计划名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String planName;        //计划名称
    @NotBlank(message="开始时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Date createTime;        //开始时间
    @NotBlank(message="结束时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Date endTime;           //结束时间
    @NotBlank(message="范围不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String fanwei;           //范围
    @NotBlank(message="引流位置不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String position;        //引流位置
    @NotBlank(message="计划流量不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String planFlow;        //计划流量
    @NotBlank(message="计划状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String planType;        //计划状态
}
