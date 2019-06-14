package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.ArrayListUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysFcominDao;
import io.renren.modules.sys.entity.AddressEntity;
import io.renren.modules.sys.entity.FChannelDataEntity;
import io.renren.modules.sys.entity.SysFcominEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.service.AddressService;
import io.renren.modules.sys.service.FChannelDataService;
import io.renren.modules.sys.service.SysFcominService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sysFcominService")
public class SysFcominServiceImpl extends ServiceImpl<SysFcominDao, SysFcominEntity> implements SysFcominService {


    @Autowired
    private AddressService addressService;

    @Autowired
    private SysFcominService sysFcominService;

    @Autowired
    private FChannelDataService fChannelDataService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = (String) params.get("id");
        String type = (String) params.get("type");

        IPage<SysFcominEntity> page = this.page(
                new Query<SysFcominEntity>().getPage(params),
                new QueryWrapper<SysFcominEntity>().eq("fplanid", id)
        );
        if ("area".equalsIgnoreCase(type)) {
            page = this.page(
                    new Query<SysFcominEntity>().getPage(params),
                    new QueryWrapper<SysFcominEntity>().eq("fplanid", id).eq("fisarea", "1")
            );

        }

//        IPage<SysFcominEntity> page = this.page(
//                new Query<SysFcominEntity>().getPage(params),
//                new QueryWrapper<SysFcominEntity>()
//                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
//        );

        return new PageUtils(page);
    }

    @Override
    public void buildFlowData(SysFplancountEntity fplan) {

        String planArea = fplan.getFanwei();
        String planChannel = fplan.getLocation();
        String startDate = fplan.getKaishi();
        String endDate = fplan.getJieshu();
        String planFlow = fplan.getFlow();
        if (StringUtils.isBlank(planFlow)) {
            return;
        }
        Double planFlowNums = Double.parseDouble(planFlow);
        if(planFlowNums<=100){
            planFlowNums=planFlowNums*10000;
        }
        String[] areas = planArea.split("-");
        String[] channels = planChannel.split(",");
        List<SysFcominEntity> dataList = new ArrayList<SysFcominEntity>();
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
                    String channel = ArrayListUtils.getElementFromArray(channels);
                    String flowDate = DateUtils.randomDate(startDate, endDate, true);
                    String flowTime = DateUtils.randomDate(startDate, endDate, false);
                    long num = DateUtils.random(1, 30);
                    String rate = DateUtils.getRateNum(num, 30);
                    SysFcominEntity ff = new SysFcominEntity();
                    ff.setFplanid(fplan.getId());
                    ff.setFip(flowIp);
                    ff.setFsource(channel);
                    ff.setFdate(flowDate);
                    ff.setFtime(flowTime);
                    ff.setFbrowse(String.valueOf(num));
                    ff.setFarea(area);
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
                    String channel = ArrayListUtils.getElementFromArray(channels);
                    String flowTime = DateUtils.randomDate(startDate, endDate, false);
                    String flowDate = DateUtils.randomDate(startDate, endDate, true);
                    long num = DateUtils.random(1, 30);
                    String rate = DateUtils.getRateNum(num, 30);
                    SysFcominEntity ff = new SysFcominEntity();
                    ff.setFplanid(fplan.getId());
                    ff.setFip(flowIp);
                    ff.setFsource(channel);
                    ff.setFdate(flowDate);
                    ff.setFtime(flowTime);
                    ff.setFbrowse(String.valueOf(num));
                    ff.setFarea(area);
                    ff.setFconversion(rate);
                    ff.setFisarea("0");
                    ff.setFaddress(fcd.getCurl());
                    ff.setCdate(DateUtils.getCurrentDate());
                    ff.setUdate(DateUtils.getCurrentDate());
                    dataList.add(ff);
                }
            }
        }

        sysFcominService.saveBatch(dataList);

    }


    /**
     * 导出数据
     *
     * @param fplan
     * @param type
     * @param dstPath
     * @param response
     */
    @Override
    public void exportFile(SysFplancountEntity fplan, String type, String dstPath, HttpServletResponse response) {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            List<SysFcominEntity> dataList = null;
            if ("area".equalsIgnoreCase(type)) {
                dataList = this.list(new QueryWrapper<SysFcominEntity>()
                        .eq("fplanid", fplan.getId()).eq("fisarea", "1")
                );
            }
            if ("all".equalsIgnoreCase(type)) {
                dataList = this.list(new QueryWrapper<SysFcominEntity>()
                        .eq("fplanid", fplan.getId())
                );
            }

//            File fi = new File(dstPath);
//            if (!fi.exists()) {
//                //先得到文件的上级目录，并创建上级目录，在创建文件
//                fi.getParentFile().mkdir();
//                try {
//                    //创建文件
//                    fi.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            wb = new XSSFWorkbook(new FileInputStream(fi));

            // 读取了模板内所有sheet内容
            XSSFSheet sheet = wb.createSheet("店铺引流");
            sheet.setColumnWidth(0,30 * 256);
            sheet.setColumnWidth(1, 30 * 256);
            sheet.setColumnWidth(2, 30 * 256);
            sheet.setColumnWidth(3, 30 * 256);
            sheet.setColumnWidth(4, 30 * 256);
            sheet.setColumnWidth(5, 30 * 256);
            sheet.setColumnWidth(6, 30 * 256);
            sheet.setDefaultRowHeight((short) 300);
//
//            XSSFRow row1 = sheet.createRow(0);   //--->创建一行
//            // 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
////        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
//            XSSFCell cell1 = row1.createCell(0);   //--->创建一个单元格
//            cell1.setCellValue("店铺引流");

            XSSFRow row= sheet.createRow(0);   ////创建第二列 标题

            XSSFCell fip = row.createCell((short) 0);   //--->创建一个单元格
            fip.setCellValue("IP地址");

            XSSFCell fsource = row.createCell((short) 1);   //--->创建一个单元格
            fsource.setCellValue("来源");

            XSSFCell fdate = row.createCell((short) 2);   //--->创建一个单元格
            fdate.setCellValue("日期");

            XSSFCell ftime = row.createCell((short) 3);   //--->创建一个单元格
            ftime.setCellValue("时间");

            XSSFCell fbrowse = row.createCell((short) 4);   //--->创建一个单元格
            fbrowse.setCellValue("浏览次数");

            XSSFCell fconversion = row.createCell((short) 5);   //--->创建一个单元格
            fconversion.setCellValue("预期转化率");

            XSSFCell faddress = row.createCell((short) 6);   //--->创建一个单元格
            faddress.setCellValue("访问地址");

            for (int i = 0; i < dataList.size(); i++) {
                SysFcominEntity data = dataList.get(i);
                XSSFRow rows = sheet.createRow(i + 1);
                XSSFCell fips  = rows.createCell(0);
                fips .setCellValue(data.getFip());
                XSSFCell fsources  = rows.createCell(1);
                fsources .setCellValue(data.getFsource());
                XSSFCell fdates  = rows.createCell(2);
                fdates .setCellValue(data.getFdate());
                XSSFCell ftimes  = rows.createCell(3);
                ftimes .setCellValue(data.getFtime());
                XSSFCell fbrowses  = rows.createCell(4);
                fbrowses .setCellValue(data.getFbrowse());
                XSSFCell fconversions  = rows.createCell(5);
                fconversions .setCellValue(data.getFconversion());
                XSSFCell faddresss  = rows.createCell(6);
                faddresss .setCellValue(data.getFaddress());
            }

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(dstPath);
                wb.write(fileOut);
                //fileOut.close();
                System.out.print("OK");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOut != null) {
                    try {
                        fileOut.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
