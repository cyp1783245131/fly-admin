package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.entity.SysFchannelEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.service.FChannelDataService;
import io.renren.modules.sys.service.SysFplancountService;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/fplancount")
public class SysFplancountController extends AbstractController {

    @Autowired
    private SysFplancountService sysFplancountService;

    @Autowired
    private FChannelDataService fChannelDataService;


    @RequestMapping("/list")
    @RequiresPermissions("sys:fplancount:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysFplancountService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll() {
        List<SysFplancountEntity> list = sysFplancountService.list(null);
        return R.ok().put("fplancountList", list);
    }

    @RequestMapping("/deleteByid")
    public R deleteByid(@RequestBody Long id) {
        sysFplancountService.removeById(id);
        return R.ok();
    }

    /**
     * 引流信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:fplancount:info")
    public R info(@PathVariable("id") Long id) {
        SysFplancountEntity fplancount = sysFplancountService.getById(id);

        List<FChannelDataEntity> fChannelDataEntityList = fChannelDataService.queryListByPlanId(id);
        fplancount.setFChannelDataList(fChannelDataEntityList);

        return R.ok().put("fplancount", fplancount);
    }

    /**
     * 保存引流
     */
    @SysLog("保存引流")
    @RequestMapping("/save")
    @RequiresPermissions("sys:fplancount:save")
    public R save(@RequestBody SysFplancountEntity fPlan) {
        fPlan.setCuser(getUserId());
        fPlan.setCtime(DateUtils.getCurrentDate());
        fPlan.setUtime(DateUtils.getCurrentDate());
        fPlan.setDeptid(getDeptId());
        sysFplancountService.save(fPlan);
        fChannelDataService.saveOrUpdateChannelDatas(fPlan);
        return R.ok();
    }

    /**
     * 修改引流
     */
    @SysLog("修改引流")
    @RequestMapping("/update")
    @RequiresPermissions("sys:fplancount:update")
    public R update(@RequestBody SysFplancountEntity fPlan) {
        ValidatorUtils.validateEntity(fPlan);
        fPlan.setUtime(DateUtils.getCurrentDate());
        fPlan.setDeptid(getDeptId());
        sysFplancountService.updateById(fPlan);//全部更新
        fChannelDataService.saveOrUpdateChannelDatas(fPlan);
        return R.ok();
    }

    /**
     * 删除引流
     */
    @SysLog("删除引流")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fplancount:delete")
    public R delete(@RequestBody Long[] userIds) {
        sysFplancountService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

}
