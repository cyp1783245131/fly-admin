package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPmoneyDao;
import io.renren.modules.sys.entity.SysPmoneyEntity;
import io.renren.modules.sys.service.SysPmoneyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@Service("sysPmoneyService")
public class SysPmoneyServiceImpl extends ServiceImpl<SysPmoneyDao,SysPmoneyEntity> implements SysPmoneyService {
    //查询所有
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String starttime= (String) params.get("starttime");
        String endtime= (String) params.get("endtime");
        String monid= (String) params.get("monid");
        IPage<SysPmoneyEntity> page = this.page(
                new Query<SysPmoneyEntity>().getPage(params),
                new QueryWrapper<SysPmoneyEntity>()
                        .ge(StringUtils.isNotBlank(starttime),"mtime",starttime)
                        .le(StringUtils.isNotBlank(endtime),"mtime",endtime)
                        .eq(StringUtils.isNotBlank(monid),"monid",monid)
        );

        return new PageUtils(page);
    }
}
