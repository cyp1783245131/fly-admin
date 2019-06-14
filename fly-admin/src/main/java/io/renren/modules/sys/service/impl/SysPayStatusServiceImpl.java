package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPayDao;
import io.renren.modules.sys.dao.SysPayStatusDao;
import io.renren.modules.sys.entity.SysPayEntity;
import io.renren.modules.sys.entity.SysPayStatusEntity;
import io.renren.modules.sys.service.SysPayService;
import io.renren.modules.sys.service.SysPayStatusService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@Service("sysPayStatusService")
public class SysPayStatusServiceImpl extends ServiceImpl<SysPayStatusDao,SysPayStatusEntity> implements SysPayStatusService {

    //查询所有
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String starttime= (String) params.get("starttime");
        String endtime= (String) params.get("endtime");
        IPage<SysPayStatusEntity> page = this.page(
                new Query<SysPayStatusEntity>().getPage(params),
                new QueryWrapper<SysPayStatusEntity>()
                        .ge(StringUtils.isNotBlank(starttime),"ptime",starttime)
                        .le(StringUtils.isNotBlank(endtime),"ptime",endtime)
        );

        return new PageUtils(page);
    }

}
