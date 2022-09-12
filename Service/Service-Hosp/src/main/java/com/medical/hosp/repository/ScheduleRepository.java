package com.medical.hosp.repository;

import com.medical.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Luo.X
 * @Description 操作mongodb
 * @CreateTime 2022-09-12 13:53
 * @Version 1.0
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

}
