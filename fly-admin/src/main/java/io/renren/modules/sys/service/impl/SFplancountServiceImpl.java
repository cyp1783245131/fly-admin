package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SFplancountDao;
import io.renren.modules.sys.entity.SFplancountEntity;
import io.renren.modules.sys.service.AddressService;
import io.renren.modules.sys.service.FChannelDataService;
import io.renren.modules.sys.service.SFplancountService;
import io.renren.modules.sys.service.SysFcominService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sFplancount")
public class SFplancountServiceImpl extends ServiceImpl<SFplancountDao, SFplancountEntity> implements SFplancountService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private SysFcominService sysFcominService;

    @Autowired
    private FChannelDataService fChannelDataService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String dname = (String) params.get("dname");

        IPage<SFplancountEntity> page = this.page(
                new Query<SFplancountEntity>().getPage(params),
                new QueryWrapper<SFplancountEntity>()
                        .like(StringUtils.isNotBlank(dname), "dname", dname)
        );

        return new PageUtils(page);
    }

}
