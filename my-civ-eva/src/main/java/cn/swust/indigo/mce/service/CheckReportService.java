package cn.swust.indigo.mce.service;

import cn.hutool.core.lang.Pair;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;
import cn.swust.indigo.mce.entity.vo.CheckCommitAllInfo;
import cn.swust.indigo.mce.entity.vo.CheckDept;
import cn.swust.indigo.mce.entity.vo.CheckSite;
import cn.swust.indigo.mce.entity.vo.QCheckReport;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.CheckReport;
//import javafx.util.Pair;

import java.util.List;

/**
 * 自查报告
 *
 * @author lhz
 * @date 2023-03-18 10:24:27
 */
public interface CheckReportService extends IService<CheckReport> {

    List<CheckCommitAllInfo> getCommitById(Integer checkReportId);

    List<CheckCommitAllInfo> commitAllInfoList(CheckReport checkReport);

    R commitReport(Integer checkReportId);

    IPage<CheckReport> pageAllInfo(IPage<CheckReport> page, Wrapper<CheckReport> queryWrapper);
}
