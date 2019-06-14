package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysPaySearchEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
public interface SysPaySearchService extends IService<SysPaySearchEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);

}
