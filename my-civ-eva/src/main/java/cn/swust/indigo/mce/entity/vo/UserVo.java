package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.core.util.PinYinUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class UserVo {
    /**
     * 第一次输入密码
     */
    @ApiModelProperty(value = "输入密码")
    @NotNull
    private String password;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    @NotNull
    private Date birthday;
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotNull
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @NotNull
    private String nickname;


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
    /**
     * 0-正常，1-删除
     */
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    @TableField(exist = false)
    private String delFlag;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @NotNull
    @Size(min = 11,max = 11,message = "11位电话号码")
    private String phone;

    /**
     * 状态 不可用 0;可用 1;锁定2;申请中 3;申请不成功 4
     */
    @ApiModelProperty(value = "状态 不可用 0;可用 1;锁定2;申请中 3;申请不成功 4")
    private Integer status;


    /**
     * 所属部门id
     */
    @ApiModelProperty(value = "所属部门id")
    @NotNull
    private Integer departmentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "所属部门名称")
    @NotNull
    @TableField(exist = false)
    private String deptName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @NotNull
    private String email;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别0是女1是男")
    @NotNull
    @Size(min = 1, max = 1, message = "指南名字 最大为100")
    private String sex;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音")
    private String userPy;


    @ApiModelProperty(value = "自己的角色ID")
    @TableField(exist = false)
    private int roleId;

    public void setUserPy() {
        if(userPy!=null){
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String[] headByString = PinYinUtils.getHeadByString(username);
        for(int i = 0 ; i < headByString.length; i++){
            stringBuffer = stringBuffer.append(headByString[i]);
        }
        String namePy = stringBuffer.toString();
        this.userPy = namePy;
    }
}
