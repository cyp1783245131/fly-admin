package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressDao extends BaseMapper<AddressEntity> {

    @Select("SELECT ipend FROM address WHERE city =#{city} LIMIT #{flow}")
    public List<AddressEntity> findIpByCity(String city, Double flow);
}
