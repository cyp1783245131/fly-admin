package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SGenerateEntity;
import lombok.experimental.Delegate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SGenerateDao extends BaseMapper<SGenerateEntity> {


    @Select("SELECT * FROM s_generate WHERE gdate =#{days}")
    List<SGenerateEntity> selectByDays(String days);

//    @Delete("DELETE FROM s_generate WHERE id = #{id}")
//    void deleteByid(Long id);
}
