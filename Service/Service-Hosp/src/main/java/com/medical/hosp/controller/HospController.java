package com.medical.hosp.controller;

import com.medical.model.HospitalSet;
import com.medical.hosp.service.HospService;
import com.medical.vo.HospitalSetVo;
import com.medical.result.Result;
import com.medical.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author Luo.X
 * @Description 医院模块 controller
 * @CreateTime 2022-09-04 16:42
 * @Version 1.0
 */
// 设置swagger中文提示
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/Hosp")
// 解决跨域问题
@CrossOrigin
public class HospController {

    // 注入service
    @Autowired
    private HospService hospService;

    /**
     * 查询医院设置表的所有信息
     */
    @ApiOperation(value = "查询医院所有信息")
    @GetMapping("/findHospList")
    public Result findHospList(){
        List<HospitalSet> list = hospService.list();
        return Result.ok(list);
    }

    /**
     * 逻辑删除医院设置
     */
    @ApiOperation(value = "逻辑删除医院信息")
    @DeleteMapping("/removeHospByID/{id}")
    public Result removeHospByID(@PathVariable Long id){
        boolean b = hospService.removeById(id);
        if (b){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 条件查询带分页
     *  @RequestBody(required = false) 代表参数hospitalSetVo可以不传递
     */
    @ApiOperation(value = "条件查询并分页")
    @PostMapping("/findHospByIDPage/{currentPage}/{pageSize}")
    public Result findHospByIDPage(@PathVariable int currentPage,@PathVariable int pageSize ,
                                   @RequestBody(required = false) HospitalSetVo hospitalSetVo){
        return Result.ok(hospService.findHospByIDPage(currentPage,pageSize,hospitalSetVo));
    }

    /**
     * 添加医院设置
     */
    @ApiOperation(value = "添加医院设置")
    @PostMapping("/saveHosp")
    public Result saveHosp(@RequestBody HospitalSet hospitalSet){
        // 生成签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean b = hospService.save(hospitalSet);
        if (b){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id获取医院设置
     */
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("/findHospById/{id}")
    public Result findHospById(@PathVariable Long id){
        HospitalSet hospitalSet = hospService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     */
    @ApiOperation(value = "修改医院设置")
    @PostMapping("/updateHosp")
    public Result updateHosp(@RequestBody HospitalSet hospitalSet){
        boolean b = hospService.updateById(hospitalSet);
        if (b){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 批量删除医院设置
     */
    @ApiOperation(value = "根据id批量删除医院设置")
    @PostMapping("/delHospByIds")
    public Result delHospByIds(@RequestBody List<Long> ids){
        System.out.println(ids);
        boolean b = hospService.removeByIds(ids);
        if (b){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 医院信息锁定和解锁
     */
    @ApiOperation(value = "医院信息锁定和解锁")
    @PutMapping("/lockHosp/{id}/{status}")
    public Result lockHosp(@PathVariable Long id,@PathVariable int status){
        // 根据id查询医院设置信息
        HospitalSet hospitalSet = hospService.getById(id);
        // 设置状态
        hospitalSet.setStatus(status);
        boolean b = hospService.updateById(hospitalSet);
        if (b){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 发送签名密钥
     */
    @ApiOperation("发送签名密钥")
    @PutMapping("/lockHospSignKey")
    public Result lockHospSignKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        return Result.ok();
    }
}
