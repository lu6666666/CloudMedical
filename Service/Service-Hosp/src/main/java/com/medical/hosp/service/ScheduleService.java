package com.medical.hosp.service;

import com.medical.model.Schedule;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
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

    /**
     * 获取科室排班信息/分页
     * @param currentPage
     * @param pageSize
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String, Object> findScheduleTimeService(Long currentPage, Long pageSize, String hoscode, String depcode);

    /**
     * 获取当天排班内容
     * @return
     */
    List<Schedule> findScheduleTimeInfoService(String hoscode, String depcode, String workDate);
}
