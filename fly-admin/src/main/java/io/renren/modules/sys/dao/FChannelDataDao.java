package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.FChannelDataEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 计划渠道数据表
 * 
 * @author wujp
 * @email 46094@wisdri.com
 * @date 2019-03-13 09:49:43
 */
@Mapper
public interface FChannelDataDao extends BaseMapper<FChannelDataEntity> {
	
}
