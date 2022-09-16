package com.medical.hosp.controller.api;

import com.medical.exception.customizeException;
import com.medical.helper.HttpRequestHelper;
import com.medical.hosp.service.DepartmentService;
import com.medical.hosp.service.HospitalService;
import com.medical.hosp.service.HospService;
import com.medical.hosp.service.ScheduleService;
import com.medical.model.Department;
import com.medical.model.Hospital;
import com.medical.result.Result;
import com.medical.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Luo.X
 * @Description
 * @CreateTime 2022-09-11 11:02
 * @Version 1.0
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospService hospService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 上传医院信息接口
     */
    @ApiOperation(value = "上传医院信息接口")
    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request) {
        // 获取传递过来的医院信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);

        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }

        // 传输过程中 “+” 转换为了“ ” 因此转换回来否则图片不显示
        String logoData = (String) switchMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        switchMap.put("logoData", logoData);

        hospitalService.saveHospService(switchMap);
        return Result.ok();
    }

    /**
     * 查询医院信息
     */
    @ApiOperation(value = "查询医院信息")
    @PostMapping("/hospital/show")
    public Result getHospitalShow(HttpServletRequest request) {
        // 获取传递过来的医院信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 获取医院编号
        String hoscode = (String) switchMap.get("hoscode");
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        // 调用方法根据医院编号查询医院信息
        Hospital hospital = hospitalService.getHospitalShowService(hoscode);
        return Result.ok(hospital);
    }

    /**
     * 上传科室信息
     */
    @ApiOperation(value = "上传科室信息")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        // 获取传递过来的科室信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.saveDepartmentService(switchMap);
        return Result.ok();
    }

    /**
     * 分页(mongodb)查询科室
     */
    @ApiOperation(value = "分页查询科室信息")
    @PostMapping("/department/list")
    public Result findDepartment(HttpServletRequest request) {
        // 接收传递过来的分页信息以及条件
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        Page<Department> page = this.departmentService.findDepartmentService(switchMap);
        return Result.ok(page);
    }

    /**
     * 删除科室信息
     */
    @ApiOperation(value = "删除科室信息")
    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        // 获取需要删除的科室信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.removeDepartmentService(switchMap);
        return Result.ok();
    }

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 上传排班信息
     */
    @ApiOperation(value = "上传排班信息")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        // 获取传递过来的排班信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);

        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.saveScheduleService(switchMap);
        return Result.ok();
    }

    /**
     * 分页查询排班信息
     */
    @ApiOperation(value = "分页查询排班信息")
    @PostMapping("/schedule/list")
    public Result findSchedule(HttpServletRequest request){
        // 获取分页条件和查询条件
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        Page page = this.scheduleService.findScheduleService(switchMap);
        return Result.ok(page);
    }

    /**
     * 删除排班信息
     */
    @ApiOperation(value = "删除排班信息")
    @PostMapping("/schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        // 获取删除条件
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> switchMap = HttpRequestHelper.switchMap(map);
        // 验证签名
        boolean b = verify(switchMap);
        if (!b) {
            throw new customizeException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.removeScheduleService(switchMap);
        return Result.ok();
    }
    /**
     * 验证签名是否一致
     *
     * @param switchMap
     * @return
     */
    private boolean verify(Map<String, Object> switchMap) {
        // 获取医院编号
        String hoscode = (String) switchMap.get("hoscode");

        // 验证签名
        // 获取医院系统传递过来的签名 签名使用MD5加密
        String sign = (String) switchMap.get("sign");

        // 根据传递过来的医院编码 查询签名
        String key = hospService.getKey(hoscode);

        // 将查询到的签名进行MD5加密
        //String keyMD5 = MD5.encrypt(key);
        if (!sign.equals(key)) {
            return false;
        }
        return true;
    }
}
