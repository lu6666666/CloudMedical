package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.client.cmnFeginClient.DictFeignClient;
import com.medical.hosp.repository.HospRepository;
import com.medical.hosp.service.HospitalService;
import com.medical.model.Dict;
import com.medical.model.Hospital;
import com.medical.vo.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Luo.X
 * @Description 医院接口实现类
 * @CreateTime 2022-09-11 11:00
 * @Version 1.0
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    public HospRepository hospRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

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

    @Override
    public Page<Hospital> getHospital(int currentPage, int pageSize, HospitalQueryVo hospitalQueryVo) {
        // 分页
        Pageable pageable = PageRequest.of(currentPage - 1,pageSize);

        // 创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()  // 构造对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 改变默认字符串匹配方式为： 模糊查询
                .withIgnoreCase(true); /* 忽略大小写 */
        // HospitalQueryVo 转换为 Hospital
        Hospital hospital = new Hospital();
        if (!StringUtils.isEmpty(hospitalQueryVo)){
            BeanUtils.copyProperties(hospitalQueryVo,hospital);
        }
        // 创建对象
        Example<Hospital> example = Example.of(hospital,matcher);
        // 调用方法实现查询
        Page<Hospital> page = hospRepository.findAll(example, pageable);

        // 查询list集合 遍历医院等级并封装
        page.getContent().stream().forEach(item->{
            this.setHospitalList(item);
        });
        return page;
    }

    @Override
    public void updateStatusService(Integer status, String id) {
        // 创建对象 并给id赋值
        Hospital hospital = new Hospital();
        hospital.setId(id);
        // 根据id查询该条数据
        Example<Hospital> example = Example.of(hospital);
        Optional<Hospital> optional = hospRepository.findOne(example);
        // 获取查询到的数据并赋值给hospital对象
        hospital = optional.get();

        // 修改该条数据的状态
        hospital.setStatus(status);
        // 保存 如果该条数据存在就是修改
        hospRepository.save(hospital);
    }

    @Override
    public Hospital findHospitalByIdService(String id) {
        // 创建对象 并给id赋值
        Hospital hospital = new Hospital();
        hospital.setId(id);
        // 根据id查询该条数据
        Example<Hospital> example = Example.of(hospital);
        Optional<Hospital> optional = hospRepository.findOne(example);
        // 获取查询到的数据并赋值给hospital对象
        hospital = optional.get();

        hospital = this.setHospitalList(hospital);
        return hospital;
    }

    @Override
    public List<Hospital> findHospitalByHosNameService(String hosName) {

        // 创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()  // 构造对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 改变默认字符串匹配方式为： 模糊查询
                .withIgnoreCase(true); /* 忽略大小写 */
        // HospitalQueryVo 转换为 Hospital
        Hospital hospital = new Hospital();
        hospital.setHosname(hosName);
        // 创建对象
        Example<Hospital> example = Example.of(hospital,matcher);
        // 调用方法实现查询
        List<Hospital> list = hospRepository.findAll(example);
        return list;
    }

    /**
     * 根据医院编码查询医院信息（查询医院挂号信息）
     * @param hoscode
     * @return
     */
    @Override
    public Hospital findHospitalByHosCodeService(String hoscode) {
        Hospital hospital = new Hospital();
        hospital.setHoscode(hoscode);
        Example<Hospital> example = Example.of(hospital);
        Optional<Hospital> one = hospRepository.findOne(example);
        hospital = one.get();

        // 封装医院等级
        hospital = this.setHospitalList(hospital);
        return hospital;
    }


    /**
     * 查询list集合 遍历医院等级并封装
     */
    private Hospital setHospitalList(Hospital hospital){
        // 获取医院等级名称
        String name = dictFeignClient.findByDictName("Hostype", hospital.getHostype());
        // 查询省市区
        // 省
        String provinceCode = dictFeignClient.findByDictName(hospital.getProvinceCode());
        // 市
        String cityCode = dictFeignClient.findByDictName(hospital.getCityCode());
        // 区
        String districtCode = dictFeignClient.findByDictName(hospital.getDistrictCode());

        hospital.getParam().put("address",provinceCode + cityCode + districtCode);
        hospital.getParam().put("name",name);
        return hospital;
    }
}
