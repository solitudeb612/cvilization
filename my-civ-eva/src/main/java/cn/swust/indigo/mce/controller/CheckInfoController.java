package cn.swust.indigo.mce.controller;

import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.CheckReport;
import cn.swust.indigo.mce.entity.vo.QCheckReport;
import cn.swust.indigo.mce.service.CheckReportService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 自查信息汇总
 *
 * @author lzn
 * @date 2023-04-20 10:24:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/check/Info")
@Api(value = "check_info", tags = "实地考察-自查信息汇总")
public class CheckInfoController {
    private final CheckReportService checkReportService;

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param checkReport 自查报告
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getCheckReportPage(Page<CheckReport> page, QCheckReport checkReport) {
        return R.ok(checkReportService.page(page, CreateQuery.getQueryWrapper(checkReport)));
    }

    /**
     * 通过id查询自查报告
     *
     * @param checkReportId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get")
    public R getById(@RequestParam("checkReportId") Integer checkReportId) {
        return R.ok(checkReportService.getCommitById(checkReportId));
    }

    /**
     * 新增自查报告
     *
     * @param checkReport 自查报告
     * @return R
     */
    @ApiOperation(value = "新增自查报告", notes = "新增自查报告")
//    @Log("新增自查报告" )
    @PostMapping
//    @PreAuthorize("hasAuthority('checkreport_add')" )
    public R save(@RequestBody CheckReport checkReport) {
        boolean flag = checkReportService.save(checkReport);
        return flag? R.ok(checkReport.getCheckReportId()) : R.failed("新增失败");
    }

    /**
     * 提交自查报告
     *
     * @param checkReportId 自查报告id
     * @return R
     */
    @ApiOperation(value = "提交自查报告", notes = "提交自查报告")
//    @Log("新增自查报告" )
    @PutMapping("/commit")
//    @PreAuthorize("hasAuthority('checkreport_add')" )
    //TODO 只有草稿状态才能提交
    public R commitReport(@RequestParam("checkReportId") Integer checkReportId) {
        return checkReportService.commitReport(checkReportId);
    }


    /**
     * 修改自查报告
     *
     * @param checkReport 自查报告
     * @return R
     */
    @ApiOperation(value = "修改自查报告", notes = "修改自查报告")
//    @Log("修改自查报告" )
    @PutMapping
//    @PreAuthorize("hasAuthority('checkreport_edit')" )
    public R updateById(@RequestBody CheckReport checkReport) {
        if (checkReport == null || checkReport.getStatus() != 0) {
            return R.failed("自查报告不存在或报告状态不为初稿");
        }
        return R.ok(checkReportService.updateById(checkReport));
    }


    /**
     * 通过id删除自查报告
     *
     * @param checkReportId
     * @return R
     */
    @ApiOperation(value = "通过id删除自查报告", notes = "通过id删除自查报告")
//    @Log("通过id删除自查报告" )
    @DeleteMapping
//    @PreAuthorize("hasAuthority('checkreport_del')" )
    public R removeById(@RequestParam("checkReportId") Integer checkReportId) {
        CheckReport checkReport = checkReportService.getById(checkReportId);
        if (checkReport == null || checkReport.getStatus() != 0) {
            return R.failed("自查报告不存在或报告状态不为初稿");
        }
        return R.ok(checkReportService.removeById(checkReportId));
    }
}
