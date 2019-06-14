package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysFwmuserEntity;

import java.util.Map;

public interface SysFwmuserService extends IService<SysFwmuserEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
