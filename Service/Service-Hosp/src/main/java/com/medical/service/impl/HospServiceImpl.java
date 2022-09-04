package com.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.mapper.HospMapper;
import com.medical.model.HospitalSet;
import com.medical.service.HospService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luo.X
 * @Description 医院模块接口实现类
 * @CreateTime 2022-09-04 16:42
 * @Version 1.0
 */
@Service
public class HospServiceImpl extends ServiceImpl<HospMapper, HospitalSet> implements HospService {

    // 注入mapper
    @Autowired
    private HospMapper hospMapper;


}
