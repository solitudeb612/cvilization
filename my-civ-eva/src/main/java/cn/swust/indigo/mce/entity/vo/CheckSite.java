package cn.swust.indigo.mce.entity.vo;

import lombok.Data;

@Data
public class CheckSite {
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
    private Integer siteId;

    /**
     * 检查组id
     */
    private Integer checkGroupId;

    /**
     * 检查组名
     */
    private String checkGroupName;

    /**
     * 站点名
     */
    private String siteName;

    /**
     *  所属部门名
     */
    private String deptName;
}
