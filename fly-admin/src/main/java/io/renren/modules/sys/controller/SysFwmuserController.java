package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.entity.SysFwmuserEntity;
import io.renren.modules.sys.service.SysFplancountService;
import io.renren.modules.sys.service.SysFwmuserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
@RestController
@RequestMapping("sys/fwmuser")
public class SysFwmuserController {

    @Autowired
    private SysFwmuserService sysFwmuserService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:fwmuser:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysFwmuserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 微盟用户信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:fwmuser:info")
    public R info(@PathVariable("id") Long id){
        SysFwmuserEntity sysFwmuserEntity = sysFwmuserService.getById(id);

        return R.ok().put("fwmuser", sysFwmuserEntity);
    }

    /**
     * 保存微盟用户
     */
    @SysLog("保存微盟用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:fwmuser:save")
    public R save(@RequestBody SysFwmuserEntity fwmuser){
        ValidatorUtils.validateEntity(fwmuser, AddGroup.class);
        fwmuser.setCtime(DateUtils.getCurrentDate());
        fwmuser.setUtime(DateUtils.getCurrentDate());
        sysFwmuserService.save(fwmuser);

        return R.ok();
    }

    /**
     * 修改微盟用户
     */
    @SysLog("修改微盟用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:fwmuser:update")
    public R update(@RequestBody SysFwmuserEntity fwmuser){
        ValidatorUtils.validateEntity(fwmuser, UpdateGroup.class);

        sysFwmuserService.updateById(fwmuser);

        return R.ok();
    }

    /**
     * 删除微盟用户
     */
    @SysLog("删除微盟用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fwmuser:delete")
    public R delete(@RequestBody Long[] userIds){
        sysFwmuserService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

}
