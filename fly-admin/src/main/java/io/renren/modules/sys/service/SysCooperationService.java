package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysCooperationEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/21.
 */
public interface SysCooperationService extends IService<SysCooperationEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void updateCooperation(SysCooperationEntity cooperation);

    void saveCooperation(SysCooperationEntity cooperation);
}
