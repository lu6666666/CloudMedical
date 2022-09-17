package com.medical.hosp.controller;

import com.medical.hosp.service.DepartmentService;
import com.medical.model.Department;
import com.medical.result.Result;
import com.medical.vo.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Luo.X
 * @Description 科室 Controller
 * @CreateTime 2022-09-16 9:40
 * @Version 1.0
 */
@Api(tags = "科室信息")
@RestController
@RequestMapping("/admin/Department")
// 解决跨域问题
//@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据医院编号 查询医院所有科室信息
     */
    @ApiOperation(value = "根据医院编号 查询医院所有科室信息")
    @GetMapping("/findDepartmentByHosCode/{hoscode}")
    public Result findDepartmentByHosCode(@PathVariable String hoscode){
        List<DepartmentVo> list = departmentService.findDepartmentByHosCodeService(hoscode);
        return Result.ok(list);
    }
}
