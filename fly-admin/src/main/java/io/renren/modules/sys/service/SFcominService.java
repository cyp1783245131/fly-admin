package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SFcominEntity;
import io.renren.modules.sys.entity.SFplancountEntity;

import java.util.Map;

public interface SFcominService  extends IService<SFcominEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void buildFlowData(SFplancountEntity fplancount);
}
