package com.medical.hosp.repository;

import com.medical.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Luo.X
 * @Description 操作mongodb
 * @CreateTime 2022-09-11 16:24
 * @Version 1.0
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    /**
     * 根据条件判断mongodb中是否存在该数据
     * @param hoscode
     * @param depcode
     * @return
     */
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);

}
