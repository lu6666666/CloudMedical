package com.medical.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.cmn.listener.DictListener;
import com.medical.cmn.mapper.DictMapper;
import com.medical.cmn.service.DictService;
import com.medical.model.Dict;
import com.medical.utils.RandomUtils;
import com.medical.vo.DictIEVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    /**
     * 使用Redis缓存
     *  @Cacheable 注解
     *      根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；
     *      如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上
     *      value	缓存名，必填，它指定了你的缓存存放在哪块命名空间
     *      cacheNames	与 value 差不多，二选一即可
     *      key	可选属性，可以使用 SpEL 标签自定义缓存的key
     * @param parentId
     * @return
     */
    @Cacheable(value = "findDictById",keyGenerator = "keyGenerator")
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

    @Override
    public void exportDictService(HttpServletResponse response) {
        try {
            // 设置头信息
            // 设置Content 类型为excel类型
            response.setContentType("application/vnd.ms-excel");
            // 设置编码格式
            response.setCharacterEncoding("utf-8");
            // 设置文件名称(使用当前时间作为文件名称) 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
            String fileName = URLEncoder.encode(RandomUtils.getDate(), "UTF-8");
            // 设置 以下载方式打开
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            // 查询所有数据
            List<Dict> dictList = baseMapper.selectList(null);
            // 返回的数据
            List<DictIEVo> dictIEVoList = new ArrayList<>();
            for (Dict dict : dictList) {
                DictIEVo dictIEVo = new DictIEVo();
                // 将dict中跟dictIEVo字段相同的数据拷贝到dictIEVo中
                BeanUtils.copyProperties(dict,dictIEVo);
                dictIEVoList.add(dictIEVo);
            }
            // sheet里的参数时excel里面的Sheet的名称
            EasyExcel.write(response.getOutputStream(),DictIEVo.class).sheet("数据字典").doWrite(dictIEVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void importDictService(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictIEVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
