package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysFchannelDao;
import io.renren.modules.sys.entity.SysFchannelEntity;
import io.renren.modules.sys.service.SysFchannelService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysFchannel")
public class SysFchannelServiceImpl extends ServiceImpl<SysFchannelDao, SysFchannelEntity> implements SysFchannelService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysFchannelEntity> page = this.page(
                new Query<SysFchannelEntity>().getPage(params),
                new QueryWrapper<SysFchannelEntity>()
        );

        return new PageUtils(page);
    }
}
