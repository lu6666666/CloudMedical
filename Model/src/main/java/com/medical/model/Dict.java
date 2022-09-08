package com.medical.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 组织架构表(Dict)实体类
 *
 * @author LuoX
 * @since 2022-09-08 09:40:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dict implements Serializable {
    private static final long serialVersionUID = 390906464005380144L;
    /**
     * id
     */
    // 解决前台由于id过长传递过来的数据不一致问题
    //@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private Long value;
    /**
     * 编码
     */
    private String dictCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标记（0:不可用 1:可用）
     */
    private Integer isDeleted;

    @ApiModelProperty(value = "是否包含子节点")
    // 标识该字段在数据库中不存在（前台需要使用该字段）
    @TableField(exist = false)
    private boolean hasChildren;

}

