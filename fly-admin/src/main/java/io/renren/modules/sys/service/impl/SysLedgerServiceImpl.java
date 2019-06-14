package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysLedgerDao;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.entity.SysLedgerEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysLedgerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/22.
 */
@Service("sysLedgerService")
public class SysLedgerServiceImpl extends ServiceImpl<SysLedgerDao, SysLedgerEntity> implements SysLedgerService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String ledgerName = (String)params.get("ledgerName");

        IPage<SysLedgerEntity> page = this.page(
                new Query<SysLedgerEntity>().getPage(params),
                new QueryWrapper<SysLedgerEntity>()
                        .like(StringUtils.isNotBlank(ledgerName),"ledger_name", ledgerName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        return new PageUtils(page);
    }

    @Override
    public void saveLedger(SysLedgerEntity ledger) {
        this.save(ledger);
    }

    @Override
    public void updateLedger(SysLedgerEntity ledger) {
        this.updateById(ledger);
    }

    @Override
    public List<SysLedgerEntity> findByTime() {
        return baseMapper.findByTime();
    }


}
