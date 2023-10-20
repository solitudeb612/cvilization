package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.entity.vo.CheckCommitAllInfo;
import cn.swust.indigo.mce.entity.vo.CheckDept;
import cn.swust.indigo.mce.entity.vo.CheckSite;
import cn.swust.indigo.mce.entity.vo.QCheckReport;
import cn.swust.indigo.mce.mapper.*;
import cn.swust.indigo.mce.service.CheckGroupService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.service.CheckReportService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自查报告
 *
 * @author lhz
 * @date 2023-03-18 10:24:27
 */
@Service
@AllArgsConstructor
public class CheckReportServiceImpl extends ServiceImpl<CheckReportMapper, CheckReport> implements CheckReportService {

    private CheckProblemMapper checkProblemMapper;

    private CheckReportMapper checkReportMapper;
    private CheckCommitMapper checkCommitMapper;
    private CheckGroupRuleMapper checkGroupRuleMapper;
    private SysDeptMapper sysDeptMapper;

    private CheckGroupMapper checkGroupMapper;

    @Override
    public List<CheckCommitAllInfo> getCommitById(Integer checkReportId){
        CheckReport checkReport = checkReportMapper.selectById(checkReportId);
        Integer checkGroupId = checkReport.getCheckGroupId();
        List<CheckGroupRule> checkGroupRules = checkGroupRuleMapper.selectList(new QueryWrapper<CheckGroupRule>().eq("group_id",checkGroupId));
        List<CheckCommitAllInfo> checkCommitAllInfoList = new ArrayList<>();
        for(CheckGroupRule checkGroupRule : checkGroupRules){
            QueryWrapper<CheckCommit> checkCommitQueryWrapper = new QueryWrapper<CheckCommit>().eq("check_report_id", checkReportId);
            checkCommitQueryWrapper.eq("check_rule_id",checkGroupRule.getGroupRuleId());
            CheckCommit checkCommit = checkCommitMapper.selectOne(checkCommitQueryWrapper);
            if(checkCommit == null){
                checkCommitAllInfoList.add(new CheckCommitAllInfo(checkGroupRule,null,false));
            }else{
                CheckCommitAllInfo temp = new CheckCommitAllInfo(checkGroupRule, checkCommit, true);
                // 往每一个COMMIT里面如果有problem就加入,如果存在多个问题那么就选择一个问题 wangxiande
                CheckProblem checkProblem = checkProblemMapper.selectOne(new LambdaQueryWrapper<CheckProblem>()
                        .eq(CheckProblem::getCheckCommitId, checkCommit.getCheckCommitId())
                        .last(" limit 1"));
                if (ObjectUtil.isNotNull(checkProblem)){
                    temp.setCheckProblem(checkProblem);
                }
                checkCommitAllInfoList.add(temp);
            }
        }
        return checkCommitAllInfoList;
    }

    @Override
    public List<CheckCommitAllInfo> commitAllInfoList(CheckReport checkReport) {
        this.save(checkReport);
        Integer checkGroupId = checkReport.getCheckGroupId();
        List<CheckGroupRule> checkGroupRules = checkGroupRuleMapper.selectList(new QueryWrapper<CheckGroupRule>().eq("group_id",checkGroupId));
        List<CheckCommitAllInfo> checkCommitAllInfoList = new ArrayList<CheckCommitAllInfo>();
        for(CheckGroupRule checkGroupRule : checkGroupRules){
            CheckCommitAllInfo checkCommitAllInfo = new CheckCommitAllInfo(checkGroupRule,null,false);
            checkCommitAllInfoList.add(checkCommitAllInfo);
        }
        return checkCommitAllInfoList;
    }

    @Override
    public R commitReport(Integer checkReportId) {
        List<CheckCommitAllInfo> commitById = this.getCommitById(checkReportId);
        for(CheckCommitAllInfo checkCommitAllInfo : commitById){
            if(!checkCommitAllInfo.isCommitFlag()){
                return R.failed("必须填写所有要求规则后才能提交");
            }
        }
        CheckReport checkReport = checkReportMapper.selectById(checkReportId);
        if(checkReport.getStatus() != 0){
            return R.failed("只有草稿状态才能提交");
        }
        checkReport.setStatus(1);
        checkReport.setCommitTime(LocalDateTime.now());
        checkReportMapper.updateById(checkReport);
        return R.ok();
    }

    @Override
    public IPage<CheckReport> pageAllInfo(IPage<CheckReport> page, Wrapper<CheckReport> queryWrapper) {
        IPage<CheckReport> nowPage = this.page(page, queryWrapper);
        if (nowPage.getRecords() == null || nowPage.getRecords().isEmpty()){
            return nowPage;
        }
        List<Integer> groupIds = nowPage.getRecords().stream().map(CheckReport::getCheckGroupId).collect(Collectors.toList());
        List<CheckGroup> checkGroups = checkGroupMapper.selectBatchIds(groupIds);
        Map<Integer, String> map =
                checkGroups.stream().collect(Collectors.toMap(CheckGroup::getCheckGroupId, CheckGroup::getGroupName));

        for (CheckReport checkReport : nowPage.getRecords()) {
            checkReport.setCheckGroupName(map.get(checkReport.getCheckGroupId()));
        }
        return nowPage;
    }

}
