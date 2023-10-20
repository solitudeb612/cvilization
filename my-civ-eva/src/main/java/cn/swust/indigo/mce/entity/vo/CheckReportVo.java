package cn.swust.indigo.mce.entity.vo;

import lombok.Data;

import java.util.Date;
@Data
public class CheckReportVo {
   private Integer checkGroupId;
   private Integer departmentId;
   private Integer siteId;
   private Integer count;
   private Date commitTime;
}
