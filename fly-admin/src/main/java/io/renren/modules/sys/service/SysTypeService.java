package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysTypeEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/28.
 */
public interface SysTypeService extends IService<SysTypeEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);

}
