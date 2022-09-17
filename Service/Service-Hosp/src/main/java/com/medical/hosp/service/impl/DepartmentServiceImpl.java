package com.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.medical.hosp.repository.DepartmentRepository;
import com.medical.hosp.service.DepartmentService;
import com.medical.model.Department;
import com.medical.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        } else {
            department.setCreateTime(date);
            department.setUpdateTime(date);
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findDepartmentService(Map<String, Object> switchMap) {
        // 根据创建时间排序 降序
        Sort sort = Sort.by(Sort.Direction.DESC, "CreateTime");
        // 获取当前页 因为mongodb分页使用0开始的所以要减一
        int currentPage = StringUtils.isEmpty(switchMap.get("page")) ? 0 : Integer.parseInt((String) switchMap.get("page")) - 1;
        // 获取每页条数
        int pageSize = StringUtils.isEmpty(switchMap.get("limit")) ? 10 : Integer.parseInt((String) switchMap.get("limit"));
        // 分页并排序
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        Department department = new Department();
        department.setHoscode((String) switchMap.get("hoscode"));
        department.setIsDeleted(0);
        // 创建匹配器 条件查询的方式
        ExampleMatcher matcher = ExampleMatcher.matching() // 构造对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 改变默认字符串匹配方式为： 模糊查询
                .withIgnoreCase(true);  // 忽略大小写
        // 创建实例
        Example<Department> example = Example.of(department,matcher);

        Page<Department> page = departmentRepository.findAll(example,pageable);
        return page;
    }

    @Override
    public void removeDepartmentService(Map<String, Object> switchMap) {
        Department department = new Department();
        department.setDepcode((String) switchMap.get("depcode"));
        department.setHoscode((String) switchMap.get("hoscode"));
        // 查询条件
        Example<Department> example = Example.of(department);
        Optional<Department> optional =  departmentRepository.findOne(example);
        // 获取科室信息
        department = optional.get();
        department.setIsDeleted(1);
        department.setUpdateTime(new Date());
        // 添加该科室 因为该科室已存在所以修改（实现逻辑删除）
        departmentRepository.save(department);
    }

    /**
     * 根据医院编号 查询医院所有科室信息
     * @param hoscode
     * @return
     */
    @Override
    public List<DepartmentVo> findDepartmentByHosCodeService(String hoscode) {
        List<DepartmentVo> departmentVoList = new ArrayList<>();
        // 根究医院编号查询所有科室信息
        Department department = new Department();
        department.setHoscode(hoscode);
        Example<Department> example = Example.of(department);
        List<Department> list = departmentRepository.findAll(example);
        // 分组 存入map集合 key是bigCode value是该分组的所有信息
        Map<String, List<Department>> map = list.stream().collect(Collectors.groupingBy(Department::getBigcode));
        // 遍历map集合分别存储大小科室信息 树形展示
        for (Map.Entry<String, List<Department>> entry : map.entrySet()) {
            // 封装大科室
            DepartmentVo bigDepartment = new DepartmentVo();
            List<Department> bigList = entry.getValue();
            // 获取大科室编号 和大科室名称
            bigDepartment.setDepcode(entry.getKey());
            bigDepartment.setDepname(bigList.get(0).getBigname());

            // 封装小科室（大科室附属科室）
            List<DepartmentVo> smallList = new ArrayList<>();
            for (Department d : bigList) {
                // 存储每条小科室信息
                DepartmentVo smallDepartment = new DepartmentVo();
                smallDepartment.setDepcode(d.getDepcode());
                smallDepartment.setDepname(d.getDepname());
                // 封装到list中
                smallList.add(smallDepartment);
            }
            // 把小科室放入大科室中
            bigDepartment.setChildren(smallList);
            // 将的单个科室信息放入到list集合中
            departmentVoList.add(bigDepartment);
        }
        return departmentVoList;
    }
}
