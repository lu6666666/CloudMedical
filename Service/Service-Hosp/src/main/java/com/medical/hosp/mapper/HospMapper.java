package com.medical.hosp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.model.HospitalSet;
import org.springframework.stereotype.Component;

/**
 * @author Luo.X
 * @Description 医院模块 mapper
 * @CreateTime 2022-09-04 16:35
 * @Version 1.0
 */
@Component
public interface HospMapper extends BaseMapper<HospitalSet> {
}
