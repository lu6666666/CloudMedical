package com.medical.client.cmnFeginClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Luo.X
 * @Description
 * @CreateTime 2022-09-12 17:25
 * @Version 1.0
 */
// 实现远程调用Service-Cmn接口
@FeignClient("Service-Cmn")
@Component
public interface DictFeignClient {

    /**
     * 根据dict_code和value查询name
     *  这里@PathVariable必须指定参数
     */
    @GetMapping("/admin/Dict/findByDictName/{dictCode}/{value}")
    String findByDictName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    /**
     * 根据value查询name
     */
    @GetMapping("/admin/Dict/findByDictName/{value}")
    String findByDictName(@PathVariable("value") String value);
}
