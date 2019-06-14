package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysFailEntity;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
public interface SysFailService extends IService<SysFailEntity> {
    //查询
    PageUtils queryPage(Map<String, Object> params);
}
