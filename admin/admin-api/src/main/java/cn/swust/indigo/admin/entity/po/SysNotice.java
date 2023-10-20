package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 系统通知
 *
 * @author lhz
 * @date 2023-03-01 13:38:37
 */
@Data
@TableName("sys_notice")

@ApiModel(value = "系统通知")
public class SysNotice {
    private static final long serialVersionUID = 1L;


    /**
     * 公告ID
     */
    @TableId(value = "notice_id",type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "公告ID")
    private Integer noticeId;


    /**
     * 标题
     */
    @NotNull
    @Size(min = 1, max = 128, message = "标题 最大为128")
    @ApiModelProperty(value = "标题 长度: 1-128")
    private String title;


    /**
     * 内容
     */
    @NotNull
    @Size(min = 1, max = 65535, message = "内容 最大为65535")
    @ApiModelProperty(value = "内容 长度: 1-65535")
    private String content;


    /**
     * 公告类型（1通知 2个人）
     */

    @NotNull
    @Size(min = 1, max = 1, message = "公告类型（0公告, 1 个人信息） 最大为1")
    @ApiModelProperty(value = "公告类型（0公告, 1 个人信息） 长度: 1-1")
    private Integer noticeType = 0;



    @ApiModelProperty(value = "个人接收消息的id")
    private Integer userId =null;


    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    /**
     * 公告状态（0正常 1关闭）
     */
    @Size(min = 1, max = 1, message = "公告状态（0正常 1关闭） 最大为1")
    @ApiModelProperty(value = "公告状态（0正常 1关闭） 长度: 1-1")
    private String status;


    /**
     * 更新时间
     */
    @NotNull
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 创建者
     */
    @Size(min = 1, max = 64, message = "创建者 最大为64")
    @ApiModelProperty(value = "创建者 长度: 1-64")
    private String creatorId;


    /**
     * 备注
     */
    @Size(min = 1, max = 255, message = "备注 最大为255")
    @ApiModelProperty(value = "备注 长度: 1-255")
    private String memo;


    /**
     * 更新者
     */
    @Size(min = 1, max = 64, message = "更新者 最大为64")
    @ApiModelProperty(value = "更新者 长度: 1-64")
    private String updaterId;

    /**
     * 更新者
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "是否阅读")
    private Boolean isRead;




}
