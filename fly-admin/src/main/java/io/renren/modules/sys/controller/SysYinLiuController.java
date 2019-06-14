package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysYinLiuEntity;
import io.renren.modules.sys.service.SysYinLiuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/7.
 */
@RestController
@RequestMapping("sys/yinliu")
public class SysYinLiuController{

    @Autowired
    private SysYinLiuService sysYinLiuService;

    /**
     * 引流列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:yinliu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysYinLiuService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存引流信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:yinliu:save")
    public R save(@RequestBody SysYinLiuEntity sysYinLiuEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysYinLiuEntity);

        sysYinLiuService.save(sysYinLiuEntity);

        return R.ok();
    }

    /**
     * 根据引流id查询引流信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:yinliu:info")
    public R info(@PathVariable("id") Integer id){
        SysYinLiuEntity yinliu = sysYinLiuService.getById(id);

        return R.ok().put("yinliu", yinliu);
    }

    /**
     * 修改引流信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:yinliu:update")
    public R update(@RequestBody SysYinLiuEntity sysYinLiuEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysYinLiuEntity);

        sysYinLiuService.updateById(sysYinLiuEntity);

        return R.ok();
    }

    /**
     * 删除引流信息
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:yinliu:delete")
    public R delete(@RequestBody Long[] ids){
        sysYinLiuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
