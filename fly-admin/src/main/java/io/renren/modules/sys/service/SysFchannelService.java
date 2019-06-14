package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysFchannelEntity;

import java.util.Map;

public interface SysFchannelService extends IService<SysFchannelEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
