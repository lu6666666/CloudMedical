package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.hosp.repository.DepartmentRepository;
import com.medical.hosp.service.DepartmentService;
import com.medical.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 科室接口实现类
 * @CreateTime 2022-09-11 16:31
 * @Version 1.0
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartmentService(Map<String, Object> switchMap) {
        // 将集合转换为字符串
        String s = JSONObject.toJSONString(switchMap);
        // 将字符串转换为对应的对象
        Department department = JSONObject.parseObject(s, Department.class);

        // 根据条件查询 数据是否已经存在
        Department departmentMongo = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        // 不存在 添加
        Date date = new Date();
        // 判断
        if (null != departmentMongo) {
            // 存在 修改
            departmentMongo.setUpdateTime(date);
            departmentMongo.setIsDeleted(0);
            departmentRepository.save(departmentMongo);
        }else {
            department.setCreateTime(date);
            department.setUpdateTime(date);
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }
}
