package io.renren.modules.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysPositionDao;
import io.renren.modules.sys.entity.SysPositionEntity;
import io.renren.modules.sys.service.SysPositionService;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2019/5/9.
 */
@Service("sysPositionService")
public class SysPositionServiceImpl extends ServiceImpl<SysPositionDao, SysPositionEntity> implements SysPositionService {

}
