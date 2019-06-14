package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDetEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/29.
 */
public interface SysDetService extends IService<SysDetEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);

}
