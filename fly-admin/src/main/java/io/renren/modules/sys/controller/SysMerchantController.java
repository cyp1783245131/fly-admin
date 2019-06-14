package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysDrainageEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.service.SysMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/13.
 * 商户管理
 */
@RestController
@RequestMapping("/sys/merchant")
public class SysMerchantController {
    @Autowired
    private SysMerchantService sysMerchantService;
    /**
     * 所有商户列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMerchantService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/findList")
    public List<SysMerchantEntity> findList(){
        List<SysMerchantEntity> merchantList = sysMerchantService.queryList(new HashMap<String, Object>());
        for (SysMerchantEntity merchantEntity : merchantList) {
            System.out.println(merchantEntity.toString());
        }
        return merchantList;
    }
    /**
     * 获得所有数据
     * @param params
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(@RequestParam Map<String, Object> params){
        List<SysMerchantEntity> merchantList = sysMerchantService.list();
        return R.ok().put("merchantList", merchantList);
    }

    /**
     * 修改状态
     */
    @RequestMapping("/subType/{mid}")
    public R subType(@PathVariable(value = "mid") Integer mid){
        SysMerchantEntity sysDrainageEntity = sysMerchantService.getById(mid);
        if(sysDrainageEntity.getMerchantType().equals("0")){
            sysDrainageEntity.setMerchantType("1");
        }else{
            sysDrainageEntity.setMerchantType("0");
        }
        sysMerchantService.updateMerchant(sysDrainageEntity);
        return R.ok();
    }


    /**
     * 添加修改
     * @param merchant
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody SysMerchantEntity merchant){
        //没有id是添加
        if(merchant.getMid() == null || "".equals(merchant.getMid())){
            System.out.println("添加");
            sysMerchantService.saveMerchant(merchant);
        }else{//否则是有id则未修改
            System.out.println("修改");
            sysMerchantService.updateMerchant(merchant);
        }
        return R.ok();
    }


    /**
     * 根据id查询引流对象
     * @param merchantId
     * @return
     */
    @RequestMapping("/getMerchantById/{merchantId}")
    public R getDrainageById(@PathVariable Integer merchantId){
        System.out.println(merchantId);
        System.out.println("-------------------------------------------------");
        SysMerchantEntity merchant = sysMerchantService.getById(merchantId);

        return R.ok().put("merchant", merchant);
    }


    /**
     * 删除多条数据
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] merchantId){
        sysMerchantService.removeByIds(Arrays.asList(merchantId));
        return R.ok();
    }

    /**
     * 删除一条数据
     */
    @RequestMapping("/deleteByid/{mid}")
    public R deleteByid(@PathVariable(value = "mid") Integer mid){
        sysMerchantService.removeById(mid);
        return R.ok();
    }
}
