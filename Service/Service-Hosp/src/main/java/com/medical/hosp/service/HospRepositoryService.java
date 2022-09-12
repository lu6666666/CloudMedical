package com.medical.hosp.service;

import com.medical.model.Hospital;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 医院 service接口
 * @CreateTime 2022-09-11 10:59
 * @Version 1.0
 */
public interface HospRepositoryService {

    /**
     * 上传医院
     * @param switchMap
     */
    void saveHospService(Map<String, Object> switchMap);

    /**
     * 根据医院编号 查询医院
     * @param hoscode
     */
    Hospital getHospitalShowService(String hoscode);
}
