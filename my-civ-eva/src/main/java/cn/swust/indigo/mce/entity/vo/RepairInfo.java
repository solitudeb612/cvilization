package cn.swust.indigo.mce.entity.vo;
import lombok.Data;

import java.util.Date;

/**
 * @author Binary Tree Cat
 */
@Data
public class RepairInfo {
    private Integer checkProblemId;

    private String commitDevice;

    private String repairerName;

    private Date repairedTime;
}
