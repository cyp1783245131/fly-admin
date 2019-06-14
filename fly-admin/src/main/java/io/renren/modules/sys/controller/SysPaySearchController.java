package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysDetEntity;
import io.renren.modules.sys.entity.SysPaySearchEntity;
import io.renren.modules.sys.service.SysDetService;
import io.renren.modules.sys.service.SysPaySearchService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@RestController
@RequestMapping("sys/paySearch")
public class SysPaySearchController {

    @Autowired
    private SysPaySearchService sysPaySearchService;

    @Autowired
    private SysDetService sysDetService;

    /**
     * 列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:paySearch:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPaySearchService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/download")
    public R download(){
        export();
        return R.ok();
    }

    /**
     * 充值列表导出
     */
    public void export() {
        List<SysPaySearchEntity> userList = sysPaySearchService.list();

        HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件

        HSSFFont font = wb.getFontAt((short) 0);
        font.setCharSet(HSSFFont.DEFAULT_CHARSET);
        font.setFontHeightInPoints((short)10);//更改默认字体大小
        font.setFontName("宋体");//

        HSSFSheet sheet = wb.createSheet("统计信息");//创建一个工作薄
        sheet.setDefaultRowHeight((short) 300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法

//        HSSFDataFormat format = wb.createDataFormat();   //--->单元格内容格式
        HSSFRow row1 = sheet.createRow(0);   //--->创建一行
        // 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
//        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
        HSSFCell cell1 = row1.createCell((short) 0);   //--->创建一个单元格
        cell1.setCellValue("付款余额明细总览");

        HSSFRow row2 = sheet.createRow(1);   ////创建第二行 标题

        HSSFCell uidRow = row2.createCell((short) 0);   //--->创建一个单元格
        uidRow.setCellValue("ID");

        HSSFCell usernameRow = row2.createCell((short) 1);   //--->创建一个单元格
        usernameRow.setCellValue("日期");

        HSSFCell userStime = row2.createCell((short) 2);   //--->创建一个单元格
        userStime.setCellValue("批次号");

        HSSFCell userEtime = row2.createCell((short) 3);   //--->创建一个单元格
        userEtime.setCellValue("交易号");

        HSSFCell emailRow = row2.createCell((short) 4);   //--->创建一个单元格
        emailRow.setCellValue("交易类型");

        HSSFCell mobileRow = row2.createCell((short) 5);   //--->创建一个单元格
        mobileRow.setCellValue("收入");

        HSSFCell gailvRow = row2.createCell((short) 6);   //--->创建一个单元格
        gailvRow.setCellValue("支出");

        HSSFCell smy = row2.createCell((short) 7);   //--->创建一个单元格
        smy.setCellValue("手续费");

        HSSFCell save = row2.createCell((short) 8);   //--->创建一个单元格
        save.setCellValue("余额");

        for (int i = 0; i < userList.size(); i++) {
            HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题

            HSSFCell fens = rows.createCell((short) 0);   //--->创建一个单元格
            fens.setCellValue(userList.get(i).getId());

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            HSSFCell createTime1 = rows.createCell((short) 1);   //--->创建一个单元格
            createTime1.setCellValue(sdf1.format(userList.get(i).getStime()));

            HSSFCell name = rows.createCell((short) 2);   //--->创建一个单元格
            name.setCellValue(userList.get(i).getSid());

            HSSFCell status = rows.createCell((short) 3);   //--->创建一个单元格
            status.setCellValue(userList.get(i).getSpid());

            HSSFCell psave = rows.createCell((short) 4);   //--->创建一个单元格
            psave.setCellValue(userList.get(i).getStype());

            HSSFCell pstatus = rows.createCell((short) 5);   //--->创建一个单元格
            pstatus.setCellValue(userList.get(i).getSmoney());

            HSSFCell spay = rows.createCell((short) 6);   //--->创建一个单元格
            spay.setCellValue(userList.get(i).getSpay());

            HSSFCell smys = rows.createCell((short) 7);   //--->创建一个单元格
            smys.setCellValue(userList.get(i).getSmy());

            HSSFCell saves = rows.createCell((short) 8);   //--->创建一个单元格
            saves.setCellValue(userList.get(i).getSave());

//            rows.createCell(6).setCellFormula("ROUND(RAND(),4)");
        }

        //获取到总行数
        int rowNum=sheet.getLastRowNum();
        System.out.println("总行数"+rowNum);

        if(rowNum>72){

        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("d:\\导出的付款余额明细表.xls");
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

}
