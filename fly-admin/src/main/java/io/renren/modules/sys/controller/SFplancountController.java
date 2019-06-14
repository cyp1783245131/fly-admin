package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SFplancountEntity;
import io.renren.modules.sys.entity.SGenerateEntity;
import io.renren.modules.sys.entity.SRegionEntity;
import io.renren.modules.sys.service.FChannelDataService;
import io.renren.modules.sys.service.SFplancountService;
import io.renren.modules.sys.service.SGenerateService;
import io.renren.modules.sys.service.SRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("s/fplancount")
public class SFplancountController extends AbstractController {

    @Autowired
    private SFplancountService sFplancountService;

    @Autowired
    private FChannelDataService fChannelDataService;

    @Autowired
    private SGenerateService sGenerateService;

    @Autowired
    private SRegionService sRegionService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sFplancountService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/findRsure/{id}/{days}")
    public R findRsure(@PathVariable("id") Long id, @PathVariable("days") String days) {
//        Long[] arr = sGenerateService.findId();
//        int i = (int) (Math.random() * arr.length);
//        System.out.println(arr[i]);
//        long generateId = Long.parseLong(arr[i].toString());


        List<SGenerateEntity> generates = sGenerateService.selectByDays(days);
        SGenerateEntity generate = null;
        int size = generates.size();//6
        Random random = new Random();
        System.out.println(random.nextInt(size));
        generate = generates.get(random.nextInt(size));
        SRegionEntity region = sRegionService.getById(id);
        System.out.println(generates.toString());
        System.out.println(region.toString());
//        String rsure = sGenerateEntity.getRsure();
//        String[] rsureList = rsure.split(",");
        return R.ok().put("generate", generate).put("region", region);
    }


    @RequestMapping("/findAll")
    public R findAll() {
        List<SFplancountEntity> list = sFplancountService.list(null);
        return R.ok().put("fplancountList", list);
    }

    @RequestMapping("/deleteByid")
    public R deleteByid(@RequestBody Long id) {
        sFplancountService.getById(id);
        return R.ok();
    }

    /**
     * 引流信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SFplancountEntity fplancount = sFplancountService.getById(id);
//        List<FChannelDataEntity> fChannelDataEntityList = fChannelDataService.queryListByPlanId(id);
//        fplancount.setFChannelDataList(fChannelDataEntityList);
        return R.ok().put("fplancount", fplancount);
    }

    /**
     * 保存引流
     */
    @SysLog("保存引流")
    @RequestMapping("/save")
    public R save(@RequestBody SFplancountEntity fPlan) {
        fPlan.setCuser(getUserId());
        fPlan.setCtime(DateUtils.getCurrentDate());
        fPlan.setUtime(DateUtils.getCurrentDate());
        fPlan.setDeptid(getDeptId());
        sFplancountService.save(fPlan);
        fChannelDataService.saveOrUpdateChannelDatas(fPlan);
        return R.ok();
    }

    /**
     * 修改引流
     */
    @SysLog("修改引流")
    @RequestMapping("/update")
    public R update(@RequestBody SFplancountEntity fPlan) {
        ValidatorUtils.validateEntity(fPlan);
        fPlan.setUtime(DateUtils.getCurrentDate());
        fPlan.setDeptid(getDeptId());
        sFplancountService.updateById(fPlan);//全部更新
        fChannelDataService.saveOrUpdateChannelDatas(fPlan);
        return R.ok();
    }

    /**
     * 删除引流
     */
    @SysLog("删除引流")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        sFplancountService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
