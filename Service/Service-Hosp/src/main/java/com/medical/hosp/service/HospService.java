package com.medical.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.model.HospitalSet;
import com.medical.model.HospitalSetVo;
import com.medical.result.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Luo.X
 * @Description 医院模块接口
 * @CreateTime 2022-09-04 16:42
 * @Version 1.0
 */
public interface HospService extends IService<HospitalSet> {

    /**
     * 条件查询带分页
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param hospitalSetVo 查询条件
     * @return
     */
    List<HospitalSet> findHospByIDPage(int currentPage, int pageSize , HospitalSetVo hospitalSetVo);
}
