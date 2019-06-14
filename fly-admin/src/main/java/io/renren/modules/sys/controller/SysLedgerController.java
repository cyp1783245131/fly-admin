package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.entity.SysCooperationEntity;
import io.renren.modules.sys.entity.SysLedgerEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysCalculationService;
import io.renren.modules.sys.service.SysLedgerService;
import io.renren.modules.sys.service.SysMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/22.
 */
@RestController
@RequestMapping("/sys/ledger")
public class SysLedgerController {

    @Autowired
    private SysLedgerService sysLedgerService;

    @Autowired
    private SysMerchantService sysMerchantService;

    @Autowired
    private SysCalculationService sysCalculationService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysLedgerService.queryPage(params);
        List<SysLedgerEntity> list = (List<SysLedgerEntity>) page.getList();
        for (SysLedgerEntity sysLedgerEntity : list) {
            sysLedgerEntity.setA(0D);
        }
        this.test1(list);
        return R.ok().put("page", page);
    }



    @RequestMapping("/findAll")
    public R findAll(){
        List<SysLedgerEntity> ledgerList = sysLedgerService.findByTime();
        for (SysLedgerEntity sysLedgerEntity : ledgerList) {
            System.out.println(sysLedgerEntity.toString() );
        }
        this.test1(ledgerList);
        return R.ok().put("ledgerList", ledgerList);
    }

    public void test1(List<SysLedgerEntity> ledgerList){
        for (SysLedgerEntity ledgerEntity : ledgerList) {
            SysMerchantEntity merchantEntity = sysMerchantService.getById(ledgerEntity.getMerchantId());
            ledgerEntity.setMerchant(merchantEntity);
            SysCalculationEntity calculationEntity = sysCalculationService.getById(ledgerEntity.getCalculationId());
            ledgerEntity.setCalculation(calculationEntity);

            //可使用资金
            String ksyzj = String.format("%.1f", calculationEntity.getUsableQuota() * ledgerEntity.getLedgerMoney());
            ledgerEntity.setKsyzj(Double.parseDouble(ksyzj));
            String aall = String.format("%.1f", calculationEntity.getUsableQuota() * ledgerEntity.getA());
            ledgerEntity.setAAll(Double.parseDouble(aall));
            //新锐营
            String xry = String.format("%.2f", ledgerEntity.getKsyzj() * calculationEntity.getCompany1());
            ledgerEntity.setXry(Double.parseDouble(xry));
            String ball = String.format("%.2f", calculationEntity.getCompany1() * ledgerEntity.getAAll());
            ledgerEntity.setBAll(Double.parseDouble(ball));
            //益斗
            String yd = String.format("%.1f",ledgerEntity.getKsyzj() * calculationEntity.getCompany2());
            ledgerEntity.setYd(Double.parseDouble(yd));
            String call = String.format("%.1f", calculationEntity.getCompany2() * ledgerEntity.getAAll());
            ledgerEntity.setCAll(Double.parseDouble(call));
            //第三方
            String dsf = String.format("%.1f", ledgerEntity.getKsyzj() * calculationEntity.getThirdparty());
            ledgerEntity.setDsf(Double.parseDouble(dsf));
            String dall = String.format("%.1f", calculationEntity.getThirdparty() * ledgerEntity.getAAll());
            ledgerEntity.setDAll(Double.parseDouble(dall));

        }
    }


    /**
     * 根据id获得对象
     * @param ledgerId
     * @return
     */
    @RequestMapping("/getLedgerById/{ledgerId}")
    public R getDrainageById(@PathVariable Integer ledgerId){
        SysLedgerEntity ledger = sysLedgerService.getById(ledgerId);

        return R.ok().put("ledger", ledger);
    }

    /**
     * 添加和修改
     * @param ledger
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public R save(@RequestBody SysLedgerEntity ledger){
        //没有id是添加
        if(ledger.getLedgerId() == null || "".equals(ledger.getLedgerId())){
            System.out.println("添加");
            sysLedgerService.saveLedger(ledger);
        }else{//否则是有id则未修改
            System.out.println("修改");
            sysLedgerService.updateLedger(ledger);
        }

        return R.ok();
    }


    /**
     * 删除数据
     */
    @RequestMapping("/deleteByid/{ledgerId}")
    public R deleteByid(@PathVariable(value = "ledgerId") Integer ledgerId){
        sysLedgerService.removeById(ledgerId);
        return R.ok();
    }
}
