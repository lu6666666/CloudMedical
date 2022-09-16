package com.medical.hosp.service;

import com.medical.model.Dict;
import com.medical.model.Hospital;
import com.medical.vo.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 医院 service接口
 * @CreateTime 2022-09-11 10:59
 * @Version 1.0
 */
public interface HospitalService {

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


    /**
     * 查询医院列表(条件查询分页)
     * @param currentPage
     * @param pageSize
     * @param hospitalQueryVo
     */
    Page<Hospital> getHospital(int currentPage, int pageSize, HospitalQueryVo hospitalQueryVo);


    /**
     * 更新医院状态
     *   0 上线 1 下线
     * @param status
     */
    void updateStatusService(Integer status, String id);

    /**
     * 医院详情（根据id查询详细信息）
     */
    Hospital findHospitalByIdService(String id);
}
