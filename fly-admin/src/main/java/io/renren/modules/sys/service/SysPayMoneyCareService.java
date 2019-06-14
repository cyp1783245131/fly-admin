package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysPayMoneyCareEntity;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
public interface SysPayMoneyCareService extends IService<SysPayMoneyCareEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);
}
