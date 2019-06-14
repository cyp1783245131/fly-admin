package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysTongJiDao;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysTongJiEntity;
import io.renren.modules.sys.service.SysTongJiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/17.
 */
@Service("sysTongJiService")
public class SysTongJiServiceImpl extends ServiceImpl<SysTongJiDao,SysTongJiEntity> implements SysTongJiService {

    //查询所有
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        List<SysTongJiEntity> list = this.list();
        int size = list.size();

        IPage<SysTongJiEntity> page = this.page(
                new Query<SysTongJiEntity>().getPage(params)
        );

        return new PageUtils(page);
    }

}
