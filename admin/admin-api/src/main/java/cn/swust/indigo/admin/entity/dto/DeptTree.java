/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package cn.swust.indigo.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * @date 2018/1/20
 * 部门树
 */
@Data
@ApiModel(value = "部门树")

public class DeptTree extends TreeNode {

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    public Integer getDeptId() {
        return id;
    }

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * 部门类型
     */
    @ApiModelProperty(value = "部门类型")
    private Integer type;

    /**
     * 部门联系单位
     */
    @ApiModelProperty(value = "部门联系单位")
    private String deptContact;

    /**
     * 部门电话
     */
    @ApiModelProperty(value = "部门电话")
    private String phone;

    @ApiModelProperty(value = "负责人ID")
    private int userId;

    @ApiModelProperty(value = "部门py")
    private String departmentPy;

}
