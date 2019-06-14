package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPayDao;
import io.renren.modules.sys.dao.SysPaySearchDao;
import io.renren.modules.sys.entity.SysPayEntity;
import io.renren.modules.sys.entity.SysPaySearchEntity;
import io.renren.modules.sys.service.SysPaySearchService;
import io.renren.modules.sys.service.SysPayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@Service("sysPaySearchService")
public class SysPaySearchServiceImpl extends ServiceImpl<SysPaySearchDao,SysPaySearchEntity> implements SysPaySearchService {

    //查询所有
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String starttime= (String) params.get("starttime");
        String endtime= (String) params.get("endtime");
        IPage<SysPaySearchEntity> page = this.page(
                new Query<SysPaySearchEntity>().getPage(params),
                new QueryWrapper<SysPaySearchEntity>()
                        .ge(StringUtils.isNotBlank(starttime),"stime",starttime)
                        .le(StringUtils.isNotBlank(endtime),"stime",endtime)
        );

        return new PageUtils(page);
    }

}
