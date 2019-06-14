package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysPayMoneyDao;
import io.renren.modules.sys.entity.SysPayMoneyEntity;
import io.renren.modules.sys.service.SysPayMoneyService;
import io.renren.modules.sys.utils.ReadExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
@Service("sysPayMoneyService")
public class SysPayMoneyServiceImpl extends ServiceImpl<SysPayMoneyDao,SysPayMoneyEntity> implements SysPayMoneyService {

    //查询所有
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String starttime= (String) params.get("starttime");
        String endtime= (String) params.get("endtime");
        String monid= (String) params.get("monid");
        IPage<SysPayMoneyEntity> page = this.page(
                new Query<SysPayMoneyEntity>().getPage(params),
                new QueryWrapper<SysPayMoneyEntity>()
                        .ge(StringUtils.isNotBlank(starttime),"mtime",starttime)
                        .le(StringUtils.isNotBlank(endtime),"mtime",endtime)
                        .eq(StringUtils.isNotBlank(monid),"monid",monid)
        );

        return new PageUtils(page);
    }

    @Override
    public String readExcelFile(MultipartFile file){
        String result = "";
        //创建处理EXCEL的类  
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取上传的事件单  
        List<SysPayMoneyEntity> userList = readExcel.getExcelInfo(file);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,  
        //和你具体业务有关,这里不做具体的示范  
        if(userList != null && !userList.isEmpty()){
        result = "上传成功";
            }else{
        result = "上传失败";
        }
        return result;
    }

}
