package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.ArrayListUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SFcominDao;
import io.renren.modules.sys.entity.AddressEntity;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.entity.SFcominEntity;
import io.renren.modules.sys.entity.SFplancountEntity;
import io.renren.modules.sys.service.AddressService;
import io.renren.modules.sys.service.FChannelDataService;
import io.renren.modules.sys.service.SFcominService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sFcominService")
public class SFcominServiceImpl extends ServiceImpl<SFcominDao, SFcominEntity> implements SFcominService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private SFcominService sFcominService;

    @Autowired
    private FChannelDataService fChannelDataService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String id = (String) params.get("id");
        IPage<SFcominEntity> page = this.page(
                new Query<SFcominEntity>().getPage(params),
                new QueryWrapper<SFcominEntity>().eq("fplanid", id)
        );

        return new PageUtils(page);
    }

    @Override
    public void buildFlowData(SFplancountEntity fplan) {

        String planArea = fplan.getFanwei();
        String startDate = fplan.getKaishi();
        String endDate = fplan.getJieshu();
        String planFlow = fplan.getFlow();
        if (StringUtils.isBlank(planFlow)) {
            return;
        }
        Double planFlowNums = Double.parseDouble(planFlow);
        int planFlowNum = (new Double(planFlowNums)).intValue();
            long random = DateUtils.random(1, 10);
            long random2 = DateUtils.random(1, 100);
        if (planFlowNum <= 100) {
            int returnId=new Long(random).intValue();
            int returnId2=new Long(random2).intValue();
            planFlowNum = returnId* 10000+returnId2;
        }
        if (planFlowNums <= 100) {
            planFlowNums = planFlowNums * 10000;
        }
        String[] areas = planArea.split("-");
        List<SFcominEntity> dataList = new ArrayList<SFcominEntity>();
        List<AddressEntity> iplist = new ArrayList<AddressEntity>();
        List<AddressEntity> uniplist = new ArrayList<AddressEntity>();
        List<FChannelDataEntity> cdList = fChannelDataService.list(new QueryWrapper<FChannelDataEntity>().eq("cplanid", fplan.getId()));
        for (String area : areas) {
            if (area.indexOf("省") != -1 || area.indexOf("北京") != -1
                    || area.indexOf("上海") != -1 || area.indexOf("天津") != -1
                    || area.indexOf("重庆") != -1) {
                area = area.replace("省", "");
                area = area.replace("市", "");
                List<AddressEntity> areaIpList = addressService.list(new QueryWrapper<AddressEntity>()
                        .eq("region", area).last("limit 100"));
                List<AddressEntity> notAreaIpList = addressService.list(new QueryWrapper<AddressEntity>()
                        .notIn("region", StringUtils.join(areas, ",")).last("limit 100"));
                iplist.addAll(areaIpList);
                uniplist.addAll(notAreaIpList);
            } else {
                {
                    area = area.replace("省", "");
                    area = area.replace("市", "");
                    List<AddressEntity> areaIpList = addressService.list(new QueryWrapper<AddressEntity>()
                            .eq("city", area).last("100").last("limit 100"));
                    List<AddressEntity> notAreaIpList = addressService.list(new QueryWrapper<AddressEntity>()
                            .notIn("city", StringUtils.join(areas, ",")).last("limit 100"));
                    iplist.addAll(areaIpList);
                    uniplist.addAll(notAreaIpList);
                }
            }
            FChannelDataEntity fcd = (FChannelDataEntity) ArrayListUtils.getElementFromList(cdList);
            int ipNums = (int) (Math.random() * 10) + 1;
            Double areaNum = planFlowNums / ipNums;
            for (int i = 0; i < areaNum; i++) {
                AddressEntity ipRange = (AddressEntity) ArrayListUtils.getElementFromList(iplist);
                String ipStart = ipRange.getIpstart();
                ipStart = ipStart.substring(0, ipStart.lastIndexOf("."));
                for (int j = 0; j < ipNums + 1; j++) {
                    String flowIp = ipStart + "." + DateUtils.random(1, 240);
                    String flowDate = DateUtils.randomDate(startDate, endDate, true);
                    String flowTime = DateUtils.randomDate(startDate, endDate, false);
                    long num = DateUtils.random(1, planFlowNum * 10000);
                    String rate = DateUtils.getRateNum(num, 30);
                    SFcominEntity ff = new SFcominEntity();
                    ff.setFplanid(fplan.getId());
                    ff.setFip(flowIp);
                    ff.setFdate(flowDate);
                    ff.setFtime(flowTime);
                    ff.setFbrowses(String.valueOf(planFlowNum + DateUtils.random(1, 1000)));
                    ff.setFbrowse(String.valueOf(DateUtils.random(planFlowNum, planFlowNum+100)));
                    ff.setFarea(area);
                    ff.setRule(DateUtils.random(1, 100));
                    ff.setFconversion(rate);
                    ff.setFisarea("1");
                    ff.setFaddress(fcd.getCurl());
                    ff.setCdate(DateUtils.getCurrentDate());
                    ff.setUdate(DateUtils.getCurrentDate());
                    dataList.add(ff);
                }
            }
            int unIpNums = (int) (Math.random() * 10);
            for (int i = 0; i < 100; i++) {
                AddressEntity ipRange = (AddressEntity) ArrayListUtils.getElementFromList(uniplist);
                String ipStart = ipRange.getIpstart();
                ipStart = ipStart.substring(0, ipStart.lastIndexOf("."));
                for (int j = 0; j < unIpNums; j++) {
                    String flowIp = ipStart + "." + DateUtils.random(1, 240);
                    String flowTime = DateUtils.randomDate(startDate, endDate, false);
                    String flowDate = DateUtils.randomDate(startDate, endDate, true);
                    long num = DateUtils.random(1, 30);
                    String rate = DateUtils.getRateNum(num, 30);
                    SFcominEntity ff = new SFcominEntity();
                    ff.setFplanid(fplan.getId());
                    ff.setFip(flowIp);
                    ff.setFdate(flowDate);
                    ff.setFtime(flowTime);
                    ff.setFbrowses(String.valueOf(planFlowNum + DateUtils.random(1, 100)));
                    ff.setFbrowse(String.valueOf(planFlowNum));
                    ff.setFarea(area);
                    ff.setRule(DateUtils.random(1, 100));
                    ff.setFconversion(rate);
                    ff.setFisarea("0");
                    ff.setFaddress(fcd.getCurl());
                    ff.setCdate(DateUtils.getCurrentDate());
                    ff.setUdate(DateUtils.getCurrentDate());
                    dataList.add(ff);
                }
            }
        }

        sFcominService.saveBatch(dataList);

    }
}
