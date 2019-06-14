package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysCooperationDao;
import io.renren.modules.sys.entity.SysCooperationEntity;
import io.renren.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/21.
 */
@Service("sysCooperationService")
public class SysCooperationServiceImpl extends ServiceImpl<SysCooperationDao, SysCooperationEntity> implements SysCooperationService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String cooperationId = (String)params.get("cooperationId");

        IPage<SysCooperationEntity> page = this.page(
                new Query<SysCooperationEntity>().getPage(params),
                new QueryWrapper<SysCooperationEntity>()
                        .like(StringUtils.isNotBlank(cooperationId),"cooperation_id", cooperationId)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public void updateCooperation(SysCooperationEntity cooperation) {
        this.updateById(cooperation);
    }

    @Override
    public void saveCooperation(SysCooperationEntity cooperation) {
        this.save(cooperation);
    }
}
