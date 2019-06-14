package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SGenerateEntity;

import java.util.List;
import java.util.Map;

public interface SGenerateService  extends IService<SGenerateEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<SGenerateEntity>  selectByDays(String days);
}
