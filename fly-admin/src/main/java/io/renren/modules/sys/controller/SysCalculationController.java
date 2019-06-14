package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.service.SysCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/20.
 */
@RestController
@RequestMapping("/sys/calculation")
public class SysCalculationController {

    @Autowired
    private SysCalculationService sysCalculationService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysCalculationService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll(){
        List<SysCalculationEntity> calculationList = sysCalculationService.list();
        return R.ok().put("calculationList", calculationList);
    }

    @RequestMapping("/getCalculationById/{calculationId}")
    public R getCalculationById(@PathVariable Integer calculationId){
        SysCalculationEntity calculation = sysCalculationService.getById(calculationId);

        return R.ok().put("calculation", calculation);
    }

    @RequestMapping("/saveOrUpdate")
    public R save(@RequestBody SysCalculationEntity calculation){
        //没有id是添加
        if(calculation.getCalculationId() == null || "".equals(calculation.getCalculationId())){
            System.out.println("添加");
            sysCalculationService.saveCalculation(calculation);
        }else{//否则是有id则未修改
            System.out.println("修改");
            sysCalculationService.updateCalculation(calculation);
        }

        return R.ok();
    }

    /**
     * 删除多条数据
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] calculationIds){
        sysCalculationService.removeByIds(Arrays.asList(calculationIds));
        return R.ok();
    }

    /**
     * 删除数据
     */
    @RequestMapping("/deleteByid/{calculationId}")
    public R deleteByid(@PathVariable(value = "calculationId") Integer calculationId){
        sysCalculationService.removeById(calculationId);
        return R.ok();
    }
}
