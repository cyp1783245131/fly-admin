package io.renren.modules.sys.controller;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import io.renren.modules.sys.entity.SysDrainageEntity;

import io.renren.modules.sys.service.SysDrainageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * Created by Sy on 2019/5/7.
 */
@RestController
@RequestMapping("/sys/drainage")
public class SysDrainageController extends AbstractController {
    @Autowired
    private SysDrainageService sysDrainageService;
    /**
     * 所有引流列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDrainageService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
//    @RequiresPermissions("sys:user:list")
    public R findAll(){
        List<SysDrainageEntity> drainageList = sysDrainageService.list();
        return R.ok().put("drainageList", drainageList);
    }

    /**
     * 添加修改
     * @param drainage
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public R save(@RequestBody SysDrainageEntity drainage){
        //没有id是添加
        if(drainage.getCid() == null || "".equals(drainage.getCid())){
            System.out.println("添加");
            sysDrainageService.saveDrainage(drainage);
        }else{//否则是有id则未修改
            System.out.println("修改");
            sysDrainageService.updateDrainage(drainage);
        }

        return R.ok();
    }

    /**
     * 根据id查询引流对象
     * @param drainageId
     * @return
     */
    @RequestMapping("/getDrainageById/{drainageId}")
    public R getDrainageById(@PathVariable Integer drainageId){
        System.out.println(drainageId);
        System.out.println("-------------------------------------------------");
        SysDrainageEntity drainage = sysDrainageService.getById(drainageId);

        return R.ok().put("drainage", drainage);
    }


    /**
     * 删除多条引流数据
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] drainageId){
        sysDrainageService.removeByIds(Arrays.asList(drainageId));
        return R.ok();
    }

    /**
     * 删除一条引流数据
     */
    @RequestMapping("/deleteByid/{cid}")
    public R deleteByid(@PathVariable(value = "cid") Integer cid){
        sysDrainageService.removeById(cid);
        return R.ok();
    }


    /**
     * 修改状态
     */
    @RequestMapping("/subType/{cid}")
    public R subType(@PathVariable(value = "cid") Integer cid){
        SysDrainageEntity sysDrainageEntity = sysDrainageService.getById(cid);
        if(sysDrainageEntity.getPlanType().equals("0")){
            sysDrainageEntity.setPlanType("1");
        }else{
            sysDrainageEntity.setPlanType("0");
        }
        sysDrainageService.updateDrainage(sysDrainageEntity);
        return R.ok();
    }
}
