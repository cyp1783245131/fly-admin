package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMembersEntity;

import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
public interface SysMembersService extends IService<SysMembersEntity> {
    //查询
    PageUtils queryPage(Map<String, Object> params);
}
