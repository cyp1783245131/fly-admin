package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dao.SGenerateDao;
import io.renren.modules.sys.entity.SGenerateEntity;
import io.renren.modules.sys.service.SGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 生成规则
 */
@RestController
@RequestMapping("s/generate")
public class SGenerateController extends AbstractController {

    @Autowired
    private SGenerateService sGenerateService;
    @Autowired
    private SGenerateDao sGenerateDao;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sGenerateService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll() {
        List<SGenerateEntity> list = sGenerateService.list(null);
        for (SGenerateEntity entity : list) {
            System.out.println(entity.toString());
        }
        return R.ok().put("generateList", list);
    }

    @RequestMapping("/deleteByid/{id}")
    public R deleteByid(@PathVariable("id") Long id) {
        System.out.println(id);
        sGenerateService.getById(id);
        return R.ok();
    }


    /**
     * 区域规则信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SGenerateEntity generate = sGenerateService.getById(id);
        return R.ok().put("generate", generate);
    }

    /**
     * 保存区域规则
     */
    @SysLog("保存区域规则")
    @RequestMapping("/save")
    public R save(@RequestBody SGenerateEntity generate) {
        System.out.println(generate.toString());
        sGenerateService.save(generate);
        return R.ok();
    }

    /**
     * 修改区域规则
     */
    @SysLog("修改区域规则")
    @RequestMapping("/update")
    public R update(@RequestBody SGenerateEntity generate) {
        ValidatorUtils.validateEntity(generate);
        sGenerateService.updateById(generate);//全部更新
        return R.ok();
    }

    /**
     * 删除区域规则
     */
    @SysLog("删除区域规则")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        sGenerateService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
