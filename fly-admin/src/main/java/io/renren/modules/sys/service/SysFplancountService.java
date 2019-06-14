package io.renren.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysFplancountEntity;

import java.util.Map;

public interface SysFplancountService extends IService<SysFplancountEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
