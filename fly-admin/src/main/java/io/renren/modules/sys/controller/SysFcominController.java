package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysFcominEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import io.renren.modules.sys.service.SysFcominService;
import io.renren.modules.sys.service.SysFplancountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("sys/fcomin")
public class SysFcominController extends AbstractController {


    @Autowired
    private SysFcominService sysFcominService;

    @Autowired
    private SysFplancountService fplancountService;

//    @Value("${spring.servlet.multipart.location}")
//    private String tmpPath;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        String id = (String) params.get("id");
        SysFplancountEntity fplancount = fplancountService.getById(id);

        int count = sysFcominService.count(new QueryWrapper<SysFcominEntity>().eq("fplanid", id));
        PageUtils page = null;
        if (count > 0) {
            page = sysFcominService.queryPage(params);
        } else {
            sysFcominService.buildFlowData(fplancount);
            page = sysFcominService.queryPage(params);
        }
        return R.ok().put("page", page);
    }

    /**
     * 引流统计信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SysFcominEntity fcominEntity = sysFcominService.getById(id);

        return R.ok().put("fcomin", fcominEntity);
    }

//    @RequestMapping("/export")
//    public void export() {
//        List<SysFcominEntity> list = sysFcominService.selectList(null);
//
//        HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件
//        HSSFSheet sheet = wb.createSheet("用户信息");//创建一个工作薄
//        sheet.setColumnWidth((short) 3, 50 * 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
//        sheet.setColumnWidth((short) 4, 50 * 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
//        sheet.setDefaultRowHeight((short) 300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法
//        HSSFDataFormat format = wb.createDataFormat();   //--->单元格内容格式
//        HSSFRow row1 = sheet.createRow(0);   //--->创建一行
//        // 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
////        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
//
//        HSSFCell cell1 = row1.createCell((short) 0);   //--->创建一个单元格
//        cell1.setCellValue("店铺引流");
//
//        HSSFRow row2 = sheet.createRow(1);   ////创建第二列 标题
//
//        HSSFCell fip = row2.createCell((short) 0);   //--->创建一个单元格
//        fip.setCellValue("IP地址");
//
//        HSSFCell fsource = row2.createCell((short) 1);   //--->创建一个单元格
//        fsource.setCellValue("来源");
//
//        HSSFCell fdate = row2.createCell((short) 2);   //--->创建一个单元格
//        fdate.setCellValue("日期");
//
//        HSSFCell ftime = row2.createCell((short) 3);   //--->创建一个单元格
//        ftime.setCellValue("时间");
//
//        HSSFCell fbrowse = row2.createCell((short) 4);   //--->创建一个单元格
//        fbrowse.setCellValue("浏览次数");
//
//        HSSFCell fconversion = row2.createCell((short) 5);   //--->创建一个单元格
//        fconversion.setCellValue("预期转化率");
//
//        HSSFCell faddress = row2.createCell((short) 6);   //--->创建一个单元格
//        faddress.setCellValue("访问地址");
//        int index = 0;//记录额外创建的sheet数量
//        for (int i = 0; i < list.size(); i++) {
//            if ((i + 1) % 65535 == 0) {
//                sheet = wb.createSheet("用户信息" + index);
//                HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题
//                HSSFCell fips = rows.createCell((short) 0);   //--->创建一个单元格
//                fips.setCellValue(list.get(i).getFip());
//
//                HSSFCell fsources = rows.createCell((short) 1);   //--->创建一个单元格
//                fsources.setCellValue(list.get(i).getFsource());
//
//                HSSFCell fdates = rows.createCell((short) 2);   //--->创建一个单元格
//                fdates.setCellValue(list.get(i).getFdate());
//
//                HSSFCell ftimes = rows.createCell((short) 3);   //--->创建一个单元格
//                ftimes.setCellValue(list.get(i).getFtime());
//
//                HSSFCell fbrowses = rows.createCell((short) 4);   //--->创建一个单元格
//                fbrowses.setCellValue(list.get(i).getFbrowse());
//
//                HSSFCell fconversions = rows.createCell((short) 5);   //--->创建一个单元格
//                fconversions.setCellValue(list.get(i).getFconversion());
//
//                HSSFCell faddresss = rows.createCell((short) 6);   //--->创建一个单元格
//                faddresss.setCellValue(list.get(i).getFaddress());
//                index++;
//            }
//        }
//        FileOutputStream fileOut = null;
//        try {
//            fileOut = new FileOutputStream("d:\\fconmin.xls");
//            wb.write(fileOut);
//            //fileOut.close();
//            System.out.print("OK");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fileOut != null) {
//                try {
//                    fileOut.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    /**
     * 导出文件
     */
    @RequestMapping("/export")
    public void export(@RequestParam("id") String id,@RequestParam("type") String type, HttpServletResponse response){
        // System.out.println(ids);
//        String dstPath=tmpPath.substring(0,tmpPath.lastIndexOf("/"))+ File.separator+"template/引流数据导出文件.xlsx";
        SysFplancountEntity fplan = fplancountService.getById(id);
        String dstPath="d:/引流数据导出文件.xlsx";
        sysFcominService.exportFile(fplan,type,dstPath,response);
    }

    /**
     * 统计引流总数和符合区域引流数
     */
    @RequestMapping("/count")
    public R count(@RequestParam String id) {
        int count = sysFcominService.count(new QueryWrapper<SysFcominEntity>().eq("fplanid", id));
        int area_count = sysFcominService.count(new QueryWrapper<SysFcominEntity>().eq("fplanid", id).eq("fisarea", "1"));
        return R.ok().put("count", count).put("area_count", area_count);
    }


}
