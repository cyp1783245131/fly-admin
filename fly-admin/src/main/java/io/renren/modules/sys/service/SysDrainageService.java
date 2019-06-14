package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDrainageEntity;


import java.util.Map;

/**
 * Created by Sy on 2019/5/7.
 */
public interface SysDrainageService extends IService<SysDrainageEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveDrainage(SysDrainageEntity drainage);

    void updateDrainage(SysDrainageEntity drainage);

    SysDrainageEntity getDrainageById(Integer id);
}
