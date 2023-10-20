package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@ApiModel(value = "角色")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    @ApiModelProperty(value = "角色编号")
    private Integer roleId;
    @ApiModelProperty(value = "创建人id")
    private Integer creatorId;
    @ApiModelProperty(value = "修改人id")
    private Integer updaterId;
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    @Size(min = 1, max = 64, message = "角色名称字符长度1-64")
    @NotNull
    private String roleName;

    @NotBlank(message = "角色标识不能为空")
    @ApiModelProperty(value = "角色标识")
    @Size(min = 1, max = 64, message = "角色标识字符长度1-64")
    @NotNull
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    @Size(min = 0, max = 255, message = "角色描述字符长度0-255")
    private String roleDesc;


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
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    @Size(min = 0, max = 1, message = "删除标记字符长度0-255")
    private String delFlag;

}