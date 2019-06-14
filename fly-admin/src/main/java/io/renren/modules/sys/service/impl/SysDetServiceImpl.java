package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysDetDao;
import io.renren.modules.sys.entity.SysDetEntity;
import io.renren.modules.sys.service.SysDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/29.
 */
@Service("sysDetService")
public class SysDetServiceImpl extends ServiceImpl<SysDetDao,SysDetEntity> implements SysDetService {

    //查询所有节点类型
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDetEntity> page = this.page(
                new Query<SysDetEntity>().getPage(params)
        );

        return new PageUtils(page);
    }

}
