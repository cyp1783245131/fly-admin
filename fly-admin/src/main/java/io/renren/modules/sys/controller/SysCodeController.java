package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.service.SysCodeService;
import io.renren.modules.sys.service.SysDetService;
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
@RequestMapping("sys/code")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;

    /**
     * 风险列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:code:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysCodeService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 保存风险信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:code:save")
    public R save(@RequestBody SysCodeEntity sysCodeEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysCodeEntity);

        sysCodeService.save(sysCodeEntity);

        return R.ok();
    }

    /**
     * 根据风险id查询引流信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:code:info")
    public R info(@PathVariable("id") Integer id){
        SysCodeEntity code = sysCodeService.getById(id);

        return R.ok().put("code", code);
    }

    /**
     * 修改风险信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:code:update")
    public R update(@RequestBody SysCodeEntity sysCodeEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysCodeEntity);

        sysCodeService.updateById(sysCodeEntity);

        return R.ok();
    }

    /**
     * 删除风险信息
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:code:delete")
    public R delete(@RequestBody Long[] ids){
        sysCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询所有风险类型
     * @return
     */
    @RequestMapping("/codeList")
    public R list(){
        List<SysCodeEntity> codeList = sysCodeService.list();
        return R.ok().put("codeList", codeList);
    }

}
