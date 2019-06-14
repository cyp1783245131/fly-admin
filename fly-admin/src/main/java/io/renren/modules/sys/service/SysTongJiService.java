package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysPaiChaEntity;
import io.renren.modules.sys.entity.SysTongJiEntity;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/17.
 */
public interface SysTongJiService extends IService<SysTongJiEntity> {


    //查询
    PageUtils queryPage(Map<String, Object> params);

}
