package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.Site;
import cn.swust.indigo.mce.entity.vo.CheckDept;
import cn.swust.indigo.mce.entity.vo.CheckSite;

import java.util.List;

public interface CheckDeptOrSiteMapper {

    List<CheckDept> getCheckDept(Integer checkPlanId,Integer userId);

    List<CheckSite> getCheckSite(Integer checkPlanId,Integer userId,List<Site> sites);
}
