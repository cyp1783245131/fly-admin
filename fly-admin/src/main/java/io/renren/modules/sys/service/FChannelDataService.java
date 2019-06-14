package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.entity.SFplancountEntity;

import java.util.List;
import java.util.Map;

public interface FChannelDataService extends IService<FChannelDataEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveOrUpdateChannelDatas(SysFplancountEntity fPlan);

    void saveOrUpdateChannelDatas(SFplancountEntity fPlan);

    List<FChannelDataEntity> queryListByPlanId(Long planId);
}
