package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SRegionEntity;
import io.renren.modules.sys.service.SRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 区域规则
 */
@RestController
@RequestMapping("s/region")
public class SRegionController  extends AbstractController {

    @Autowired
    private SRegionService sRegionService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sRegionService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll() {
        List<SRegionEntity> list = sRegionService.list(null);
        return R.ok().put("regionList", list);
    }

    @RequestMapping("/deleteByid/{id}")
    public R deleteByid(@PathVariable("id") Long id) {
        sRegionService.getById(id);
        return R.ok();
    }


    /**
     * 区域规则信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SRegionEntity region = sRegionService.getById(id);
        return R.ok().put("region", region);
    }

    /**
     * 保存区域规则
     */
    @SysLog("保存区域规则")
    @RequestMapping("/save")
    public R save(@RequestBody SRegionEntity region) {
        sRegionService.save(region);
        return R.ok();
    }

    /**
     * 修改区域规则
     */
    @SysLog("修改区域规则")
    @RequestMapping("/update")
    public R update(@RequestBody SRegionEntity region) {
        ValidatorUtils.validateEntity(region);
        sRegionService.updateById(region);//全部更新
        return R.ok();
    }

    /**
     * 删除区域规则
     */
    @SysLog("删除区域规则")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        sRegionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}