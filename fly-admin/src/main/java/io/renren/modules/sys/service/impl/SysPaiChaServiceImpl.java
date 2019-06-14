package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPaiChaDao;
import io.renren.modules.sys.entity.SysPaiChaEntity;
import io.renren.modules.sys.service.SysPaiChaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/15.
 */
@Service("sysPaiChaService")
public class SysPaiChaServiceImpl extends ServiceImpl<SysPaiChaDao,SysPaiChaEntity> implements SysPaiChaService{

    //查询所有项目信息
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String pname = (String)params.get("pname");

        IPage<SysPaiChaEntity> page = this.page(
                new Query<SysPaiChaEntity>().getPage(params),
                new QueryWrapper<SysPaiChaEntity>()
                        .like(StringUtils.isNotBlank(pname),"pname", pname)
        );

        return new PageUtils(page);
    }

}
