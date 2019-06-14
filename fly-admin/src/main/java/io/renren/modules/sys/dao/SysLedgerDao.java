package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysLedgerEntity;
import io.renren.modules.sys.entity.SysMerchantEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/5/22.
 */
@Mapper
public interface SysLedgerDao extends BaseMapper<SysLedgerEntity> {
    List<SysLedgerEntity> findByTime();
}
