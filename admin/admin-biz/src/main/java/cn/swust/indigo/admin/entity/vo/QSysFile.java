package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program:
 * @description:
 **/
@Data
@ApiModel(value = "文件查找参数")
public class QSysFile {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "文件编号")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Long id;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String fileName;
    /**
     * 原文件名
     */
    @ApiModelProperty(value = "原始文件名")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String original;
    /**
     * 容器名称
     */
    @ApiModelProperty(value = "存储桶名称")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String bucketName;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String type;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Long fileSize;
    /**
     * 上传人
     */
    @ApiModelProperty(value = "创建者")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String createUser;
    /**
     * 上传时间
     */
    @ApiModelProperty(value = "创建时间")
    @DynamicQuery(value = DynamicQuery.QType.between)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DynamicQuery(value = DynamicQuery.QType.between)
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新者")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String updateUser;
    /**
     * 删除标识：1-删除，0-正常
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer delFlag;
}
