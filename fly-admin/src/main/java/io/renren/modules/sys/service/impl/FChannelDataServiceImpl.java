package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.FChannelDataDao;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.entity.SFplancountEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.service.FChannelDataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("fChannelDataService")
public class FChannelDataServiceImpl extends ServiceImpl<FChannelDataDao, FChannelDataEntity> implements FChannelDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FChannelDataEntity> page = this.page(
                new Query<FChannelDataEntity>().getPage(params),
                new QueryWrapper<FChannelDataEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveOrUpdateChannelDatas(SysFplancountEntity fPlan) {
        List<FChannelDataEntity> dataList=fPlan.getFChannelDataList();
        this.baseMapper.delete(new QueryWrapper<FChannelDataEntity>().eq("cplanid",fPlan.getId()));
        if(dataList!=null&&dataList.size()>0){
            for (FChannelDataEntity fcd:dataList) {
                fcd.setCplanid(fPlan.getId());
                fcd.setCtime(DateUtils.getCurrentDate());
                fcd.setUtime(DateUtils.getCurrentDate());
                this.baseMapper.insert(fcd);
            }
        }
    }

    @Override
    public void saveOrUpdateChannelDatas(SFplancountEntity fPlan) {
        List<FChannelDataEntity> dataList=fPlan.getFChannelDataList();
        this.baseMapper.delete(new QueryWrapper<FChannelDataEntity>().eq("cplanid",fPlan.getId()));
        if(dataList!=null&&dataList.size()>0){
            for (FChannelDataEntity fcd:dataList) {
                fcd.setCplanid(fPlan.getId());
                fcd.setCtime(DateUtils.getCurrentDate());
                fcd.setUtime(DateUtils.getCurrentDate());
                this.baseMapper.insert(fcd);
            }
        }
    }

    @Override
    public List<FChannelDataEntity> queryListByPlanId(Long cplanid) {
        return this.baseMapper.selectList(new QueryWrapper<FChannelDataEntity>().eq("cplanid",cplanid));
    }


}
