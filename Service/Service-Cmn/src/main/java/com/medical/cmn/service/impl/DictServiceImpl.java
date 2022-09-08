package com.medical.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.cmn.mapper.DictMapper;
import com.medical.cmn.service.DictService;
import com.medical.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo.X
 * @Description 组织架构 service实现类
 * @CreateTime 2022-09-08 9:52
 * @Version 1.0
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 不需要注入dictMapper 因为继承 ServiceImpl 时已经帮我们注入了
     */
    /*@Autowired
    private DictMapper dictMapper;*/

    @Override
    public List<Dict> findDictByIdService(Long parentId) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        // 向list集合每个dict对象中设置hasChildren参数
        for (Dict dict : dictList) {
            Long id = dict.getId();
            boolean b = this.isChildren(id);
            dict.setHasChildren(b);
        }
        return dictList;
    }

    /**
     * 判断id下面是由有子节点
     */
    private boolean isChildren(Long parentId){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
