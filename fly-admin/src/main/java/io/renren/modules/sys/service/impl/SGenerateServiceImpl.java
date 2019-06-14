package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SGenerateDao;
import io.renren.modules.sys.entity.SGenerateEntity;
import io.renren.modules.sys.service.SGenerateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sGenerate")
public class SGenerateServiceImpl extends ServiceImpl<SGenerateDao, SGenerateEntity> implements SGenerateService {

    @Autowired
    private SGenerateDao sGenerateDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String rname = (String) params.get("rname");

        IPage<SGenerateEntity> page = this.page(
                new Query<SGenerateEntity>().getPage(params),
                new QueryWrapper<SGenerateEntity>()
                        .like(StringUtils.isNotBlank(rname), "rname", rname)
        );

        return new PageUtils(page);
    }

    @Override
    public List<SGenerateEntity>  selectByDays(String days) {
        List<SGenerateEntity>  list = sGenerateDao.selectByDays(days);
        return list;
    }



}
