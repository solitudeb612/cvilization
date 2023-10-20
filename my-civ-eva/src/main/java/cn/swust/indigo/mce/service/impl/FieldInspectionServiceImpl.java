//package cn.swust.indigo.mce.service.impl;
//
//import cn.swust.indigo.admin.entity.po.SysDept;
//import cn.swust.indigo.admin.entity.po.SysUser;
//import cn.swust.indigo.admin.service.SysDeptService;
//import cn.swust.indigo.common.security.util.SecurityUtils;
//import cn.swust.indigo.mce.entity.po.DeptDetail;
//import cn.swust.indigo.mce.entity.po.Site;
//import cn.swust.indigo.mce.entity.vo.CheckReportVo;
//import cn.swust.indigo.mce.entity.vo.QFieldInspection;
//import cn.swust.indigo.mce.entity.vo.ReviewVo;
//import cn.swust.indigo.mce.entity.vo.SiteDetail;
//import cn.swust.indigo.mce.mapper.CheckGroupMapper;
//import cn.swust.indigo.mce.mapper.CheckPlanMapper;
//import cn.swust.indigo.mce.service.FieldInspectionService;
//import cn.swust.indigo.mce.service.SiteService;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class FieldInspectionServiceImpl implements FieldInspectionService {
//    @Autowired
//    private SysDeptService sysDeptService;
//    @Autowired
//    private SiteService siteService;
//    @Autowired
//    private CheckGroupMapper checkGroupMapper;
//    @Autowired
//    private CheckPlanMapper checkPlanMapper;
//    @Override
//    public ReviewVo getFieldInspection(QFieldInspection qFieldInspection) {
//        SysUser sysUser = SecurityUtils.getUser().getSysUser();
//        Integer departmentId = sysUser.getDepartmentId();
//        //他管理的部门的下级站点
//        List<SiteDetail> siteDetails = checkGroupMapper.selectSiteDetail
//                (qFieldInspection.getCheckPlanId(),departmentId);
//        List<CheckReportVo> checkReportVos = checkGroupMapper.
//                findSiteCountAndLastTime(qFieldInspection.getCheckId(),departmentId);
//        for(SiteDetail siteDetail : siteDetails){
//            for(CheckReportVo checkReportVo : checkReportVos){
//                if(siteDetail.getSiteId().equals(checkReportVo.getSiteId())
//                && siteDetail.getCheckGroupId().equals(checkReportVo.getCheckGroupId())){
//                    siteDetail.setCounts(checkReportVo.getCount());
//                    siteDetail.setCommitTime(checkReportVo.getCommitTime());
//                }
//            }
//        }
//        //他管理的部门下的下级部门
//        List<DeptDetail> deptDetails  = checkGroupMapper.selectDeptDetail(qFieldInspection.getCheckPlanId(),departmentId);
//       List<CheckReportVo>  checkReportVos1 = checkGroupMapper.
//                findDeptCountAndLastTime(qFieldInspection.getCheckId(),departmentId);
//        for(DeptDetail deptDetail : deptDetails){
//            for(CheckReportVo checkReportVo : checkReportVos1){
//                if(deptDetail.getDeptId().equals(checkReportVo.getDepartmentId())
//                        && deptDetail.getCheckGroupId().equals(checkReportVo.getCheckGroupId())){
//                    deptDetail.setCounts(checkReportVo.getCount());
//                    deptDetail.setCommitTime(checkReportVo.getCommitTime());
//                }
//            }
//        }
//        ReviewVo reviewVo = new ReviewVo();
//        reviewVo.setDeptDetails(deptDetails);
//        reviewVo.setSiteDetails(siteDetails);
//        return reviewVo;
//    }
//}
