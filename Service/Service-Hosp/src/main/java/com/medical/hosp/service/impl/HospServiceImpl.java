package com.medical.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.hosp.mapper.HospMapper;
import com.medical.model.HospitalSet;
import com.medical.hosp.service.HospService;
import com.medical.vo.HospitalSetVo;
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


    @Override
    public Page findHospByIDPage(int currentPage, int pageSize, HospitalSetVo hospitalSetVo) {
        // 创建page对象 当前页 每页条数为参数
        Page<HospitalSet> page = new Page<>(currentPage, pageSize);

        // 查询条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        // 模糊查询
        if (hospitalSetVo.getHosname() != null && !hospitalSetVo.getHosname().isEmpty()) {
            wrapper.like("hosname", hospitalSetVo.getHosname());
        }
        if (hospitalSetVo.getHoscode() != null && !hospitalSetVo.getHoscode().isEmpty()) {
            wrapper.eq("hoscode", hospitalSetVo.getHoscode());
        }
        Page<HospitalSet> selectPage = hospMapper.selectPage(page, wrapper);
        return selectPage;
    }
}
