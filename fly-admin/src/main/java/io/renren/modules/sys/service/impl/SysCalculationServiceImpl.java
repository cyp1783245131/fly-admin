package io.renren.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysCalculationDao;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/20.
 */
@Service("sysCalculationService")
public class SysCalculationServiceImpl extends ServiceImpl<SysCalculationDao, SysCalculationEntity> implements SysCalculationService {

    @Autowired
    private SysCalculationDao sysCalculationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String rule = (String)params.get("rule");

        IPage<SysCalculationEntity> page = this.page(
                new Query<SysCalculationEntity>().getPage(params),
                new QueryWrapper<SysCalculationEntity>()
                        .like(StringUtils.isNotBlank(rule),"rule", rule)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public void saveCalculation(SysCalculationEntity calculation) {
        this.save(calculation);
    }

    @Override
    public void updateCalculation(SysCalculationEntity calculation) {
        this.updateById(calculation);
    }
}
