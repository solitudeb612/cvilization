package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.mapper.CommitDetailMapper;
import cn.swust.indigo.mce.mapper.CommitReportMapper;
import cn.swust.indigo.mce.service.CommitReportService;
import cn.swust.indigo.mce.service.GuideService;
import cn.swust.indigo.mce.service.TaskCommitService;
import cn.swust.indigo.mce.utils.CommitReportFailedType;
import cn.swust.indigo.mce.utils.CommitReportStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Binary cat
 * @date 2023/3/23 14:35
 */
@Service
public class CommitReportServiceImpl extends ServiceImpl<CommitReportMapper, CommitReport> implements CommitReportService {

    @Autowired
    CommitReportService commitReportService;

    @Autowired
    CommitDetailMapper commitDetailMapper;

    @Autowired
    TaskCommitService taskCommitService;

    @Autowired
    GuideService guideService;

    @Override
    public boolean checkStatusCommit(Integer reportId) {
        CommitReport byId = commitReportService.getById(reportId);
        if (byId.getStatus() == 1 || byId.getStatus() == 2){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean checkStatusByDetailId(Integer commitDetailId) {
        CommitDetail commitDetail = commitDetailMapper.selectById(commitDetailId);
        return checkStatusCommit(commitDetail.getReportId());
    }

    @Override
    public Integer checkSubmit(Integer reportId) {
        // 检测提交的申请是否符合要求
        List<CommitDetail> commitDetailList = commitDetailMapper.selectList(new LambdaQueryWrapper<CommitDetail>()
                .eq(CommitDetail::getReportId, reportId));

        CommitReport commitReport = commitReportService.getById(reportId);
        // 判断此时保存的任务是否是在提交范围之类的
        Guide guide = guideService.getById(commitReport.getGuideId());
        LocalDateTime now = LocalDateTime.now();
        if (ObjectUtil.isNotNull(guide.getBeginTime()) && now.isBefore(guide.getBeginTime())) {
            return CommitReportFailedType.BEFORE_BEGIN_TIME;
        }
        if (ObjectUtil.isNotNull(guide.getEndTime()) && now.isAfter(guide.getEndTime())) {
            return CommitReportFailedType.AFTER_END_TIME;
        }

        // 检测提交的申请集合中是否包含必须的条目
        Set<Integer> origin = commitDetailList.stream().map(CommitDetail::getTaskCommitId).collect(Collectors.toSet());
        LambdaQueryWrapper<TaskCommit> taskCommitLambdaQueryWrapper = new LambdaQueryWrapper<>();
        taskCommitLambdaQueryWrapper.eq(TaskCommit::getTaskId, commitDetailList.get(0).getTaskId());
        List<TaskCommit> standard = taskCommitService.list(taskCommitLambdaQueryWrapper);
        for (TaskCommit taskCommit : standard) {
            if (taskCommit.getIsRequired() == 1 && !origin.contains(taskCommit.getTaskCommitId())) {
                return CommitReportFailedType.MISSING_NECESSARY_DOCUMENTS;
            }
        }

        // 检查文件类型
        for (CommitDetail i : commitDetailList) {
            for (TaskCommit j : standard) {
                if (Objects.equals(i.getTaskCommitId(), j.getTaskCommitId()) && !Objects.equals(i.getType(), j.getType())) {
                    return CommitReportFailedType.ERROR_FILE_TYPE;
                }
            }
        }
        // 设置为已经提交
        commitReport.setStatus(CommitReportStatus.SUBMIT);
        commitReport.setCommitTime(LocalDateTimeUtil.now());
        commitReportService.updateById(commitReport);
        return CommitReportFailedType.SUBMIT_SUCCESS;
    }
}
