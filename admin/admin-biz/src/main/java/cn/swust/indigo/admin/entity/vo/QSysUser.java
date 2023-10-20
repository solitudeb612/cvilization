package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QSysUser implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer userId;

    /**
     * 用户名
     */
    @Size(max = 64, message = "用户名最大字符长度为64")
    @ApiModelProperty(value = "用户名")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String username;

    /**
     * 昵称
     */
    @Size(max = 255, message = "昵称最大字符长度为255")
    @ApiModelProperty(value = "昵称")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String nickname;


    /**
     * 简介
     */
    @Size(max = 20, message = "简介最大字符长度为20")
    @ApiModelProperty(value = "简介")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String phone;


    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer deptId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime createTime;

    /**
     * 创建时间结束
     */
    @ApiModelProperty(value = "创建时间结束 ")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime createTimeEnd;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime updateTime;

    /**
     * 修改时间结束
     */
    @ApiModelProperty(value = "修改时间结束 ")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime updateTimeEnd;
    /**
     * 0-正常，9-锁定
     */
    @Size(max = 1, message = "0-正常，9-锁定 最大为1")
    @ApiModelProperty(value = "0-正常，9-锁定")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    @Size(max = 1, message = "0-正常，1-删除 最大为1")
    @ApiModelProperty(value = "0-正常，1-删除")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String delFlag;

    /**
     * 微信openid
     */
    @Size(max = 32, message = "微信openid最大字符长度为32")
    @ApiModelProperty(value = "微信openid")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String wxOpenid;

    /**
     * QQ openid
     */
    @Size(max = 32, message = "QQ openid最大字符长度为32")
    @ApiModelProperty(value = "QQ openid")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String qqOpenid;

    /**
     *
     */
    @Size(max = 100, message = " 最大为100")
    @ApiModelProperty(value = "")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String giteeLogin;

    /**
     * 开源中国唯一标识
     */
    @Size(max = 100, message = " 开源中国唯一标识最大字符长度为100")
    @ApiModelProperty(value = "开源中国唯一标识")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String oscId;

    /**
     * 所属租户
     */
    @ApiModelProperty(value = "所属租户", hidden = true)
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer tenantId;

    /**
     * 邮箱
     */
    @Size(max = 50, message = "邮箱最大字符长度为50")
    @ApiModelProperty(value = "邮箱")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String email;

    /**
     * 性别：2表示男，1表示女
     */
    @ApiModelProperty(value = "性别：2表示男，1表示女")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer sex;

}
