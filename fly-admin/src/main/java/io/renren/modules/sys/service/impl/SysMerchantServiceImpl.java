package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysMerchantDao;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysMerchantService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/13.
 */
@Service("sysMerchantService")
public class SysMerchantServiceImpl extends ServiceImpl<SysMerchantDao,SysMerchantEntity> implements SysMerchantService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String merchantName = (String)params.get("merchantName");

        IPage<SysMerchantEntity> page = this.page(
                new Query<SysMerchantEntity>().getPage(params),
                new QueryWrapper<SysMerchantEntity>()
                        .like(StringUtils.isNotBlank(merchantName),"merchant_name", merchantName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public void saveMerchant(SysMerchantEntity merchant) {
        this.save(merchant);
    }

    @Override
    public void updateMerchant(SysMerchantEntity merchant) {
        this.updateById(merchant);
    }

    @Override
    public List<SysMerchantEntity> queryList(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }

    @Override
    public List<SysMerchantEntity> findByMid(Long mid) {
        return baseMapper.findByMid(mid);
    }
}
