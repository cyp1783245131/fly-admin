package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysMoneysDao;
import io.renren.modules.sys.entity.SysMoneysEntity;
import io.renren.modules.sys.service.SysMoneysService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@Service("sysMoneysService")
public class SysMoneysServiceImpl extends ServiceImpl<SysMoneysDao,SysMoneysEntity> implements SysMoneysService {
    //查询所有节点类型
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<SysMoneysEntity> page = this.page(
                new Query<SysMoneysEntity>().getPage(params)
        );

        return new PageUtils(page);
    }
}
