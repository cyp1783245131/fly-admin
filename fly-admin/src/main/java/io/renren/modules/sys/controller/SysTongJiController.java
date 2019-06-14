package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysTongJiEntity;
import io.renren.modules.sys.service.SysTongJiService;
import io.renren.modules.sys.utils.POIUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/17.
 */
@RestController
@RequestMapping("sys/tongji")
public class SysTongJiController {

    @Autowired
    private SysTongJiService sysTongJiService;

    /**
     * 统计查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:tongji:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysTongJiService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/download")
    public R download(){
        export();
        return R.ok();
    }

    /**
     * 文件下载
     */
    public void export() {
        List<SysTongJiEntity> userList = sysTongJiService.list();

        HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件

        HSSFFont font = wb.getFontAt((short) 0);
        font.setCharSet(HSSFFont.DEFAULT_CHARSET);
        font.setFontHeightInPoints((short)8);//更改默认字体大小
        font.setFontName("宋体");//

        HSSFSheet sheet = wb.createSheet("统计信息");//创建一个工作薄
        sheet.setDefaultRowHeight((short) 300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法

//        HSSFDataFormat format = wb.createDataFormat();   //--->单元格内容格式
        HSSFRow row1 = sheet.createRow(0);   //--->创建一行
        // 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
//        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
        HSSFCell cell1 = row1.createCell((short) 0);   //--->创建一个单元格
        cell1.setCellValue("统计信息总览");

        HSSFRow row2 = sheet.createRow(1);   ////创建第二行 标题

        HSSFCell uidRow = row2.createCell((short) 0);   //--->创建一个单元格
        uidRow.setCellValue("ID");

        HSSFCell usernameRow = row2.createCell((short) 1);   //--->创建一个单元格
        usernameRow.setCellValue("车牌号");

        HSSFCell userStime = row2.createCell((short) 2);   //--->创建一个单元格
        userStime.setCellValue("排查开始时间");

        HSSFCell userEtime = row2.createCell((short) 3);   //--->创建一个单元格
        userEtime.setCellValue("排查结束时间");

        HSSFCell emailRow = row2.createCell((short) 4);   //--->创建一个单元格
        emailRow.setCellValue("评测级别");

        HSSFCell mobileRow = row2.createCell((short) 5);   //--->创建一个单元格
        mobileRow.setCellValue("评测结果");

        HSSFCell gailvRow = row2.createCell((short) 6);   //--->创建一个单元格
        gailvRow.setCellValue("评测概率");

        for (int i = 0; i < userList.size(); i++) {
            HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题

            HSSFCell fens = rows.createCell((short) 0);   //--->创建一个单元格
            fens.setCellValue(userList.get(i).getId());

            HSSFCell nos = rows.createCell((short) 1);   //--->创建一个单元格
            nos.setCellValue(userList.get(i).getTcar());

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            HSSFCell createTime1 = rows.createCell((short) 2);   //--->创建一个单元格
            createTime1.setCellValue(sdf1.format(userList.get(i).getStime()));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            HSSFCell createTime2 = rows.createCell((short) 3);   //--->创建一个单元格
            createTime2.setCellValue(sdf2.format(userList.get(i).getEtime()));

            HSSFCell name = rows.createCell((short) 4);   //--->创建一个单元格
            name.setCellValue(userList.get(i).getTlevel());

            HSSFCell status = rows.createCell((short) 5);   //--->创建一个单元格
            status.setCellValue(userList.get(i).getTlast());

            rows.createCell(6).setCellFormula("ROUND(RAND(),4)");
        }

        //获取到总行数
        int rowNum=sheet.getLastRowNum();
        System.out.println("总行数"+rowNum);

        if(rowNum>72){

        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("d:\\导出的统计表.xls");
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
    }

    /**
     * * 从web页面导入单个excel文件
     *
     * @param excelFile 上传的excel文件
     * @param request   Request对象
     * @return 内容
     * @throws IOException
     */
    @PostMapping("/import")
    @ResponseBody
    public List<SysTongJiEntity> importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request) throws IOException {

        // 1: 转存文件
        if (!excelFile.isEmpty()) {
            /**
             * 这里的getRealPath("/")是打包之后的项目的根目录。
             * 也就是 target\项目名-1.0-SNAPSHOT\
             */
            String storePath = request.getSession().getServletContext().getRealPath("/") + "upload/temp/";
            excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
        }

        // 2: 解析excel数据
        List<String[]> excelData = POIUtil.readExcelFile(excelFile, 1);

        List<SysTongJiEntity> userInfoList = new ArrayList<>();
        for (String[] arr : excelData) {
            SysTongJiEntity userInfo = new SysTongJiEntity();
            userInfo.setId(Long.valueOf(arr[0]));
            userInfo.setTcar(arr[1]);
            userInfo.setTlast(arr[2]);
            userInfo.setTlevel(arr[3]);
            userInfoList.add(userInfo);
        }
        // 3: 输出excel数据
        return userInfoList;
    }

}
