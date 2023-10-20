package cn.swust.indigo.mce.controller;

import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.mapper.CommitReportMapper;
import cn.swust.indigo.mce.service.DataOverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Binary cat
 * @date 2023/4/22 12:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/overview")
@Api(value = "overview", tags = "数据概览获取接口")
public class DataOverviewController {

    @Autowired
    DataOverviewService dataOverviewService;

    @Autowired
    CommitReportMapper commitReportMapper;

    @ApiOperation(value = "Data Broad 信息面板数据")
    @GetMapping("/all")
    public R getData(){
        return R.ok(dataOverviewService.getDataVo());
    }

    @GetMapping("/guide/month")
    @ApiOperation(value = "近一年内提交的Guide数量、发布总数", notes = "近一年内提交的Guide数量、发布总数")
    public R getGuideMonthCount(){
        return R.ok(dataOverviewService.getGuideMonthCount());
    }

    @GetMapping("/guide/year")
    @ApiOperation(value = "统计每年提交的Guide总数、发布总数", notes = "统计每年提交的Guide总数、发布总数")
    public R getGuideYearCount(){
       return R.ok(dataOverviewService.getGuideYearCount());
    }

    @GetMapping("/report/month")
    @ApiOperation(value = "近一年内提交的REPORT数量", notes = "近一年内提交的Guide数量、发布总数")
    public R getReportYearCount(){
        return R.ok(commitReportMapper.getYearTimeAndCount());
    }

    @GetMapping("/report/total")
    @ApiOperation(value = "REPORT 总数量")
    public R getReportTotalCount(){
        return R.ok(commitReportMapper.getTotalReportCount());
    }
}
