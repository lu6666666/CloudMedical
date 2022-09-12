package com.medical.hosp.service;

import com.medical.model.Department;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 科室 service接口
 * @CreateTime 2022-09-11 16:30
 * @Version 1.0
 */
public interface DepartmentService {
    /**
     * 上传科室
     * @param switchMap
     */
    void saveDepartmentService(Map<String, Object> switchMap);

    /**
     * 分页(mongodb)查询科室
     * @param switchMap
     */
    Page<Department> findDepartmentService(Map<String, Object> switchMap);

    /**
     * 删除科室信息
     * @param switchMap
     */
    void removeDepartmentService(Map<String, Object> switchMap);
}
