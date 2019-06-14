package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysPaiChaEntity;
import io.renren.modules.sys.service.SysPaiChaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/15.
 */
@RestController
@RequestMapping("sys/paicha")
public class SysPaiChaController {

    @Autowired
    private SysPaiChaService sysPaiChaService;

    /**
     * 引流列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:paicha:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPaiChaService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存引流信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:paicha:save")
    public R save(@RequestBody SysPaiChaEntity sysPaiChaEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysPaiChaEntity);

        sysPaiChaService.save(sysPaiChaEntity);

        return R.ok();
    }

    /**
     * 根据引流id查询引流信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:paicha:info")
    public R info(@PathVariable("id") Integer id){
        SysPaiChaEntity paicha = sysPaiChaService.getById(id);

        return R.ok().put("paicha", paicha);
    }

    /**
     * 修改引流信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:paicha:update")
    public R update(@RequestBody SysPaiChaEntity sysPaiChaEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysPaiChaEntity);

        sysPaiChaService.updateById(sysPaiChaEntity);

        return R.ok();
    }

    /**
     * 删除引流信息
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:paicha:delete")
    public R delete(@RequestBody Long[] ids){
        sysPaiChaService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
