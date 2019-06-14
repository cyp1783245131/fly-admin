package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysCalculationEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/20.
 */
public interface SysCalculationService extends IService<SysCalculationEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveCalculation(SysCalculationEntity calculation);

    void updateCalculation(SysCalculationEntity calculation);
}
