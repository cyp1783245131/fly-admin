package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysCodeEntity;
import io.renren.modules.sys.entity.SysTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2019/5/28.
 */
@Mapper
public interface SysTypeDao extends BaseMapper<SysTypeEntity> {
}
