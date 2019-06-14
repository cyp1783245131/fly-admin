package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysPayMoneyEntity;
import io.renren.modules.sys.service.SysPayMoneyService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@RestController
@RequestMapping("sys/payMoney")
public class SysPayMoneyController {

    @Autowired
    private SysPayMoneyService sysPayMoneyService;

    /**
     * 风险列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:payMoney:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPayMoneyService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/download")
    public R download(){
        export();
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:payMoney:save")
    public R save(@RequestBody SysPayMoneyEntity sysPayMoneyEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysPayMoneyEntity);

        sysPayMoneyService.save(sysPayMoneyEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:payMoney:delete")
    public R delete(@RequestBody Long[] ids){
        sysPayMoneyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 充值列表导出
     */
    public void export() {
        List<SysPayMoneyEntity> userList = sysPayMoneyService.list();

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
        cell1.setCellValue("批量付款总览");

        HSSFRow row2 = sheet.createRow(1);   ////创建第二行 标题

        HSSFCell uidRow = row2.createCell((short) 0);   //--->创建一个单元格
        uidRow.setCellValue("ID");

        HSSFCell usernameRow = row2.createCell((short) 1);   //--->创建一个单元格
        usernameRow.setCellValue("批次号");

        HSSFCell userStime = row2.createCell((short) 2);   //--->创建一个单元格
        userStime.setCellValue("支付笔数");

        HSSFCell userEtime = row2.createCell((short) 3);   //--->创建一个单元格
        userEtime.setCellValue("支付金额");

        HSSFCell emailRow = row2.createCell((short) 4);   //--->创建一个单元格
        emailRow.setCellValue("申请时间");

        HSSFCell mobileRow = row2.createCell((short) 5);   //--->创建一个单元格
        mobileRow.setCellValue("手续费");

        for (int i = 0; i < userList.size(); i++) {
            HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题

            HSSFCell fens = rows.createCell((short) 0);   //--->创建一个单元格
            fens.setCellValue(userList.get(i).getId());

            HSSFCell name = rows.createCell((short) 1);   //--->创建一个单元格
            name.setCellValue(userList.get(i).getMonid());

            HSSFCell status = rows.createCell((short) 2);   //--->创建一个单元格
            status.setCellValue(userList.get(i).getPcount());

            HSSFCell psave = rows.createCell((short) 3);   //--->创建一个单元格
            psave.setCellValue(userList.get(i).getPmoney());

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            HSSFCell createTime1 = rows.createCell((short) 4);   //--->创建一个单元格
            createTime1.setCellValue(sdf1.format(userList.get(i).getPtime()));

            HSSFCell musername = rows.createCell((short) 5);   //--->创建一个单元格
            musername.setCellValue(userList.get(i).getPay());

//            rows.createCell(6).setCellFormula("ROUND(RAND(),4)");
        }

        //获取到总行数
        int rowNum=sheet.getLastRowNum();
        System.out.println("总行数"+rowNum);

        if(rowNum>72){

        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("d:\\导出的批量付款表.xls");
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

    @RequestMapping(value = "/uploadExcel/{type}")
    public JSONObject uploadExcel(@RequestParam(value = "file", required = false)MultipartFile file, @PathVariable("type") Integer type) {
        JSONObject json = new JSONObject();
        try {
            json.put("state", "00");  // 成功
            List<SysPayMoneyEntity> userEntityList = new ArrayList<>();

            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            HSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                SysPayMoneyEntity sysUserEntity = new SysPayMoneyEntity();

                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    continue;
                }

                if(rowNum == 1){
                    continue;
                }

                if(row.getCell(0)!=null){//第4列数据
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setId(Long.parseLong(row.getCell(0).getStringCellValue().trim() ));
                }

                if(row.getCell(1)!=null){//第4列数据
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setMonid(row.getCell(1).getStringCellValue().trim() );
                }

                if(row.getCell(2)!=null){//第5列数据
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setPcount(Integer.parseInt(row.getCell(2).getStringCellValue().trim()) );
                }

                if(row.getCell(3)!=null){//第6列数据
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setPmoney(Double.parseDouble(row.getCell(3).getStringCellValue().trim()) );
                }

                if(row.getCell(4)!=null){//第7列
                    row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
                    sysUserEntity.setPtime(HSSFDateUtil.getJavaDate(row.getCell(4).getNumericCellValue()));
                }

                if(row.getCell(5)!=null){//第5列数据
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setPay(Double.parseDouble(row.getCell(5).getStringCellValue().trim() ));
                }

                userEntityList.add(sysUserEntity);
            }
            if(null != userEntityList && userEntityList.size()>0){
                System.err.println("---------------------------");
                // 查询是否有重复
                List<SysPayMoneyEntity>  list = sysPayMoneyService.list();
                List<SysPayMoneyEntity>  list2 = new ArrayList<>();
                for (SysPayMoneyEntity sysUserEntity : list) {
                    for (SysPayMoneyEntity userEntity : userEntityList) {
                        if(sysUserEntity.getId() == userEntity.getId()){
                            list2.add(userEntity);
                        }
                    }
                }
                userEntityList.removeAll(list2);

                //调用service执行保存personInfoLists的方法
                for (SysPayMoneyEntity sysUserEntity : userEntityList) {
                    sysPayMoneyService.save(sysUserEntity);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return json;
    }


}
