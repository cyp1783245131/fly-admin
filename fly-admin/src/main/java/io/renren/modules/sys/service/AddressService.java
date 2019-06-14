package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.AddressEntity;

import java.util.Map;

public interface AddressService extends IService<AddressEntity> {
    PageUtils queryPage(Map<String, Object> params);

}
