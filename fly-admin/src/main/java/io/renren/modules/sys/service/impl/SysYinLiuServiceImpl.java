package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysYinLiuDao;
import io.renren.modules.sys.entity.SysYinLiuEntity;
import io.renren.modules.sys.service.SysYinLiuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/6.
 */
@Service("sysYinLiuService")
public class SysYinLiuServiceImpl extends ServiceImpl<SysYinLiuDao,SysYinLiuEntity> implements SysYinLiuService{

    //查询所有引流信息
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<SysYinLiuEntity> page = this.page(
                new Query<SysYinLiuEntity>().getPage(params),
                new QueryWrapper<SysYinLiuEntity>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }


}
