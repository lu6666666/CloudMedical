package com.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 医院设置表(HospitalSet)实体类
 *
 * @author LuoX
 * @since 2022-09-04 16:39:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HospitalSet implements Serializable {
    private static final long serialVersionUID = -18032608498158198L;
    /**
     * 编号
     */
    private Long id;
    /**
     * 医院名称
     */
    private String hosname;
    /**
     * 医院编号
     */
    private String hoscode;
    /**
     * api基础路径
     */
    private String apiUrl;
    /**
     * 签名秘钥
     */
    private String signKey;
    /**
     * 联系人
     */
    private String contactsName;
    /**
     * 联系人手机
     */
    private String contactsPhone;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 逻辑删除(1:已删除，0:未删除)
     */
    private Integer isDeleted;
}

