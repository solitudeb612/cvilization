package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QSysRole implements Serializable {

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String roleName;

    /**
     * 角色标识
     */
    @Size(max = 64, message = " 角色标识最大字符长度为64")
    @ApiModelProperty(value = "角色标识")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String roleCode;

    /**
     * 角色描述
     */
    @Size(max = 255, message = " 角色描述最大字符长度为255")
    @ApiModelProperty(value = "角色描述")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String roleDesc;

//    /**
//     * 数据权限类型
//     */
//    @Size(max = 1, message = "数据权限类型最大字符长度为1")
//    @ApiModelProperty(value = "数据权限类型")
//    @DynamicQuery(value = DynamicQuery.QType.eq)
//    private String dsType;
//
//    /**
//     * 数据权限范围
//     */
//    @Size(max = 255, message = "数据权限范围最大字符长度为255")
//    @ApiModelProperty(value = "数据权限范围")
//    @DynamicQuery(value = DynamicQuery.QType.like)
//    private String dsScope;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间 ")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime createTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer creatorId;

    /**
     * 修改人id
     */
    @ApiModelProperty(value = "修改人id")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer updaterId;

//    /**
//     * 结束时间
//     */
//    @ApiModelProperty(value = "结束时间 ")
//    @DynamicQuery(value = DynamicQuery.QType.like)
//    private LocalDateTime updateTimeEnd;
    /**
     * 删除标识（0-正常,1-删除）
     */
    @Size(max = 1, message = "删除标识（0-正常,1-删除） 最大为1")
    @ApiModelProperty(value = "删除标识（0-正常,1-删除）")
    private String delFlag;

//    /**
//     * 租户id
//     */
//    @ApiModelProperty(value = "租户id", hidden = true)
//    private Integer tenantId;

}
