package com.medical.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Luo.X
 * @Description 数据字典导入导出实体类
 * @CreateTime 2022-09-08 15:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DictIEVo implements Serializable {
    /**
     * id
     */
    // value 代表在excel中的标题数据 index 代表的excel中的列
    @ExcelProperty(value = "id",index = 0)
    private Long id;
    /**
     * 上级id
     */
    @ExcelProperty(value = "上级id",index = 1)
    private Long parentId;
    /**
     * 名称
     */
    @ExcelProperty(value = "名称",index = 2)
    private String name;
    /**
     * 值
     */
    @ExcelProperty(value = "值",index = 3)
    private Long value;
    /**
     * 编码
     */
    @ExcelProperty(value = "编码",index = 4)
    private String dictCode;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间",index = 5)
    private Date createTime;
}
