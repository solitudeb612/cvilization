package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.po.CheckReport;
import cn.swust.indigo.mce.entity.vo.CheckCommitAllInfo;
import cn.swust.indigo.mce.entity.vo.QCheckCommit;
import cn.swust.indigo.mce.mapper.CheckReportMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.CheckCommit;
import cn.swust.indigo.mce.mapper.CheckCommitMapper;
import cn.swust.indigo.mce.service.CheckCommitService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

/**
 * 自查报告提交内容
 *
 * @author lhz
 * @date 2023-03-18 10:33:42
 */
@Service
@AllArgsConstructor
public class CheckCommitServiceImpl extends ServiceImpl<CheckCommitMapper, CheckCommit> implements CheckCommitService {
    private CheckCommitMapper checkCommitMapper;
    private CheckReportMapper checkReportMapper;

    @Override public Page<CheckCommitAllInfo> commitPage(Page page, QCheckCommit checkCommit) {
        return null;
    }

    @Override
    public boolean saveCommit(CheckCommit checkCommit) {

        LambdaQueryWrapper<CheckCommit> wrapper = new LambdaQueryWrapper<CheckCommit>()
                .eq(CheckCommit::getCheckReportId, checkCommit.getCheckReportId())
                .eq(CheckCommit::getCheckRuleId, checkCommit.getCheckRuleId());
        CheckCommit old = checkCommitMapper.selectOne(wrapper);
        if (old == null){
            // 如果先前不存在 CheckCommit
            if(this.save(checkCommit)){
                Integer isPassed = checkCommit.getIsPassed();
                CheckReport checkReport = checkReportMapper.selectById(checkCommit.getCheckReportId());
                if (isPassed == 0)
                    checkReport.setFailCount(checkReport.getFailCount() + 1);
                else
                    checkReport.setPassedCount(checkReport.getPassedCount() + 1);
                checkReportMapper.updateById(checkReport);
                return true;
            }
        }else {
            // 修改 report 通过和不通过数量
            CheckReport checkReport = checkReportMapper.selectById(checkCommit.getCheckReportId());
            if (old.getIsPassed() == 1 && checkCommit.getIsPassed() == 0){
                // 通过改为不通过
                checkReport.setFailCount(checkReport.getFailCount() + 1);
                checkReport.setPassedCount(checkReport.getPassedCount() - 1);
            }
            if (old.getIsPassed() == 0 && checkCommit.getIsPassed() == 1){
                // 不通过改为通过
                checkReport.setFailCount(checkReport.getFailCount() - 1);
                checkReport.setPassedCount(checkReport.getPassedCount() + 1);
            }
            checkReportMapper.updateById(checkReport);
            checkCommit.setCheckCommitId(old.getCheckCommitId());
            checkCommitMapper.updateById(checkCommit);
            return true;
        }
        return false;
    }
}
