package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPayMoneyCareDao;
import io.renren.modules.sys.entity.SysPayMoneyCareEntity;
import io.renren.modules.sys.service.SysPayMoneyCareService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
@Service("sysPayMoneyCareService")
public class SysPayMoneyCareServiceImpl extends ServiceImpl<SysPayMoneyCareDao,SysPayMoneyCareEntity> implements SysPayMoneyCareService {
    //查询所有项目信息
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPayMoneyCareEntity> page = this.page(
                new Query<SysPayMoneyCareEntity>().getPage(params)
        );

        return new PageUtils(page);
    }
}
