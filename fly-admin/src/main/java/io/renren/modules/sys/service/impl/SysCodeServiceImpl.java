package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysCodeDao;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysYinLiuEntity;
import io.renren.modules.sys.service.SysCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/15.
 */
@Service("sysCodeService")
public class SysCodeServiceImpl extends ServiceImpl<SysCodeDao,SysCodeEntity> implements SysCodeService {

    //查询所有节点类型
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String cname = (String)params.get("cname");

        IPage<SysCodeEntity> page = this.page(
                new Query<SysCodeEntity>().getPage(params),
                new QueryWrapper<SysCodeEntity>()
                        .like(StringUtils.isNotBlank(cname),"cname", cname)
        );

        return new PageUtils(page);
    }

}
