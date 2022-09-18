package com.medical.hosp.controller.api;

import com.medical.client.cmnFeginClient.DictFeignClient;
import com.medical.hosp.service.DepartmentService;
import com.medical.hosp.service.HospitalService;
import com.medical.model.Dict;
import com.medical.model.Hospital;
import com.medical.result.Result;
import com.medical.vo.DepartmentVo;
import com.medical.vo.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Luo.X
 * @Description 用户展示页面接口
 * @CreateTime 2022-09-17 10:15
 * @Version 1.0
 */
@Api(tags = "用户展示页面接口")
@RestController
@RequestMapping("/site/web")
public class SiteApplication {

    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private DictFeignClient dictFeignClient;

    /**
     * 查询医院列表(条件查询分页) 前端用户页面展示
     * @RequestBody(required = false) 代表参数hospitalSetVo可以不传递
     */
    @ApiOperation(value = "查询医院列表(条件查询分页) 前端用户页面展示")
    @PostMapping("/getHospital/{currentPage}/{pageSize}")
    public Result findHospital(@PathVariable int currentPage, @PathVariable int pageSize,
                               @RequestBody(required = false) HospitalQueryVo hospitalQueryVo){
        Page<Hospital> page = hospitalService.getHospital(currentPage, pageSize, hospitalQueryVo);
        return Result.ok(page);
    }

    /**
     * 根据上级id查询子数据列表
     */
    @ApiOperation(value = "根据上级id查询子数据列表")
    @GetMapping("/findDictById/{parentId}")
    public Result findDictById(@PathVariable("parentId") Long parentId){
        return dictFeignClient.findDictById(parentId);
    }

    /**
     * 根据dictCode查询所有的省
     */
    @ApiOperation(value = "根据dictCode查询所有的省")
    @GetMapping("/findByProvince/{dictCode}")
    public Result findByProvince(@PathVariable String dictCode){
        return dictFeignClient.findByProvince(dictCode);
    }

    /**
     * 根据前台传递的医院名称 模糊查询所有医院信息
     */
    @ApiOperation(value = "根据前台传递的医院名称 模糊查询所有医院信息")
    @GetMapping("/findHospitalByHosName/{hosName}")
    public Result findHospitalByHosName(@PathVariable String hosName){
         List<Hospital> list = hospitalService.findHospitalByHosNameService(hosName);
         return Result.ok(list);
    }

    /**
     * 根据医院编码查询医院信息（查询医院挂号信息）
     */
    @ApiOperation(value = "根据医院编码查询医院信息（查询医院挂号信息）")
    @GetMapping("/findHospitalByHosCode/{hoscode}")
    public Result findHospitalByHosCode(@PathVariable String hoscode){
        Hospital hospital = hospitalService.findHospitalByHosCodeService(hoscode);
        return Result.ok(hospital);
    }

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
