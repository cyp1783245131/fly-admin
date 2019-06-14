package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysPayMoneyCareEntity;
import io.renren.modules.sys.service.SysPayMoneyCareService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
@RestController
@RequestMapping("sys/payMoneyCare")
public class SysPayMoneyCareController {

    @Autowired
    private SysPayMoneyCareService sysPayMoneyCareService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:payMoneyCare:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPayMoneyCareService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据id信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:payMoneyCare:info")
    public R info(@PathVariable("id") Integer id){
        SysPayMoneyCareEntity payMoneyCare = sysPayMoneyCareService.getById(id);

        return R.ok().put("payMoneyCare", payMoneyCare);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:payMoneyCare:update")
    public R update(@RequestBody SysPayMoneyCareEntity sysPayMoneyCareEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysPayMoneyCareEntity);

        sysPayMoneyCareService.updateById(sysPayMoneyCareEntity);

        return R.ok();
    }

}
