package com.medical.hosp.controller;

import com.medical.hosp.service.ScheduleService;
import com.medical.model.Schedule;
import com.medical.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 排班 controller
 * @CreateTime 2022-09-16 11:19
 * @Version 1.0
 */
@Api(tags = "排班信息")
@RestController
@RequestMapping("/admin/Schedule")
// 解决跨域问题
//@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 获取科室排班信息/分页
     * @return
     */
    @ApiOperation(value = "获取科室排班日期/分页")
    @GetMapping("/findScheduleTime/{currentPage}/{pageSize}/{hoscode}/{depcode}")
    public Result findScheduleTime(@PathVariable Long currentPage,@PathVariable Long pageSize ,@PathVariable String hoscode,@PathVariable String depcode){
        Map<String,Object> map = scheduleService.findScheduleTimeService(currentPage,pageSize,hoscode,depcode);
        return Result.ok(map);
    }

    /**
     * 获取当天排班内容
     */
    @ApiOperation(value = "获取当天排班内容")
    @GetMapping("/findScheduleTimeInfo/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleTimeInfo( @PathVariable String hoscode,@PathVariable String depcode,@PathVariable String workDate){
        List<Schedule> list = scheduleService.findScheduleTimeInfoService(hoscode,depcode,workDate);
        return Result.ok(list);
    }
}
