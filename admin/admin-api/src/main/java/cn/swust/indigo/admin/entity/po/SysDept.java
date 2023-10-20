package cn.swust.indigo.admin.entity.po;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.util.PinYinUtil;
import cn.swust.indigo.admin.entity.util.PinYinUtils;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门管理
 * </p>
 */
@Data
@ApiModel(value = "部门")
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 2449263323L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @Size(min = 1, max = 50, message = "部门名称 最大为50")
    @ApiModelProperty(value = "部门名称 长度: 1-50")
    @TableField(value = "department_name")
    private String name;



    /**
     * 部门拼音
     */
    @ApiModelProperty(value = "部门拼音 长度: 1-50")
    @TableField(value = "department_py")
    private String departmentPy;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField(value = "sort")
    private Integer sort = 0;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",fill =FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(exist = false)
    private LocalDateTime updateTime;

    /**
     * 是否删除  1：已删除  0：正常
     */

    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常 长度: 1-1")
    @TableLogic
    private Boolean delFlag;

    /**
     *
     */
    @ApiModelProperty(value = "父亲节点ID")
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 部门类别ID，1:行政部门 2:区级部门 3:考察部门
     */
    @ApiModelProperty(value = "部门类别ID，1:行政部门 2:区级部门 3:考察部门")
    @TableField(value = "department_type")
    private Integer type;

    /**
     * 部门电话
     */
    @ApiModelProperty(value = "部门电话 长度: 1-20")
    @TableField(value = "phone")
    private String phone;

    /**
     * 部门主管ID
     */
    @ApiModelProperty(value = "部门主管ID")
    @TableField(value = "leader_id")
    private int leaderId;

    /**
     * 每次获取部门拼音，检查是否正常
     * @return
     */
    public String getDepartmentPy() {
        if((ObjectUtil.isNull(this.departmentPy) || "".equals(this.departmentPy)) && ObjectUtil.isNotNull(this.name)){
            // 获取每一个字的拼音首字母
//            this.departmentPy = StringUtils.join(PinYinUtils.getHeadByString(this.name)).toLowerCase();
            this.departmentPy = PinYinUtil.getFirstSpell(this.name);
        }
        return departmentPy;
    }
}
