package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.mce.entity.po.CommitReport;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.entity.vo.DataVo;
import cn.swust.indigo.mce.entity.vo.GuidePerCount;
import cn.swust.indigo.mce.mapper.CommitReportMapper;
import cn.swust.indigo.mce.mapper.GuideMapper;
import cn.swust.indigo.mce.mapper.RuleDeptTaskMapper;
import cn.swust.indigo.mce.service.DataOverviewService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Binary cat
 * @date 2023/4/25 15:26
 */
@Service
public class DataOverviewServiceImpl implements DataOverviewService {
    @Autowired
    GuideMapper guideMapper;

    @Autowired
    RuleDeptTaskMapper ruleDeptTaskMapper;

    @Autowired
    CommitReportMapper commitReportMapper;

    @Override
    public List<GuidePerCount> getGuideMonthCount() {
        List<GuidePerCount> temp = guideMapper.monthTotalCount();
        List<GuidePerCount> res = guideMapper.monthPublishCount();
        return this.unionTotalAndPublish(temp, res);
    }

    @Override
    public List<GuidePerCount> getGuideYearCount() {
        List<GuidePerCount> temp = guideMapper.yearTotalCount();
        List<GuidePerCount> res = guideMapper.yearPublishCount();
        return this.unionTotalAndPublish(temp, res);
    }

    @Override
    public DataVo getDataVo() {
        DataVo dataVo = new DataVo();

        dataVo.setGuideTotalCount(guideMapper.selectCount(null));
        dataVo.setReportTotalCount(commitReportMapper.selectCount(null));

        // 获取 最新的 GUIDE
        Guide guide = guideMapper.getLastUsefulGuide();

//        if (guide == null){
//            return new DataVo();
//        }

        Integer guideId = guide.getGuideId();
        // 被布置任务的部门总数
        LambdaQueryWrapper<RuleDeptTask> ruleDeptTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ruleDeptTaskLambdaQueryWrapper.eq(RuleDeptTask::getRootRuleId, guide.getRootRuleId());
        Integer shouldCount = ruleDeptTaskMapper.selectCount(ruleDeptTaskLambdaQueryWrapper);
        // 提交的报告总数
        LambdaQueryWrapper<CommitReport> commitReportLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commitReportLambdaQueryWrapper.eq(CommitReport::getGuideId,guide.getGuideId());
        Integer yetCount = commitReportMapper.selectCount(commitReportLambdaQueryWrapper);

        dataVo.setAbsenteeismDepartmentCount((Math.max(shouldCount - yetCount, 0)));

        // 草稿的 REPORT
        LambdaQueryWrapper<CommitReport> draft = new LambdaQueryWrapper<>();
        draft.eq(CommitReport::getGuideId, guideId).eq(CommitReport::getStatus, 0);
        dataVo.setDraftReportCount(commitReportMapper.selectCount(draft));
        // 提交 通过 的 REPORT
        LambdaQueryWrapper<CommitReport> submit = new LambdaQueryWrapper<>();
        submit.eq(CommitReport::getGuideId, guideId)
                .and(i -> i.eq(CommitReport::getStatus, 1).or().eq(CommitReport::getStatus, 2));
        dataVo.setSubmitReportCount(commitReportMapper.selectCount(submit));
        // 所有 驳回的 REPORT
        LambdaQueryWrapper<CommitReport> overrule = new LambdaQueryWrapper<>();
        overrule.eq(CommitReport::getGuideId, guideId)
                .and(i -> i.eq(CommitReport::getStatus, 3).or().eq(CommitReport::getStatus, 4));
        dataVo.setOverruleReportCount(commitReportMapper.selectCount(overrule));

        return dataVo;
    }

    private List<GuidePerCount> unionTotalAndPublish(List<GuidePerCount> temp, List<GuidePerCount> res) {
        for (GuidePerCount i : res) {
            for (GuidePerCount j : temp) {
                if (Objects.equals(j.getTime(), i.getTime())) {
                    i.setPublishCount(j.getPublishCount());
                }
            }
            if (ObjectUtil.isNull(i.getPublishCount())) {
                i.setPublishCount(0);
            }
        }
        return res;
    }
}
