package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysMoneysEntity;
import io.renren.modules.sys.service.SysMoneysService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@RestController
@RequestMapping("sys/moneys")
public class SysMoneysController {

    @Autowired
    private SysMoneysService sysMoneysService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:moneys:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMoneysService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据id信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:moneys:info")
    public R info(@PathVariable("id") Integer id){
        SysMoneysEntity moneys = sysMoneysService.getById(id);

        return R.ok().put("moneys", moneys);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:moneys:update")
    public R update(@RequestBody SysMoneysEntity sysMoneysEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysMoneysEntity);

        sysMoneysService.updateById(sysMoneysEntity);

        return R.ok();
    }

}
