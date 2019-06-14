package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysLedgerEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/22.
 */
public interface SysLedgerService extends IService<SysLedgerEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveLedger(SysLedgerEntity ledger);

    void updateLedger(SysLedgerEntity ledger);
    List<SysLedgerEntity> findByTime();
}
