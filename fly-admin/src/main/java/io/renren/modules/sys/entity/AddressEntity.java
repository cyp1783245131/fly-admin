package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName("address")
public class AddressEntity {
    /**
     * 主键id
     */
    @TableId
    private Integer id;
    /**
     * ip前三位，去掉符号
     */
    private Long ip;
    /**
     * 国家
     */
    private String country;
    /**
     * 地区
     */
    private String area;
    /**
     * 省份
     */
    private String region;
    /**
     * 市区
     */
    private String city;
    /**
     * 县城
     */
    private String county;
    /**
     * 网络运营商
     */
    private String isp;
    /**
     * 国家id
     */
    private String countryId;
    /**
     * 地区id
     */
    private String areaId;
    /**
     * 省份id
     */
    private String regionId;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 县城id
     */
    private String countyId;
    /**
     * 网络运营商id
     */
    private String ispId;
    /**
     * 同步
     */
    private Integer sync;
    /**
     *
     */
    private String ipstart;
    /**
     * IP地址
     */
    private String ipend;

}
