package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMerchantEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/13.
 */
public interface SysMerchantService extends IService<SysMerchantEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveMerchant(SysMerchantEntity merchant);

    void updateMerchant(SysMerchantEntity merchant);

    List<SysMerchantEntity> queryList(Map<String, Object> params);

    List<SysMerchantEntity> findByMid(Long mid);
}
