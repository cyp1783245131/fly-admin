package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysFchannelEntity;
import io.renren.modules.sys.service.SysFchannelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/fchannel")
public class SysFchannelController {

    @Autowired
    private SysFchannelService sysFchannelService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:fchannel:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysFchannelService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
//    @RequiresPermissions("sys:fchannel:findAll")
    public R findAll() {
        List<SysFchannelEntity> list = sysFchannelService.list(null);

        return R.ok().put("fchannelList", list);
    }

    /**
     * 渠道信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:fchannel:info")
    public R info(@PathVariable("id") Long id){
        SysFchannelEntity fchannelEntity = sysFchannelService.getById(id);

        return R.ok().put("fchannel", fchannelEntity);
    }

    /**
     * 保存渠道
     */
    @SysLog("保存渠道")
    @RequestMapping("/save")
    @RequiresPermissions("sys:fchannel:save")
    public R save(@RequestBody SysFchannelEntity fchannel){
        ValidatorUtils.validateEntity(fchannel, AddGroup.class);

        sysFchannelService.save(fchannel);

        return R.ok();
    }

    /**
     * 修改渠道
     */
    @SysLog("修改渠道")
    @RequestMapping("/update")
    @RequiresPermissions("sys:fchannel:update")
    public R update(@RequestBody SysFchannelEntity fchannel){
        ValidatorUtils.validateEntity(fchannel, UpdateGroup.class);

        sysFchannelService.updateById(fchannel);

        return R.ok();
    }

    /**
     * 删除渠道
     */
    @SysLog("删除渠道")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fchannel:delete")
    public R delete(@RequestBody Long[] ids){


        sysFchannelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
