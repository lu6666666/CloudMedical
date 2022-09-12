package com.medical.hosp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.model.HospitalSet;
import com.medical.vo.HospitalSetVo;

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
    Page findHospByIDPage(int currentPage, int pageSize , HospitalSetVo hospitalSetVo);

    /**
     * 根据医院编码 查询签名
     */
    String getKey(String hoscode);
}
