package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysPmoneyEntity;
import io.renren.modules.sys.service.SysPmoneyService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by xw on 2019/6/5.
 */
@RestController
@RequestMapping("sys/pmoney")
public class SysPmoneyController {

    @Autowired
    private SysPmoneyService sysPmoneyService;

    /**
     * 风险列表查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:pmoney:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysPmoneyService.queryPage(params);
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
        List<SysPmoneyEntity> userList = sysPmoneyService.list();

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

        HSSFCell member = row2.createCell((short) 6);   //--->创建一个单元格
        member.setCellValue("组员");

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

            HSSFCell member2 = rows.createCell((short) 6);   //--->创建一个单元格
            member2.setCellValue(userList.get(i).getPname());

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
            List<SysPmoneyEntity> userEntityList = new ArrayList<>();

            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            HSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                SysPmoneyEntity sysUserEntity = new SysPmoneyEntity();

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

                if(row.getCell(6)!=null){//第5列数据
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    sysUserEntity.setPname(row.getCell(6).getStringCellValue().trim());
                }

                userEntityList.add(sysUserEntity);
            }
            if(null != userEntityList && userEntityList.size()>0){
                System.err.println("---------------------------");
                // 查询是否有重复
                List<SysPmoneyEntity>  list = sysPmoneyService.list();
                List<SysPmoneyEntity>  list2 = new ArrayList<>();
                for (SysPmoneyEntity sysUserEntity : list) {
                    for (SysPmoneyEntity userEntity : userEntityList) {
                        if(sysUserEntity.getId() == userEntity.getId()){
                            list2.add(userEntity);
                        }
                    }
                }
                userEntityList.removeAll(list2);

                //调用service执行保存personInfoLists的方法
                for (SysPmoneyEntity sysUserEntity : userEntityList) {
                    sysPmoneyService.save(sysUserEntity);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:pmoney:save")
    public R save(@RequestBody SysPmoneyEntity sysPmoneyEntity){
        //校验类型
        ValidatorUtils.validateEntity(sysPmoneyEntity);

        sysPmoneyService.save(sysPmoneyEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:pmoney:delete")
    public R delete(@RequestBody Long[] ids){
        sysPmoneyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
