package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysMembersDao;
import io.renren.modules.sys.entity.SysMembersEntity;
import io.renren.modules.sys.service.SysMembersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@Service("sysMembersService")
public class SysMembersServiceImpl extends ServiceImpl<SysMembersDao,SysMembersEntity> implements SysMembersService {
    //查询所有节点类型
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String phoneid = (String)params.get("phoneid");

        IPage<SysMembersEntity> page = this.page(
                new Query<SysMembersEntity>().getPage(params),
                new QueryWrapper<SysMembersEntity>()
                        .like(StringUtils.isNotBlank(phoneid),"phoneid", phoneid)
        );

        return new PageUtils(page);
    }
}
