package com.medical.hosp.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.medical.hosp.service.HospitalService;
import com.medical.model.Hospital;
import com.medical.result.Result;
import com.medical.vo.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luo.X
 * @Description 医院信息controller
 * @CreateTime 2022-09-12 16:02
 * @Version 1.0
 */
@Api(tags = "医院管理")
@RestController
@RequestMapping("/admin/Hospital")
// 解决跨域问题
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * 查询医院列表(条件查询分页)
     * @RequestBody(required = false) 代表参数hospitalSetVo可以不传递
     */
    @GetMapping("/getHospital/{currentPage}/{pageSize}")
    public Result getHospital(@PathVariable int currentPage, @PathVariable int pageSize,
                              @RequestBody(required = false) HospitalQueryVo hospitalQueryVo){
        Page<Hospital> page = hospitalService.getHospital(currentPage, pageSize, hospitalQueryVo);
        return Result.ok(page);
    }

    /**
     * 更新医院状态
     *  0 上线 1 下线
     */
    @ApiOperation(value = "更新医院状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Integer status,@PathVariable String id){
        hospitalService.updateStatusService(status, id);
        return Result.ok();
    }

    /**
     * 医院详情（根据id查询详细信息）
     */
    @ApiOperation(value = "医院详情")
    @GetMapping("/findHospitalById/{id}")
    public Result findHospitalById(@PathVariable String id){
        Hospital hospital = hospitalService.findHospitalByIdService(id);
        return Result.ok(hospital);
    }
}
