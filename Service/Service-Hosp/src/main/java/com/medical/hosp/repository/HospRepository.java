package com.medical.hosp.repository;

import com.medical.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Luo.X
 * @Description 操作mongodb
 * @CreateTime 2022-09-11 10:57
 * @Version 1.0
 */
@Repository
public interface HospRepository extends MongoRepository<Hospital,String> {

    /**
     * 判断mongodb是否存在该数据
     *  jpa命名规范By后面必须是实体类中存在的字段 否则就会报错
     *  总得来说就是jpa的规范限制的，需要做的就是方法名By的参数根据实体类中的来就不会报此错
     * @param
     */
    Hospital getHospitalByHoscode(String hoscode);
}
