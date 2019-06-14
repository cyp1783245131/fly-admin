package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysDrainageDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysDrainageEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysDrainageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by Sy on 2019/5/7.
 */
@Service("sysDrainageService")
public class SysDrainageServiceImpl extends ServiceImpl<SysDrainageDao, SysDrainageEntity> implements SysDrainageService {
    @Autowired
    private SysDrainageService sysDrainageService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String planName = (String)params.get("planName");

        IPage<SysDrainageEntity> page = this.page(
                new Query<SysDrainageEntity>().getPage(params),
                new QueryWrapper<SysDrainageEntity>()
                        .like(StringUtils.isNotBlank(planName),"plan_name", planName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDrainage(SysDrainageEntity drainage) {
        this.save(drainage);
    }

    @Override
    public void updateDrainage(SysDrainageEntity drainage) {
        this.updateById(drainage);
    }

    @Override
    public SysDrainageEntity getDrainageById(Integer id) {
        return this.getDrainageById(id);
    }
}
