package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * Created by Administrator on 2019/5/13.
 */
@Data
@ToString
@TableName("sys_merchant")
public class SysMerchantEntity {
    @TableId
    private Long mid;   //id

    @NotBlank(message="租户名称", groups = {AddGroup.class, UpdateGroup.class})
    private String merchantName;

    @NotBlank(message="租户简称", groups = {AddGroup.class, UpdateGroup.class})
    private String merchantJiancheng;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String contractCreateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String contractEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String openTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @NotBlank(message="合同编号", groups = {AddGroup.class, UpdateGroup.class})
    private String contraceNumber;

    @NotBlank(message="开通状态", groups = {AddGroup.class, UpdateGroup.class})
    private String merchantType;

    private String flag1;

    private String flag2;

    private String flag3;

}
