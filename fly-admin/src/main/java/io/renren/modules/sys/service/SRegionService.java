package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SRegionEntity;

import java.util.Map;

public interface SRegionService extends IService<SRegionEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
