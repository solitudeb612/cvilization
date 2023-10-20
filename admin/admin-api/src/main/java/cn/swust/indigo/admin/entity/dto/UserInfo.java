package cn.swust.indigo.admin.entity.dto;

import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
@ApiModel(value = "用户信息")
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    @ApiModelProperty(value = "用户基本信息")
    private SysUser sysUser;

    /**
     * 权限标识集合
     */
    @ApiModelProperty(value = "权限标识集合")
    private String[] permissions;

    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色标识集合")
    private Integer[] roles;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名字")
    private String[] roleNames;
}
