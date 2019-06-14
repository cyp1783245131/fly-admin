package io.renren.modules.sys.controller;


import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysPositionEntity;
import io.renren.modules.sys.service.SysPositionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */
@RestController
@RequestMapping("/sys/position")
public class SysPositionController {
    @Autowired
    private SysPositionService sysPositionService;

    /**
     * 查询所有引流位置
     * @return
     */
    @RequestMapping("/list")
    public R list(){
        List<SysPositionEntity> positionList = sysPositionService.list();
        return R.ok().put("positionList", positionList);
    }
}
