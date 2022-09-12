package com.medical.hosp.controller.api;

import com.medical.exception.ExceptionHandling;
import com.medical.exception.customizeException;
import com.medical.helper.HttpRequestHelper;
import com.medical.hosp.service.DepartmentService;
import com.medical.hosp.service.HospRepositoryService;
import com.medical.hosp.service.HospService;
import com.medical.model.Hospital;
import com.medical.result.Result;
import com.medical.result.ResultCodeEnum;
import com.medical.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospRepositoryService hospRepositoryService;

    @Autowired
    private HospService hospService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 上传医院接口
     */
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

        hospRepositoryService.saveHospService(switchMap);
        return Result.ok();
    }

    /**
     * 查询医院
     */
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
        Hospital hospital = hospRepositoryService.getHospitalShowService(hoscode);
        return Result.ok(hospital);
    }

    /**
     * 上传科室
     */
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
