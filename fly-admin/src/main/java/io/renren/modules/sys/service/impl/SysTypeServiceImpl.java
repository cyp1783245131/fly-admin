package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysCodeDao;
import io.renren.modules.sys.dao.SysTypeDao;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysTypeEntity;
import io.renren.modules.sys.service.SysCodeService;
import io.renren.modules.sys.service.SysTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/28.
 */
@Service("sysTypeService")
public class SysTypeServiceImpl extends ServiceImpl<SysTypeDao,SysTypeEntity> implements SysTypeService {

    //查询所有节点类型
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysTypeEntity> page = this.page(
                new Query<SysTypeEntity>().getPage(params)
        );

        return new PageUtils(page);
    }

}
