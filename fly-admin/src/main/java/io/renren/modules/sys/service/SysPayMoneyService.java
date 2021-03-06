package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysPayMoneyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Administrator on 2019/5/27.
 */
public interface SysPayMoneyService extends IService<SysPayMoneyEntity> {

    //查询
    PageUtils queryPage(Map<String, Object> params);

    String readExcelFile(MultipartFile file);

}
