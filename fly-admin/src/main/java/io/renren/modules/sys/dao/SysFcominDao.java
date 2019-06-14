package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysFcominEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 引流统计Dao
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysFcominDao extends BaseMapper<SysFcominEntity> {

}
