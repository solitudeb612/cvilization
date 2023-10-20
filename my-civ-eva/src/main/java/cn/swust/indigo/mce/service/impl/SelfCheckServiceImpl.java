package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.po.CheckReport;
import cn.swust.indigo.mce.mapper.CheckReportMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.SelfCheck;
import cn.swust.indigo.mce.mapper.SelfCheckMapper;
import cn.swust.indigo.mce.service.SelfCheckService;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

/**
 * 自查通知
 *
 * @author lhz
 * @date 2023-03-18 10:14:00
 */
@Service
@AllArgsConstructor
public class SelfCheckServiceImpl extends ServiceImpl<SelfCheckMapper, SelfCheck> implements SelfCheckService {
    SelfCheckMapper selfCheckMapper;
    CheckReportMapper checkReportMapper;

    @Override
    public boolean updateState(Integer guideId, Integer status) {
        return selfCheckMapper.updateStatus(guideId, status);
    }

    @Override
    public SelfCheck calculateNum(SelfCheck selfCheck) {
        QueryWrapper<CheckReport> checkReportQueryWrapper = new QueryWrapper<>();
        checkReportQueryWrapper.eq("check_id",selfCheck.getCheckId());
        checkReportQueryWrapper.eq("status",1);
        int commitNum = checkReportMapper.selectCount(checkReportQueryWrapper);
        checkReportQueryWrapper = new QueryWrapper<CheckReport>().eq("status",0).eq("check_id",selfCheck.getCheckId());
        int disCommitNum = checkReportMapper.selectCount(checkReportQueryWrapper);
        selfCheck.setCommitNum(commitNum);
        selfCheck.setDisCommitNum(disCommitNum);
        selfCheckMapper.updateById(selfCheck);
        return selfCheck;
    }
}
