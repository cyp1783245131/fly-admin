package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysMembersEntity;
import io.renren.modules.sys.service.SysMembersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@RestController
@RequestMapping("sys/members")
public class SysMembersController {

    @Autowired
    private SysMembersService sysMembersService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:members:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMembersService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存风险信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:members:save")
    public R save(@RequestBody SysMembersEntity sysMembersEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysMembersEntity);

        sysMembersService.save(sysMembersEntity);

        return R.ok();
    }

    /**
     * 根据风险id查询引流信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:members:info")
    public R info(@PathVariable("id") Integer id){
        SysMembersEntity members = sysMembersService.getById(id);

        return R.ok().put("members", members);
    }

    /**
     * 修改风险信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:members:update")
    public R update(@RequestBody SysMembersEntity sysMembersEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysMembersEntity);

        sysMembersService.updateById(sysMembersEntity);

        return R.ok();
    }

    /**
     * 删除风险信息
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:members:delete")
    public R delete(@RequestBody Long[] ids){
        sysMembersService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
