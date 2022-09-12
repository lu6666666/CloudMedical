package com.medical.hosp.service;

import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 排班 service接口
 * @CreateTime 2022-09-12 13:54
 * @Version 1.0
 */
public interface ScheduleService {

    /**
     * 上传排班信息
     * @param switchMap
     */
    void saveScheduleService(Map<String, Object> switchMap);

    /**
     * 分页查询排班信息
     * @param switchMap
     * @return
     */
    Page findScheduleService(Map<String, Object> switchMap);

    /**
     * 删除排班信息
     * @param switchMap
     */
    void removeScheduleService(Map<String, Object> switchMap);
}
