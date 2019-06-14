package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCalculationEntity;
import io.renren.modules.sys.entity.SysCooperationEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysCooperationService;
import io.renren.modules.sys.service.SysMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/21.
 */
@RestController
@RequestMapping("/sys/cooperation")
public class SysCooperationController {

    @Autowired
    private SysCooperationService sysCooperationService;

    @Autowired
    private SysMerchantService sysMerchantService;

    /**
     * 查询所有数据，带分页
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysCooperationService.queryPage(params);
        List<SysCooperationEntity> list = (List<SysCooperationEntity>) page.getList();
        for (SysCooperationEntity sysCooperationEntity : list) {
            SysMerchantEntity sysMerchantEntity = sysMerchantService.getById(sysCooperationEntity.getMerchantId());
            sysCooperationEntity.setMerchantEntity(sysMerchantEntity);
        }
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll(){
        List<SysCooperationEntity> cooperationList = sysCooperationService.list();
        return R.ok().put("cooperationList", cooperationList);
    }


    /**
     * 根据id获得对象
     * @param cooperationId
     * @return
     */
    @RequestMapping("/getCooperationById/{cooperationId}")
    public R getDrainageById(@PathVariable Integer cooperationId){
        SysCooperationEntity cooperation = sysCooperationService.getById(cooperationId);

        return R.ok().put("cooperation", cooperation);
    }

    /**
     * 添加和修改
     * @param cooperation
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public R save(@RequestBody SysCooperationEntity cooperation){
        //没有id是添加
        if(cooperation.getCooperationId() == null || "".equals(cooperation.getCooperationId())){
            System.out.println("添加");
            sysCooperationService.saveCooperation(cooperation);
        }else{//否则是有id则未修改
            System.out.println("修改");
            sysCooperationService.updateCooperation(cooperation);
        }

        return R.ok();
    }


    /**
     * 删除多条数据
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] cooperationIds){
        sysCooperationService.removeByIds(Arrays.asList(cooperationIds));
        return R.ok();
    }

    /**
     * 删除数据
     */
    @RequestMapping("/deleteByid/{cooperationId}")
    public R deleteByid(@PathVariable(value = "cooperationId") Integer cooperationId){
        sysCooperationService.removeById(cooperationId);
        return R.ok();
    }


}
