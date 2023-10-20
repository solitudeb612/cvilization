package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
@Data
public class DeptDetail {
    /**
     * 点位id
     */
    @TableId(type = IdType.AUTO)
    private Integer deptId;

    /**
     * 名字
     */
    @Size(min = 1, max = 255, message = "名字 最大为255")
    @ApiModelProperty(value = "名字 长度: 1-255")
    private String name;

    /**
     *
     */
    private Integer checkGroupId;

    /**
     * 组名
     */
    private String groupName;




    /**
     * 检测方法
     */
    private String checkMethod;

    private String check_plan_name;

    private Integer counts = 0;

    private Date commitTime;
    @ApiModelProperty("测评明细")
    private String memo;

}
