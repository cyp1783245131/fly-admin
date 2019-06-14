package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.entity.SysLedgerEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/24.
 */
@RestController
@RequestMapping("/sys/overview")
public class SysOverviewController {
    @Autowired
    private SysLedgerService sysLedgerService;

    @Autowired
    private SysLedgerController sysLedgerController;

    @RequestMapping("/findAll")
    public R findAll(){
        List<SysLedgerEntity> ledgerList = sysLedgerService.findByTime();
        sysLedgerController.test1(ledgerList);

        for (SysLedgerEntity sysLedgerEntity : ledgerList) {
            Date ledgerTime = sysLedgerEntity.getLedgerTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ledgerTime);
            int i = calendar.get(Calendar.MONTH) +1;
            int month = sysLedgerEntity.getLedgerTime().getMonth() + 1;
            System.err.println(sysLedgerEntity.getAAll());
        }

        return R.ok().put("ledgerList", ledgerList);
    }


}
