package com.medical.hosp.service;

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
}
