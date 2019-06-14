package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysPaiChaEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/15.
 */
public interface SysPaiChaService extends IService<SysPaiChaEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);

}
