package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysFailDao;
import io.renren.modules.sys.entity.SysFailEntity;
import io.renren.modules.sys.service.SysFailService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
@Service("sysFailService")
public class SysFailServiceImpl extends ServiceImpl<SysFailDao,SysFailEntity> implements SysFailService {
    //查询所有项目信息
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysFailEntity> page = this.page(
                new Query<SysFailEntity>().getPage(params)
        );
        return new PageUtils(page);
    }
}
