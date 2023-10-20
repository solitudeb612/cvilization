package cn.swust.indigo.mce.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CheckDept {
    /**
     * 检查方案明细Id
     */
    private Integer checkPlanDetailId;

    /**
     * 检查方案Id
     */
    private Integer checkPlanId;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     *  部门名
     */
    private String departmentName;

    /**
     * 检查组id
     */
    private Integer checkGroupId;

    /**
     * 检查组名
     */
    private String checkGroupName;
}
