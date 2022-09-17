package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.hosp.repository.ScheduleRepository;
import com.medical.hosp.service.ScheduleService;
import com.medical.model.Department;
import com.medical.model.Schedule;
import com.medical.utils.TimeUtils;
import com.medical.vo.BookingScheduleRuleVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Autowired
    private MongoTemplate mongoTemplate;

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
        Example<Schedule> example = Example.of(schedule, matcher);

        Page<Schedule> page = scheduleRepository.findAll(example, pageable);
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

    /**
     * 获取科室排班信息/分页
     *
     * @param currentPage
     * @param pageSize
     * @param hoscode
     * @param depcode
     * @return
     */
    @Override
    public Map<String, Object> findScheduleTimeService(Long currentPage, Long pageSize, String hoscode, String depcode) {
        // 根据条件查询排班信息
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        // 聚合
        Aggregation aggregation = Aggregation.newAggregation(
                // 匹配条件
                Aggregation.match(criteria),
                // 分组字段
                Aggregation.group("workDate").first("workDate").as("workDate")
                // 统计挂号数量
                .count().as("docCount").sum("reservedNumber").as("reservedNumber").sum("availableNumber").as("availableNumber"),
                // 根据workDate排序 降序
                Aggregation.sort(Sort.Direction.DESC,"workDate"),
                // 分页
                Aggregation.skip((currentPage-1)*pageSize),
                Aggregation.limit(pageSize)
                );
        // 执行查询 参数一：聚合操作 参数二：实体类 参数三：返回结果的实体类
        AggregationResults<BookingScheduleRuleVo> aggregate = mongoTemplate.aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);
        // 获取list数据集合
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggregate.getMappedResults();
        // 分组查询的总记录数
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> a = mongoTemplate.aggregate(totalAgg,Schedule.class,BookingScheduleRuleVo.class);
        // 获取数据总条数
        int total = a.getMappedResults().size();

        // 遍历把日期改为星期
        for (BookingScheduleRuleVo b : bookingScheduleRuleVoList) {
            String dayOfWeek = TimeUtils.getDayOfWeek(new DateTime(b.getWorkDate()));
            b.setDayOfWeek(dayOfWeek);
        }
        // 将数据集合 和总数据条数封装到map集合中返回
        Map<String,Object> map = new HashMap<>();
        map.put("list",bookingScheduleRuleVoList);
        map.put("total",total);

        return map;
    }

    /**
     * 获取当天排班内容
     */
    @Override
    public List<Schedule> findScheduleTimeInfoService(String hoscode, String depcode, String workDate) {
        Date date = new DateTime(workDate).toDate();
        List<Schedule> list = scheduleRepository.findScheduleByHoscodeAndDepcodeAndWorkDate(hoscode,depcode,date);
        return list;
    }
}
