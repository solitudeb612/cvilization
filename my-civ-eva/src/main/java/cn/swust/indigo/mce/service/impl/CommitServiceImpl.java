package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.SysUserRole;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.admin.service.*;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.entity.vo.*;
import cn.swust.indigo.mce.entity.vo.CommitReportVo;
import cn.swust.indigo.mce.mapper.*;
import cn.swust.indigo.mce.service.*;
import cn.swust.indigo.mce.utils.CommitDetailType;
import cn.swust.indigo.mce.utils.CommitReportStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目提交内容
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@Service
@AllArgsConstructor
public class CommitServiceImpl extends
        ServiceImpl<CommitDetailMapper, CommitDetail>
        implements CommitService {

    @Autowired
    private RuleDeptTaskMapper ruleDeptTaskMapper;

    @Autowired
    private EvaluationRulesMapper evaluationRulesMapper;

    @Autowired
    private CommitDetailMapper commitDetailMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private TaskCommitService taskCommitService;
    @Autowired
    private CommitReportService commitReportService;

    @Autowired
    private CommitReportMapper commitReportMapper;

    @Autowired
    private TaskCommitMapper taskCommitMapper;
    @Autowired
    private CommitSpotCheckService commitSpotCheckService;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private ICommitImgStarService iCommitImgStarService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private GuideService guideService;

    @Override
    @Deprecated
    public List<CommitRuleTableInfo> getCommitList(QCommitDetail commit) {
        //TODO
        Guide guide = guideMapper.selectById(commit.getGuideId());
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
         Integer[] roleIds = SecurityUtils.getUser().getRoles();
        if (Arrays.stream(roleIds).findAny().equals(1)) {
            userId = null;
        }
        List<CommitRuleTableInfo> commitRuleList = ruleDeptTaskMapper.commitRuleList(userId, guide.getRootRuleId());
        SysDept deptByUserId = sysDeptService.getDeptByUserId(userId);
        // 查询四级规则内容
        List<Integer> ruleIdList = Lists.transform(commitRuleList, CommitRuleTableInfo::getRuleId);

        List<CommitRuleTableInfo> allRuleInfo = evaluationRulesMapper.getAllRuleInfo(ruleIdList);

        // 根据ruleId 设置 是否是牵头部门、部门id、任务详情、部门名称
        for (CommitRuleTableInfo info : allRuleInfo) {
            for (CommitRuleTableInfo taskInfo : commitRuleList) {
                if (info.getRuleId().equals(taskInfo.getRuleId())) {
                    info.setLeadFlag(taskInfo.getLeadFlag());
                    info.setDepartmentId(taskInfo.getDepartmentId());
                    info.setTaskDetail(taskInfo.getTaskDetail());
                    info.setDepartmentName(deptByUserId.getName());
                    break;
                }
            }
        }

        //补齐提交的其它所有信息、上次提交时间、提交的总数、reportId、状态
        List<CommitRuleTableInfo> commitInfoList = commitReportMapper.getCommitReportCount(userId, commit.getGuideId());
        for (CommitRuleTableInfo info : allRuleInfo) {
            // 设置状态未申报
            info.setStatus(0);
            for (CommitRuleTableInfo commitRuleTableInfo : commitInfoList) {
                if (info.getRuleId().equals(commitRuleTableInfo.getRuleId()) && info.getDepartmentId().equals(commitRuleTableInfo.getDepartmentId())) {
                    info.setCommitCount(commitRuleTableInfo.getCommitCount());
                    info.setLastCommitTime(commitRuleTableInfo.getLastCommitTime());
                    info.setReportId(commitRuleTableInfo.getReportId());
                    if (commitRuleTableInfo.getStatus() == 0) {
                        // 如果原来状态未草稿，则设置信息为1
                        info.setStatus(1);
                    } else if (commitRuleTableInfo.getStatus() == 1) {
                        // 设置为已经提交
                        info.setStatus(2);
                    }
                    break;
                }
            }
        }

        return allRuleInfo;
    }

    @Override
    public List<Department> getLeadDepartment(Integer guideId) {
        List<Department> departments = commitDetailMapper.selectLeadDept(guideId);
        return departments;
    }

    @Override
    public Page getAllDepartmentCommitByAdmin(QAdminRuleTask qAdminRuleTask, Page page) {
        // 注意，当参数中status为零的情况下，修改了 RULE DEPT TASK MAPPER文件
        long current = page.getCurrent();
        long index = (current - 1) * page.getSize();
        // 根据角色查询 RULE DEPT TASK
        if(!qAdminRuleTask.isAdmin()){
            QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
            sysDeptQueryWrapper.eq("leader_id",SecurityUtils.getUser().getSysUser().getUserId());
            SysDept sysDept = sysDeptMapper.selectOne(sysDeptQueryWrapper);
            qAdminRuleTask.setDepartmentId(sysDept.getDeptId());
        }
        LambdaQueryWrapper<RuleDeptTask> ruleDeptWrapper = new LambdaQueryWrapper<>();
        Integer rootRuleId = guideMapper.selectById(qAdminRuleTask.getGuideId()).getRootRuleId();
        ruleDeptWrapper.eq(RuleDeptTask::getRootRuleId, rootRuleId);
        if(qAdminRuleTask.getDepartmentId() != null){
            ruleDeptWrapper.eq(RuleDeptTask::getDepartmentId, qAdminRuleTask.getDepartmentId()).eq(RuleDeptTask::getLeadFlag, 1);
        }
        List<RuleDeptTask> ruleDeptTasks = ruleDeptTaskMapper.selectList(ruleDeptWrapper);
        if(ruleDeptTasks.size() == 0){
            return page;
        }
        List<Integer> ruleIds = ruleDeptTasks.stream().map(ruleDeptTask -> ruleDeptTask.getRuleId()).collect(Collectors.toList());
        //获取一个规则一个部门提交的总数
        Integer guideId = qAdminRuleTask.getGuideId();
        List<CommitRuleTableInfoVo> commitRuleTableInfoVos = ruleDeptTaskMapper.
                getAllSubordinateDepartments(rootRuleId, guideId, index, page.getSize(),qAdminRuleTask.getStatus());
        List<CommitRuleTableInfo> commitRuleTableInfos = evaluationRulesMapper.getAllRuleInfo(ruleIds);
        List<CommitRuleTableInfoVo> commitRuleTableInfoVos1 = new ArrayList<>();
        for (int i = 0; i < commitRuleTableInfoVos.size(); i++) {
            for (int j = 0; j < commitRuleTableInfos.size(); j++) {
                CommitRuleTableInfoVo commitRuleTableInfoVo = commitRuleTableInfoVos.get(i);
                CommitRuleTableInfo commitRuleTableInfo = commitRuleTableInfos.get(j);
                if (commitRuleTableInfo.getRuleId().equals(commitRuleTableInfoVo.getRuleId())) {
                    commitRuleTableInfoVo.setRuleLevel1Name(commitRuleTableInfo.getRuleLevel1Name());
                    commitRuleTableInfoVo.setRuleLevel2Name(commitRuleTableInfo.getRuleLevel2Name());
                    commitRuleTableInfoVo.setRuleLevel3Name(commitRuleTableInfo.getRuleLevel3Name());
                    commitRuleTableInfoVo.setRuleLevel4Name(commitRuleTableInfo.getRuleLevel4Name());
                    commitRuleTableInfoVo.setGuideId(qAdminRuleTask.getGuideId());
                    commitRuleTableInfoVos1.add(commitRuleTableInfoVo);
                }
            }
        }
        // 如果传入的 status 为 0  wxd
//        if (ObjectUtil.isNotNull(qAdminRuleTask.getStatus()) && qAdminRuleTask.getStatus() == 0){
//            commitRuleTableInfoVos1 = commitRuleTableInfoVos1.stream().map(item -> {
//                if (ObjectUtil.isNull(item.getReportId())) {
//                    item.setStatus(0);
//                }
//                return item;
//            }).collect(Collectors.toList());
//        }

        page.setTotal(commitRuleTableInfoVos1.size());
        return page.setRecords(commitRuleTableInfoVos1);
    }


    @Override
    public List<CommitReportVo> getCommitReportByTaskId(Integer taskId, Integer guideId, Integer status) {
        RuleDeptTask ruleDeptTask = ruleDeptTaskMapper.selectById(taskId);
        if(ruleDeptTask == null || commitReportMapper.selectList(new QueryWrapper<CommitReport>().eq("guide_id",guideId).eq("rule_id",ruleDeptTask.getRuleId())) == null){
            return null;
        }
        List<CommitReportVo> commitReportVos = this.getTaskCommitReportByTaskId(ruleDeptTask.getRuleId(),ruleDeptTask.getDepartmentId(),guideId,status);
        Iterator iterator = commitReportVos.iterator();
        while (iterator.hasNext()){
            CommitReportVo commitReportVo = (CommitReportVo)iterator.next();
            commitReportVo.setTaskDetail(ruleDeptTask.getTaskDetail());
            commitReportVo.setDepartmentName(sysDeptMapper.selectById(commitReportVo.getDepartmentId()).getName());
            UserVO userVO = sysUserService.selectUserVoById(commitReportVo.getCreatorId());
            commitReportVo.setCreatorName(userVO.getNickname());
            commitReportVo.setCreatorPhone(userVO.getPhone());
            commitReportVo.setRuleName(evaluationRulesMapper.selectById(commitReportVo.getRuleId()).getRuleName());
            // 插入抽查内容
            LambdaQueryWrapper<CommitSpotCheck> commitSpotCheckLambdaQueryWrapper = new LambdaQueryWrapper<>();
            commitSpotCheckLambdaQueryWrapper.eq(CommitSpotCheck::getReportId, commitReportVo.getReportId()).orderByDesc(CommitSpotCheck::getCommitSpotId).last(" limit 1");
            CommitSpotCheck spotCheck = commitSpotCheckService.getOne(commitSpotCheckLambdaQueryWrapper);
            if (ObjectUtil.isNotNull(spotCheck)) {
                commitReportVo.setCommitSpotCheck(spotCheck);
                commitReportVo.setReviewOpinion(spotCheck.getReviewOpinion());
                commitReportVo.setReviewGrade(spotCheck.getReviewGrade());
            }

        }
        return commitReportVos;
    }

//    @Override
//    public List<CommitReportVo> getCommitReportByTaskId(Integer taskId, Integer guideId, Integer status) {
//        LambdaQueryWrapper<CommitDetail> commitDetailWrapper = new LambdaQueryWrapper<>();
//        commitDetailWrapper.eq(CommitDetail::getTaskId, taskId);
//        List<CommitDetail> commitDetails = commitDetailMapper.selectList(commitDetailWrapper);
//        List<Integer> reportIds = commitDetails.stream().map(commitDetail -> commitDetail.getReportId()).collect(Collectors.toList());
//        LambdaQueryWrapper<CommitReport> commitReportWrapper = new LambdaQueryWrapper<>();
//        commitReportWrapper.in(CommitReport::getReportId, reportIds);
//        commitReportWrapper.le(CommitReport::getStatus, status);
//        List<CommitReport> commitReports = commitReportMapper.list(commitReportWrapper);
//        List<CommitReportVo> commitReportVos = new ArrayList<CommitReportVo>();
//        for (CommitReport commitReport : commitReports) {
//            CommitReportVo commitReportVo = new CommitReportVo();
//            BeanUtils.copyProperties(commitReport, commitReportVo);
//            List<CommitDetail> commitDetailsList = new ArrayList<CommitDetail>();
//            for (CommitDetail commitDetail : commitDetails) {
//                if (commitReportVo.getReportId().equals(commitDetail.getReportId())) {
//                    commitDetailsList.add(commitDetail);
//                }
//            }
////            commitReportVo.setCommitDetail(commitDetailsList);
//            commitReportVos.add(commitReportVo);
//        }
//        return commitReportVos;
//    }

    @Override
    public List<CommitReportVo> getTaskCommitReportByTaskId(Integer ruleId, Integer departmentId, Integer guideId, Integer status) {
        Integer rootRuleId = guideMapper.selectById(guideId).getRootRuleId();
        QueryWrapper<RuleDeptTask> ruleDeptTaskQueryWrapper = new QueryWrapper<>();
        ruleDeptTaskQueryWrapper.eq("root_rule_id",rootRuleId).eq("rule_id",ruleId).eq("department_id",departmentId);
        RuleDeptTask ruleDeptTask = ruleDeptTaskMapper.selectOne(ruleDeptTaskQueryWrapper);
        // 如果没有 RULE DEPT TASK 就返回 空数组
        if (ObjectUtil.isNull(ruleDeptTask)) {
            return new ArrayList<>();
        }
        Integer taskId = ruleDeptTask.getTaskId();
        LambdaQueryWrapper<TaskCommit> taskCommitLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskCommitLambdaQueryWrapper.eq(TaskCommit::getTaskId,taskId);
        List<TaskCommit> taskCommits = taskCommitMapper.selectList(taskCommitLambdaQueryWrapper);
        LambdaQueryWrapper<CommitReport> commitReportWrapper = new LambdaQueryWrapper<>();
        commitReportWrapper.eq(CommitReport::getGuideId,guideId);
        commitReportWrapper.eq(CommitReport::getRuleId,ruleId);
        commitReportWrapper.eq(CommitReport::getDepartmentId,departmentId);
        commitReportWrapper.ge(CommitReport::getStatus, status);
        List<CommitReport> commitReports = commitReportMapper.selectList(commitReportWrapper);
        List<CommitReportVo> commitReportVos = new ArrayList<CommitReportVo>();
        for (CommitReport commitReport : commitReports) {
            CommitReportVo commitReportVo = new CommitReportVo();
            BeanUtils.copyProperties(commitReport, commitReportVo);
            List<TaskCommitDetail> taskCommitDetails = new ArrayList<TaskCommitDetail>();
            for (TaskCommit taskCommit : taskCommits) {
                CommitDetail commitDetail = commitDetailMapper.selectOne(new QueryWrapper<CommitDetail>().eq("task_commit_id", taskCommit.getTaskCommitId()).eq("report_id",commitReport.getReportId()));
                TaskCommitDetail taskCommitDetail = new TaskCommitDetail();
                BeanUtils.copyProperties(taskCommit, taskCommitDetail);
//                CommitSpotCheck commitSpotCheck = commitSpotCheckService.getOne(new QueryWrapper<CommitSpotCheck>().eq("commit_id", commitDetail.getCommitId()));
//                if(commitSpotCheck != null){
//                    commitReportVo.setCommitSpotCheck(commitSpotCheck);
//                }
                if(commitDetail != null) {
                    taskCommitDetail.setCommitId(commitDetail.getCommitId());
                    taskCommitDetail.setDescription(commitDetail.getDescription());
                    taskCommitDetail.setName(commitDetail.getName());
                    taskCommitDetail.setFileIdList(commitDetail.getFileIdList());
                    taskCommitDetail.setFileIds(commitDetail.getFileIds());
                    taskCommitDetail.setType(commitDetail.getType());
                }
                taskCommitDetails.add(taskCommitDetail);
            }
            commitReportVo.setTaskCommitDetails(taskCommitDetails);
            commitReportVos.add(commitReportVo);
        }
        return commitReportVos;
    }

    /**
     * 拿到多个提交后，检测附件个数是否符合要求；且存已经完成必填选项 （注意此方法使用了事务）
     *
     * @param commitReportAndDetail 新增提交多个细节
     * @return 成功信息
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public R saveCommitDetailList(CommitReportAndDetail commitReportAndDetail) {
        /*
        List<CommitDetail> commitDetailList = commitReportAndDetail.getStarImageFileCommitDetails().stream().map(
                now -> {
                    CommitDetail temp = new CommitDetail();
                    BeanUtils.copyProperties(now, temp);
                    return temp;
                }).collect(Collectors.toList());
        */

        List<CommitDetail> commitDetailList = commitReportAndDetail.getCommitDetailList();

        // 检测提交的申请是否符合要求
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        CommitReport nowGroup = new CommitReport();
        nowGroup.setCreatorId(userId);
        BeanUtils.copyProperties(commitReportAndDetail, nowGroup);

        // 检测提交的申请集合中是否包含必须的条目
        Set<Integer> origin = commitDetailList.stream().map(CommitDetail::getTaskCommitId).collect(Collectors.toSet());
        LambdaQueryWrapper<TaskCommit> taskCommitLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskCommitLambdaQueryWrapper.eq(TaskCommit::getTaskId, commitDetailList.get(0).getTaskId());
        List<TaskCommit> standard = taskCommitService.list(taskCommitLambdaQueryWrapper);

        for (TaskCommit taskCommit : standard) {
            // 检测过程
            if (taskCommit.getIsRequired() == 1 && !origin.contains(taskCommit.getTaskCommitId())) {
                return R.failed("提交的申请中缺少必要的条目，请检查是否满足要求！");
            }
        }

        // 检查文件类型
        for (CommitDetail i : commitDetailList) {
            for (TaskCommit j : standard) {
                if (Objects.equals(i.getTaskCommitId(), j.getTaskCommitId()) && !Objects.equals(i.getType(), j.getType())) {
                    return R.failed("提交的申请中有的条目文件的类型不同，请修改！");
                }
            }
        }

        // 使用默认插入，回返回新增后的ID
        commitReportService.save(nowGroup);
        // 设置分组id后保存
        List<CommitDetail> saves = commitDetailList.stream().peek(now -> now.setReportId(nowGroup.getReportId())).collect(Collectors.toList());

        this.saveBatch(saves);

        return R.ok("保存成功");
    }

    @Override
    public boolean reviewCommit(QCommitReview qCommitReview) {
        CommitReport commitReport = commitReportMapper.selectById(qCommitReview.getReportId());
        if(qCommitReview.isRejected()){
            commitReport.setStatus(3);
        }else{
            commitReport.setGrade(qCommitReview.getGrade());
            commitReport.setReviewDeptId(qCommitReview.getReviewDeptId());
            commitReport.setReviewInfo(qCommitReview.getReviewInfo());
            commitReport.setStatus(2);

            CommitSpotCheck spotCheck = new CommitSpotCheck();
            spotCheck.setReportId(qCommitReview.getReportId());
            spotCheck.setReviewGrade(qCommitReview.getGrade());
            spotCheck.setReviewOpinion(qCommitReview.getReviewInfo());
            spotCheck.setDepartmentId(qCommitReview.getReviewDeptId());
            spotCheck.setReviewTime(new Date());
            spotCheck.setReviewerId(SecurityUtils.getUser().getSysUser().getUserId());
        }
        return commitReportService.updateById(commitReport);
    }

    @Override
    @Transactional
    public Integer spotReviewCommit(QCommitSpotCheck qCommitSpotCheck) {
        CommitSpotCheck commitSpotCheck = new CommitSpotCheck();
        if(qCommitSpotCheck.getStatus() != 4){
            BeanUtils.copyProperties(qCommitSpotCheck, commitSpotCheck);
            commitSpotCheckService.saveOrUpdate(commitSpotCheck);
        }
        CommitReport commitReport = commitReportMapper.selectById(qCommitSpotCheck.getReportId());
        commitReport.setStatus(qCommitSpotCheck.getStatus());
        commitReport.setGrade(qCommitSpotCheck.getReviewGrade());
        commitReport.setReviewDeptId(qCommitSpotCheck.getDepartmentId());//为什么不用在json中显示写清除，也能获取到
        commitReport.setReviewInfo(qCommitSpotCheck.getReviewOpinion());
        commitReportService.updateById(commitReport);
        return commitSpotCheck.getCommitSpotId() != null?  commitSpotCheck.getCommitSpotId() : null;
    }

    @Override
    public List<CommitReport> getCommitReportList(QCommitReport commitDetail) {
        List<Integer> reportIds = commitDetailMapper.getReportIds(commitDetail.getGuideId(), commitDetail.getRuleId(), commitDetail.getDepartmentId());

        return  new  ArrayList<CommitReport>(commitReportService.listByIds(reportIds));
    }

    @Override
    public List<Integer> getStarImageFileIds(Integer guideId, Integer ruleId, boolean isStar) {
        LambdaQueryWrapper<CommitImgStar> commitImgStarLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(guideId)) {
            commitImgStarLambdaQueryWrapper.eq(CommitImgStar::getGuideId, guideId);
        }
        if (ObjectUtil.isNotNull(ruleId)) {
            commitImgStarLambdaQueryWrapper.eq(CommitImgStar::getRuleId, ruleId);
        }
        if (isStar) {
            commitImgStarLambdaQueryWrapper.eq(CommitImgStar::getStar, true);
        }
        List<CommitImgStar> list = iCommitImgStarService.list(commitImgStarLambdaQueryWrapper);
        return list.stream().map(CommitImgStar::getFileId).collect(Collectors.toList());
    }

    @Override
    public void getStarImagesByIds(ArrayList<Integer> fileIds, HttpServletResponse response) {
        // TODO 下载文件
//        ZipUtil.zip/
    }

    @Override
    public Integer saveImagesIdsByReportId(Integer reportId) {
        CommitReport report = commitReportService.getById(reportId);
        ArrayList<CommitImgStar> commitImgStars = new ArrayList<>();
        LambdaQueryWrapper<CommitDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommitDetail::getReportId, report.getReportId());
        List<CommitDetail> commitDetailList = this.list(queryWrapper);
        for (CommitDetail commitDetail : commitDetailList) {
            if (commitDetail.getType() == CommitDetailType.IMAGE_FILE) {
                // 仅保留图片文件
                for (Integer integer : commitDetail.getFileIdList()) {
                    CommitImgStar temp = new CommitImgStar(null, commitDetail.getCommitId(), report.getGuideId(), integer, report.getRuleId(), 0);
//                    CommitImgStar temp = new CommitImgStar();
//                    temp.setReportId(report.getReportId());
//                    temp.setRuleId(report.getRuleId());
//                    temp.setGuideId(report.getGuideId());
//                    temp.setStar(0);
//                    temp.setCommitDetailId(commitDetail.getCommitId());
//                    temp.setFileId(integer);
//                    commitImgStars.add(temp);
                }
            }
        }
        iCommitImgStarService.saveBatch(commitImgStars);
        return commitImgStars.size();
    }

    @Override
    public List<CommitRuleTableInfo> listCommitRuleTableInfo(QCommitDetail qCommitDetail) {
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        SysUserRole temp = sysUserRoleService.getOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        Integer roleId = temp.getRoleId();
        // 初始化部门id
        Integer departmentId = null;
        // 如果是不是超级管理员
        if (roleId != 1){
            SysUser user = sysUserService.getById(userId);
            departmentId = user.getDepartmentId();
        }
        Guide guide = guideService.getById(qCommitDetail.getGuideId());
        List<CommitRuleTableInfo> infos = ruleDeptTaskMapper.listCommitRuleTableInfo(guide.getRootRuleId(),
                departmentId, qCommitDetail.getRuleId(), qCommitDetail.getGuideId());
        for (CommitRuleTableInfo info : infos) {
            if (ObjectUtil.isNotNull(info.getReportId())){
                // 如果有提交
                if(Objects.equals(info.getReportStatus(), CommitReportStatus.DRAFT)){
                    // 如果 report 此时为草稿，此时显示状态为草稿
                    info.setStatus(1);
                } else if (Objects.equals(info.getReportStatus(),CommitReportStatus.SUBMIT)){
                    // 状态表示 report 已经提交通过
                    info.setStatus(2);
                }
            } else{
                // 设置状态没有提交
                info.setStatus(0);
            }
        }
        return infos;
    }

    @Override
    public List<TaskCommitWithDetail> getTaskAndDetail(Integer ruleId, Integer departmentId, Integer guideId, List<TaskCommit> taskCommits) {
        LambdaQueryWrapper<CommitReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommitReport::getRuleId, ruleId)
                .eq(CommitReport::getGuideId, guideId)
                .eq(CommitReport::getDepartmentId, departmentId);
        CommitReport report = commitReportService.getOne(wrapper);
        List<CommitDetail> commitDetailList = new ArrayList<>();
        if(report != null){
            LambdaQueryWrapper<CommitDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CommitDetail::getReportId, report.getReportId());
            commitDetailList = commitDetailMapper.selectList(queryWrapper);
        }

        ArrayList<TaskCommitWithDetail> res = new ArrayList<>();
        for (TaskCommit commit : taskCommits) {
            TaskCommitWithDetail temp = new TaskCommitWithDetail();
            BeanUtils.copyProperties(commit,temp);

            for (CommitDetail commitDetail : commitDetailList) {
                if (temp.getTaskCommitId().equals(commitDetail.getTaskCommitId())) {
                    // 如果相等就塞进去
                    temp.setCommitDetail(commitDetail);
                    temp.setDetailID(commitDetail.getCommitId());
                }
            }
            res.add(temp);
        }

        return res;
    }

    @Override
    public boolean clearFileIds(Integer commitId) {
        CommitDetail commitDetail = commitDetailMapper.selectById(commitId);
        commitDetail.setFileIdList(null);
        if(commitDetailMapper.updateById(commitDetail)>0)
            return true;
        else
            return false;
    }
}
