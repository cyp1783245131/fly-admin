package io.renren.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SRegionDao;
import io.renren.modules.sys.entity.SRegionEntity;
import io.renren.modules.sys.service.SRegionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service("sRegion")
public class SRegionServiceImpl  extends ServiceImpl<SRegionDao, SRegionEntity> implements SRegionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String rname = (String) params.get("rname");

        IPage<SRegionEntity> page = this.page(
                new Query<SRegionEntity>().getPage(params),
                new QueryWrapper<SRegionEntity>()
                        .like(StringUtils.isNotBlank(rname), "rname", rname)
        );

        return new PageUtils(page);
    }


}
