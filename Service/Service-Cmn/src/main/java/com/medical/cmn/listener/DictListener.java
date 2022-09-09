package com.medical.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.medical.cmn.mapper.DictMapper;
import com.medical.model.Dict;
import com.medical.vo.DictIEVo;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 监听器
 * @CreateTime 2022-09-08 15:48
 * @Version 1.0
 */
public class DictListener extends AnalysisEventListener<DictIEVo> {

    // 使用构造方法注入 DictMapper
    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    /**
     * 一行一行的读取excel中的数据 从第二行开始
     * @param dictIEVo
     * @param analysisContext
     */
    @Override
    public void invoke(DictIEVo dictIEVo, AnalysisContext analysisContext) {
        // 调用方法添加数据到数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictIEVo,dict);
        dictMapper.insert(dict);
    }

    /**
     * 读取excel表头数据
     * @param headMap headMap就是表头数据
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    /**
     * 读取之后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
