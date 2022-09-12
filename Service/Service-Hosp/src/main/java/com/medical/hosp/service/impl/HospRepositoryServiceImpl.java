package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.hosp.repository.HospRepository;
import com.medical.hosp.service.HospRepositoryService;
import com.medical.hosp.service.HospService;
import com.medical.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 医院接口实现类
 * @CreateTime 2022-09-11 11:00
 * @Version 1.0
 */
@Service
public class HospRepositoryServiceImpl implements HospRepositoryService {

    @Autowired
    public HospRepository hospRepository;


    @Override
    public void saveHospService(Map<String, Object> switchMap) {
        // 把集合转换为字符串
        String s = JSONObject.toJSONString(switchMap);
        // 将字符串转换为对象
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);

        // 判断是否存在数据
        Hospital hospitalMongo = hospRepository.getHospitalByHoscode(hospital.getHoscode());

        Date date = new Date();
        if (hospitalMongo != null){
            // 存在 修改
            hospital.setStatus(hospitalMongo.getStatus());
            hospital.setCreateTime(hospitalMongo.getCreateTime());
        }else {
            // 不存在 添加
            hospital.setStatus(0);
            hospital.setCreateTime(date);
        }
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospRepository.save(hospital);
    }

    @Override
    public Hospital getHospitalShowService(String hoscode) {
        return hospRepository.getHospitalByHoscode(hoscode);
    }
}
