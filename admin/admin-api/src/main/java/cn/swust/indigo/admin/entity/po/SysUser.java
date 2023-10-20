package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 */
@Data
@ApiModel(value = "用户")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @Size(min = 1, max = 255, message = "用户名字符长度1-255")
    @NotNull
    private String username;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "昵称")
    @Size(min = 0, max = 255, message = "昵称字符长度1-255")
    private String nickname;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
//    @Size(min = 1, max = 255, message = "密码盐字符长度1-255")
//    @NotNull
    private String password;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "用户所属部门id")
    private Integer departmentId;


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

    /**
     * 0-正常，1-删除
     */
//    @TableLogic
//    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
//    @Size(min = 0, max = 1, message = "删除标记字符长度1")
//    private String delFlag;

    /**
     * 锁定标记
     */
    @ApiModelProperty(value = "锁定标记0-正常，1-锁定")
    private String status;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "电话号")
    @Size(min = 0, max = 30, message = "电话号字符长度0-30")
    private String phone;

    @ApiModelProperty(value = "手机号")
    @Size(min = 0, max = 11, message = "手机号字符长度0-11")
    private String telephone;


    /**
     * 微信openid
     */
    @ApiModelProperty(value = "微信openid")
    @Size(min = 0, max = 255, message = "微信openid字符长度0-255")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String wxOpenId;


    /**
     * 姓别
     */
    @ApiModelProperty(value = "性别：2表示男，1表示女")
    @TableField()
    private Integer sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField()
    private String email;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @TableField(exist = false)
    private  String departmentName;
}
