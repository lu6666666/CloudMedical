package com.medical.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.model.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Luo.X
 * @Description 组织架构 servic接口
 * @CreateTime 2022-09-08 9:51
 * @Version 1.0
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据上级id查询子数据列表
     */
    List<Dict> findDictByIdService(Long parentId);

    /**
     * 导出数据字典
     */
    void exportDictService(HttpServletResponse response);

    /**
     * 导入数据字典
     */
    void importDictService(MultipartFile file);
}
