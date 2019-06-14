/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://47.104.82.199
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
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
import java.util.*;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);
		System.err.println(page.toString());
		return R.ok().put("page", page);
	}

	@RequestMapping("/findAll")
	public R findAll(){
		List<SysUserEntity> userList = sysUserService.list();
		return R.ok().put("userList", userList);
	}


	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}


	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.saveUser(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}

		sysUserService.removeByIds(Arrays.asList(userIds));
		
		return R.ok();
	}

	/**
	 * 删除一条引流数据
	 */
	@RequestMapping("/deleteByid/{uid}")
	public R deleteByid(@PathVariable(value = "uid") Long uid){
		if(uid == 1L){
			return R.error("系统管理员不能删除");
		}
		Long userId = getUserId();
		if(uid == userId){
			return R.error("当前用户不能删除");
		}
		sysUserService.removeById(uid);
		return R.ok();
	}


	/**
	 * 修改状态
	 */
	@RequestMapping("/subType/{mid}")
	public R subType(@PathVariable(value = "mid") Integer mid){
		SysUserEntity sysUserEntity = sysUserService.getById(mid);
		if(sysUserEntity.getStatus() == 0){
			sysUserEntity.setStatus(1);
		}else{
			sysUserEntity.setStatus(0);
		}
		sysUserService.updateDraninage(sysUserEntity);
		return R.ok();
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
		List<SysUserEntity> userList = sysUserService.list();

		HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件
		HSSFSheet sheet = wb.createSheet("用户信息");//创建一个工作薄
		sheet.setColumnWidth((short) 3, 50 * 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
		sheet.setColumnWidth((short) 4, 50 * 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
		sheet.setDefaultRowHeight((short) 300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法
		HSSFDataFormat format = wb.createDataFormat();   //--->单元格内容格式
		HSSFRow row1 = sheet.createRow(0);   //--->创建一行
		// 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
//        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
		HSSFCell cell1 = row1.createCell((short) 0);   //--->创建一个单元格
		cell1.setCellValue("用户信息总览");


		HSSFRow row2 = sheet.createRow(1);   ////创建第二列 标题

		HSSFCell usernameRow = row2.createCell((short) 0);   //--->创建一个单元格
		usernameRow.setCellValue("用户姓名");

		HSSFCell emailRow = row2.createCell((short) 1);   //--->创建一个单元格
		emailRow.setCellValue("邮箱");

		HSSFCell mobileRow = row2.createCell((short) 2);   //--->创建一个单元格
		mobileRow.setCellValue("手机号");

		HSSFCell createTimeRow = row2.createCell((short) 3);   //--->创建一个单元格
		createTimeRow.setCellValue("创建时间");

		HSSFCell deptIdRow = row2.createCell((short) 4);   //--->创建一个单元格
		deptIdRow.setCellValue("部门ID");

		for (int i = 0; i < userList.size(); i++) {
			HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题
			HSSFCell nos = rows.createCell((short) 0);   //--->创建一个单元格
			nos.setCellValue(userList.get(i).getUsername());
			HSSFCell name = rows.createCell((short) 1);   //--->创建一个单元格
			name.setCellValue(userList.get(i).getEmail());
			HSSFCell status = rows.createCell((short) 2);   //--->创建一个单元格
			status.setCellValue(userList.get(i).getMobile());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			HSSFCell createTime = rows.createCell((short) 3);   //--->创建一个单元格
			createTime.setCellValue(sdf.format(userList.get(i).getCreateTime()));

			HSSFCell deptId = rows.createCell((short) 4);   //--->创建一个单元格
			deptId.setCellValue(userList.get(i).getDeptId());
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("d:\\user.xls");
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
	public JSONObject uploadExcel(@RequestParam(value = "file", required = false)MultipartFile file,@PathVariable("type") Integer type) {
		JSONObject json = new JSONObject();
		try {
			json.put("state", "00");  // 成功
			List<SysUserEntity> userEntityList = new ArrayList<SysUserEntity>();

			//使用POI解析Excel文件
			//如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
 			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			//根据名称获得指定Sheet对象
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			for (Row row : hssfSheet) {
				SysUserEntity sysUserEntity = new SysUserEntity();

				int rowNum = row.getRowNum();
				if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
					continue;
				}

				if(rowNum == 1){
					continue;
				}

				if(row.getCell(0)!=null){//第4列数据
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					sysUserEntity.setUsername(row.getCell(0).getStringCellValue().trim() );
				}

				if(row.getCell(1)!=null){//第5列数据
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					sysUserEntity.setEmail(row.getCell(1).getStringCellValue().trim() );
				}

				if(row.getCell(2)!=null){//第6列数据
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					sysUserEntity.setMobile(row.getCell(2).getStringCellValue().trim() );
				}

				if(row.getCell(3)!=null){//第7列
                    row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
					sysUserEntity.setCreateTime( HSSFDateUtil.getJavaDate(row.getCell(3).getNumericCellValue()));
                }

				if(row.getCell(4)!=null){//第6列数据
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					sysUserEntity.setDeptId(Long.parseLong(row.getCell(4).getStringCellValue().trim()) );
				}

				sysUserEntity.setPassword("123123");
				userEntityList.add(sysUserEntity);
			}
			if(null != userEntityList && userEntityList.size()>0){
				System.err.println("---------------------------");
				// 查询是否有重复
				List<SysUserEntity>  list = sysUserService.list();
				List<SysUserEntity>  list2 = new ArrayList<>();
				for (SysUserEntity sysUserEntity : list) {
					for (SysUserEntity userEntity : userEntityList) {
						if(sysUserEntity.getUserId() == userEntity.getUserId()){
							list2.add(userEntity);
						}
					}
				}
				userEntityList.removeAll(list2);


				//调用service执行保存personInfoLists的方法
				for (SysUserEntity sysUserEntity : userEntityList) {
					sysUserService.saveUser(sysUserEntity);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

}
