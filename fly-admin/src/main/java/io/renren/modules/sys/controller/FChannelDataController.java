package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.service.FChannelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 计划渠道数据表
 *
 */
@RestController
@RequestMapping("sys/fchanneldata")
public class FChannelDataController {
    @Autowired
    private FChannelDataService fChannelDataService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fChannelDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dId}")
    public R info(@PathVariable("dId") Long dId){
        FChannelDataEntity fChannelData = fChannelDataService.getById(dId);

        return R.ok().put("fChannelData", fChannelData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FChannelDataEntity fChannelData){
        fChannelDataService.save(fChannelData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FChannelDataEntity fChannelData){
        ValidatorUtils.validateEntity(fChannelData);
        fChannelDataService.updateById(fChannelData);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] dIds){
        fChannelDataService.removeByIds(Arrays.asList(dIds));

        return R.ok();
    }

}
