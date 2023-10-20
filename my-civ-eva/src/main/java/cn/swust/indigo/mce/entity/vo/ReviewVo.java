package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.DeptDetail;
import lombok.Data;

import java.util.List;

@Data
public class ReviewVo {
    private List<DeptDetail> deptDetails;
    private List<SiteDetail> siteDetails;
}
