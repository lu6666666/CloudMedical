package com.medical.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Luo.X
 * @Description 医院信息分页查询条件
 * @CreateTime 2022-09-06 15:04
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HospitalSetVo implements Serializable {
    /**
     * 医院名称
     */
    private String hosname;
    /**
     * 医院编号
     */
    private String hoscode;
}
