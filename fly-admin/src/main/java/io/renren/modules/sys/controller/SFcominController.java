package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SFcominEntity;
import io.renren.modules.sys.entity.SFplancountEntity;
import io.renren.modules.sys.service.SFcominService;
import io.renren.modules.sys.service.SFplancountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("s/fcomin")
public class SFcominController {

    @Autowired
    private SFcominService sFcominService;

    @Autowired
    private SFplancountService fplancountService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        String id = (String) params.get("id");
        SFplancountEntity fplancount = fplancountService.getById(id);

        int count = sFcominService.count(new QueryWrapper<SFcominEntity>().eq("fplanid", id));
        PageUtils page = null;
        if (count > 0) {
            page = sFcominService.queryPage(params);
        } else {
            sFcominService.buildFlowData(fplancount);
            page = sFcominService.queryPage(params);
        }
        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll() {
        List<SFcominEntity> list = sFcominService.list(null);
        return R.ok().put("fcominList", list);
    }

}
