package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.hosp.repository.ScheduleRepository;
import com.medical.hosp.service.ScheduleService;
import com.medical.model.Department;
import com.medical.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * @author Luo.X
 * @Description 排班 service接口实现类
 * @CreateTime 2022-09-12 13:55
 * @Version 1.0
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void saveScheduleService(Map<String, Object> switchMap) {
        // 将集合转换为实体类
        Schedule schedule = JSONObject.parseObject(JSONObject.toJSONString(switchMap), Schedule.class);
        scheduleRepository.save(schedule);
    }

    @Override
    public Page findScheduleService(Map<String, Object> switchMap) {
        // 根据创建时间排序 降序
        Sort sort = Sort.by(Sort.Direction.DESC, "workDate");
        // 获取当前页 因为mongodb分页使用0开始的所以要减一
        int currentPage = StringUtils.isEmpty(switchMap.get("page")) ? 0 : Integer.parseInt((String) switchMap.get("page")) - 1;
        // 获取每页条数
        int pageSize = StringUtils.isEmpty(switchMap.get("limit")) ? 10 : Integer.parseInt((String) switchMap.get("limit"));
        // 分页并排序
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        Schedule schedule = new Schedule();
        schedule.setHoscode((String) switchMap.get("hoscode"));
        // 创建匹配器 条件查询的方式
        ExampleMatcher matcher = ExampleMatcher.matching() // 构造对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 改变默认字符串匹配方式为： 模糊查询
                .withIgnoreCase(true);  /* 忽略大小写 */
        /* 创建实例 */
        Example<Schedule> example = Example.of(schedule,matcher);

        Page<Schedule> page = scheduleRepository.findAll(example,pageable);
        return page;
    }

    @Override
    public void removeScheduleService(Map<String, Object> switchMap) {
        Schedule schedule = new Schedule();
        // 获取删除条件
        schedule.setHoscode((String) switchMap.get("hoscode"));
        schedule.setHosScheduleId((String) switchMap.get("hosScheduleId"));
        // 现根据条件查询
        Example<Schedule> example = Example.of(schedule);
        Optional<Schedule> optional = scheduleRepository.findOne(example);
        // 获取查询到的实体对象
        schedule = optional.get();

        // 根据实体对象删除
        scheduleRepository.delete(schedule);
    }
}
