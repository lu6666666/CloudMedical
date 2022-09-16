package com.medical.cmn.controller;

import com.medical.cmn.service.DictService;
import com.medical.model.Dict;
import com.medical.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;
import java.util.List;

/**
 * @author Luo.X
 * @Description 组织架构 controller
 * @CreateTime 2022-09-08 9:54
 * @Version 1.0
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/Dict")
// 解决跨域问题
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 导出数据字典
     */
    @ApiOperation(value = "导出数据字典")
    @GetMapping("/exportDict")
    public void exportDict(HttpServletResponse response){
        dictService.exportDictService(response);
    }

    /**
     * 导入数据字典
     */
    @ApiOperation(value = "导入数据字典")
    @PostMapping("/importDict")
    public Result importDict(MultipartFile file){
        dictService.importDictService(file);
        return Result.ok();
    }

    /**
     * 根据上级id查询子数据列表
     *  使用redis缓存
     */
    @ApiOperation(value = "根据上级id查询子数据列表")
    @GetMapping("/findDictById/{parentId}")
    public Result findDictById(@PathVariable Long parentId){
        List<Dict> dictList = dictService.findDictByIdService(parentId);
        return Result.ok(dictList);
    }

    /**
     * 根据dict_code和value查询name
     */
    @ApiOperation(value = "根据dict_code和value查询")
    @GetMapping("/findByDictName/{dictCode}/{value}")
    public String findByDictName(@PathVariable String dictCode,@PathVariable String value){
        return dictService.findByDictCodeAndValueService(dictCode,value);
    }

    /**
     * 根据value查询name
     */
    @ApiOperation(value = "根据dict_code和value查询")
    @GetMapping("/findByDictName/{value}")
    public String findByName(@PathVariable String value){
        return dictService.findByDictCodeAndValueService("",value);
    }

    /**
     * 根据dictCode查询所有的省
     */
    @ApiOperation(value = "根据dictCode查询所有的省")
    @GetMapping("/findByProvince/{dictCode}")
    public Result findByProvince(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findByProvinceService(dictCode);
        return Result.ok(dictList);
    }


}
