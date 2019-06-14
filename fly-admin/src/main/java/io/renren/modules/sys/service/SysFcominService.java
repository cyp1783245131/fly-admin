package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysFcominEntity;
import io.renren.modules.sys.entity.SysFplancountEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface SysFcominService extends IService<SysFcominEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void buildFlowData(SysFplancountEntity fplancountEntity);

    void exportFile(SysFplancountEntity fplan, String type, String dstPath, HttpServletResponse response);
}
