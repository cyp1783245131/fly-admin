package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysMerchantEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/13.
 */
@Mapper
public interface SysMerchantDao extends BaseMapper<SysMerchantEntity> {
    List<SysMerchantEntity> queryList(Map<String, Object> params);

    List<SysMerchantEntity> findByMid(Long mid);
}
