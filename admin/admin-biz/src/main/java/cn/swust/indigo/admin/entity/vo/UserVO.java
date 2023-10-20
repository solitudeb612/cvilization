package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.admin.entity.po.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 前端用户展示对象
 *
 * @date 2017/10/29
 */
@Data
@ApiModel(value = "前端用户展示对象")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键")
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 微信openid
     */
    @ApiModelProperty(value = "微信open id")
    private String wxOpenId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
//    /**
//     * 0-正常，1-删除
//     */
//    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
//    private String delFlag;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;


    /**
     * 所属部门id
     */
    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "所属部门名称")
    private String deptName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;
    /**
     * 角色列表
     */
    @ApiModelProperty(value = "拥有的角色列表")
    private List<SysRole> roleList;
}
