package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 生成规则
 */
@Data
@TableName("s_generate")
public class SGenerateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Id
    private Long id;
    /**
     * 规则名称
     */
    private String rname;
    /**
     * 符合区域
     */
    private String rsure;
    /**
     * 天数
     */
    private String gdate;
}
