package cn.swust.indigo.admin.entity.dto;

import cn.swust.indigo.admin.entity.po.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统用户传输对象
 */
@Data
@ApiModel(value = "系统用户传输对象")

public class UserDto extends SysUser {
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id集合")
    private List<Integer> role;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

}
