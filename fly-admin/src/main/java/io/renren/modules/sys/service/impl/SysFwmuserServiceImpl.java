package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysFwmuserDao;
import io.renren.modules.sys.entity.SysFwmuserEntity;
import io.renren.modules.sys.service.SysFwmuserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysFwmuser")
public class SysFwmuserServiceImpl extends ServiceImpl<SysFwmuserDao, SysFwmuserEntity> implements SysFwmuserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String mobile = (String) params.get("mobile");

        IPage<SysFwmuserEntity> page = this.page(
                new Query<SysFwmuserEntity>().getPage(params),
                new QueryWrapper<SysFwmuserEntity>()
                        .like(StringUtils.isNotBlank(mobile), "mobile", mobile)

        );

        return new PageUtils(page);
    }
}
