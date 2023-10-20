package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.CommitSpotCheck;
import cn.swust.indigo.mce.entity.po.QCommitSpotCheck;
import cn.swust.indigo.mce.entity.vo.*;
import cn.swust.indigo.mce.service.CommitService;
import cn.swust.indigo.mce.service.CommitSpotCheckService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网上申报评阅
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/commit/review")
@Api(value = "commit-review", tags = "网上申报评阅")
public class CommitReviewController {

    private final CommitService commitService;

    @Autowired
    CommitSpotCheckService commitSpotCheckService;

    // 管理员操作

    /**
     * 分页查询
     *
     * @param page           分页对象
     * @param qAdminRuleTask 分页查询所有待评审信息
     * @return
     */
    @ApiOperation(value = "分页查询所有待评审信息-admin or 牵头部门 管理第一步查询 申报规则、部门、任务详情信息 ", notes = "分页查询所有待评审信息-admin 牵头部门需要选择自己管理的牵头部门来查看待评审信息")
    @GetMapping("/all")
    public R getAllDepartmentCommitByAdminPage(Page page, QAdminRuleTask qAdminRuleTask) {
        return R.ok(commitService.getAllDepartmentCommitByAdmin(qAdminRuleTask, page));
    }


    //admin操作
    @ApiOperation(value = "查询所有的牵头部门 - admin 第0步")
    @GetMapping("/dept")
    public R getLeadDepartment(@RequestParam Integer guideId) {
        return R.ok(commitService.getLeadDepartment(guideId));
    }

    @ApiOperation(value = "获取已经提交的报告 第二步 查看提交的申报详情")
    @GetMapping("/report")
    public R getCommitReportByTaskId(Integer taskId, Integer guideId) {
        Integer status = 1;
        return R.ok(commitService.getCommitReportByTaskId(taskId, guideId, status));
    }


    /**
     * 评阅网上申报信息
     *
     * @param
     * @return R
     */
    @ApiOperation(value = "评阅网上申报信息 第三步 牵头部门评阅此次申报", notes = "评阅网上申报信息")
//    @Log("新增项目提交内容" )
    @PutMapping
//    @PreAuthorize("hasAuthority('projectcommit_add')" )
    public R review(@RequestBody QCommitReview qCommitReview) {
        return commitService.reviewCommit(qCommitReview)? R.ok():R.failed("评阅失败");
    }

    /**
     * 抽查网上申报信息
     *
     * @param
     * @return R
     */
    @ApiOperation(value = "抽查网上申报信息-管理员-第四步抽查申报的内容", notes = "抽查网上申报信息")
    //    @Log("新增项目提交内容" )
    @PutMapping("/spot")
    //    @PreAuthorize("hasAuthority('projectcommit_add')" )
    public R spotReview(@RequestBody QCommitSpotCheck qCommitSpotCheck) {
        Integer spotId = commitService.spotReviewCommit(qCommitSpotCheck);
        if (ObjectUtil.isNotNull(spotId)) {
            return R.ok(spotId);
        } else {
            return R.ok("驳回成功");
        }
    }


    @GetMapping("/history")
    @ApiOperation(value = "获取历史评审消息", notes = "")
    public R reviewHistory(@RequestParam Integer reportId){
        LambdaQueryWrapper<CommitSpotCheck> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommitSpotCheck::getReportId, reportId)
                .orderByAsc(CommitSpotCheck::getReviewTime);
        return R.ok(commitSpotCheckService.list(wrapper));
    }
}
