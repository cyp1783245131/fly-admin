package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysFailEntity;
import io.renren.modules.sys.service.SysFailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xw on 2019/6/4.
 */
@RestController
@RequestMapping("sys/fail")
public class SysFailController {

    @Autowired
    private SysFailService sysFailService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysFailService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据id信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:fail:info")
    public R info(@PathVariable("id") Integer id){
        SysFailEntity fail = sysFailService.getById(id);

        return R.ok().put("fail", fail);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fail:update")
    public R update(@RequestBody SysFailEntity sysFailEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysFailEntity);

        sysFailService.updateById(sysFailEntity);

        return R.ok();
    }


}
