/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://47.104.82.199
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysMerchantService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 部门管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysMerchantService sysMerchantService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return deptList;
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public R select() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
        List<SysMerchantEntity> merchantEntityList = new ArrayList<>();
        System.err.println(deptList.toString());
        System.err.println("----------------------");

        for (SysDeptEntity deptEntity : deptList) {
            merchantEntityList = sysMerchantService.findByMid(deptEntity.getMid());
        }

        for (SysMerchantEntity merchant : merchantEntityList) {
            SysDeptEntity deptEntity = new SysDeptEntity();
            if (deptList == null || deptList.isEmpty() || deptList.size() < 0) {
                deptEntity.setMid(merchant.getMid());
                deptEntity.setName(merchant.getMerchantName());
                deptEntity.setParentId(0L);
                deptEntity.setOrderNum(0);

            } else {
                deptEntity.setMid(merchant.getMid());
                deptEntity.setName(merchant.getMerchantName());
                deptEntity.setParentId(0L);
                deptEntity.setOrderNum(0);
            }
            deptList.add(deptEntity);
        }
        System.err.println(merchantEntityList.toString());
        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok().put("deptList", deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public R info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }

                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;
        }

        return R.ok().put("deptId", deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.getById(deptId);

        return R.ok().put("dept", dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public R save(@RequestBody SysDeptEntity dept) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setName(dept.getParentName());

        sysDeptEntity.setOrderNum(0);
        if(dept.getParentId() == null || dept.getParentId().equals("")){
            sysDeptEntity.setParentId(0L);
            sysDeptService.save(sysDeptEntity);
        }else{
            sysDeptEntity.setParentId(dept.getDeptId());
        }

        if(sysDeptEntity.getDeptId() == null || sysDeptEntity.getDeptId().equals("")){
            List<SysDeptEntity> list1 = sysDeptService.list();
            for (SysDeptEntity deptEntity : list1) {
                if(deptEntity.getName() == sysDeptEntity.getName()){
                    dept.setParentId(deptEntity.getDeptId());
                }
            }
        }else{
            dept.setParentId(sysDeptEntity.getDeptId());
        }

        sysDeptService.save(dept);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public R update(@RequestBody SysDeptEntity dept) {
        sysDeptService.updateById(dept);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public R delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return R.error("请先删除子部门");
        }
        SysDeptEntity deptEntity = sysDeptService.getById(deptId);
        List<SysUserEntity> userEntityList = sysUserService.list();
        for (SysUserEntity sysUserEntity : userEntityList) {
            if(sysUserEntity.getDeptId() == deptEntity.getDeptId()){
                return R.error("该部门下有用户");
            }
        }

        sysDeptService.removeById(deptId);

        return R.ok();
    }

}
