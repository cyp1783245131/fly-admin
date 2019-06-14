package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysFplancountDao;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.service.SysFplancountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysFplancount")
public class SysFplancountServiceImpl extends ServiceImpl<SysFplancountDao, SysFplancountEntity> implements SysFplancountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String dname = (String) params.get("dname");

        IPage<SysFplancountEntity> page = this.page(
                new Query<SysFplancountEntity>().getPage(params),
                new QueryWrapper<SysFplancountEntity>()
                        .like(StringUtils.isNotBlank(dname), "dname", dname)
        );

        return new PageUtils(page);
    }


}
