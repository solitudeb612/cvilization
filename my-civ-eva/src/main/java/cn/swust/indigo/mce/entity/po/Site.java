package cn.swust.indigo.mce.entity.po;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.util.PinYinUtil;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 站点
 *
 * @author lhz
 * @date 2023-02-24 17:01:59
 */
@Data
@TableName("site")
@ApiModel(value = "站点")
public class Site {
    private static final long serialVersionUID = 114514L;

    /**
     * 点位id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "点位id")
    private Integer siteId;

    /**
     * 名字
     */
    @Size(min = 1, max = 255, message = "名字 最大为255")
    @ApiModelProperty(value = "名字 长度: 1-255")
    @TableField("name")
    private String name;

    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    @TableField("mini_picture")
    private String thumbnail;


    /**
     * 名字拼音
     */
    @Size(min = 1, max = 255, message = "名字拼音 最大为255")
    @ApiModelProperty(value = "名字拼音 长度: 1-255")
    @TableField("site_py")
    private String sitePy;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    /**
     * 维度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    /**
     * 地址
     */
    @Size(min = 1, max = 255, message = "地址 最大为255")
    @ApiModelProperty(value = "地址 长度: 1-255")
    @TableField("address")
    private String address;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @TableField("leader_id")
    private Integer leaderId;

    /**
     * 站点简介
     */
    @ApiModelProperty(value = "部门简介")
    @TableField("introduce")
    private String introduce;

    /**
     * 行政上级
     */
    @ApiModelProperty(value = "行政上级")
    @TableField("department_id")
    private Integer departmentId;

    @Size(min = 1, max = 1, message = "是否删除  1：已删除  0：正常 最大为1")
    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常 长度: 1-1")
    @TableLogic
    @TableField("del_flag")
    private Boolean delFlag;

    public String getSitePy() {
        if ((ObjectUtil.isNull(this.sitePy) || "".equals(this.sitePy)) && ObjectUtil.isNotNull(this.name)){
            // 获取每一个字的拼音首字母
//            this.sitePy = StringUtils.join(PinYinUtils.getHeadByString(this.name)).toLowerCase();
            this.sitePy = PinYinUtil.getFirstSpell(this.name);
        }
        return sitePy;
    }

}
