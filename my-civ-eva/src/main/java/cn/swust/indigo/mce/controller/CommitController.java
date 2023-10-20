package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Page;
import cn.hutool.json.JSONObject;
import cn.swust.indigo.admin.entity.po.SysFile;
import cn.swust.indigo.admin.service.SysFileService;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.entity.vo.*;
import cn.swust.indigo.mce.service.*;
import cn.swust.indigo.mce.utils.CommitReportFailedType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目提交内容
 *
 * @author lhz
 * @date 2023-02-24 18:03:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/commit")
@Api(value = "commit", tags = "网上申报--提交详情--管理")
public class CommitController {

    protected static class CommitTemporaryId {
        Integer commitId;

        public Integer getCommitId() {
            return commitId;
        }
    }


    private final CommitService commitService;

    @Autowired
    RuleDeptTaskService ruleDeptTaskService;

    @Autowired
    TaskCommitService taskCommitService;

    @Autowired
    CommitReportService commitReportService;

    @Autowired
    ICommitImgStarService commitImgStarService;

    @Autowired
    CommitSpotCheckService commitSpotCheckService;

    @Autowired
    private GuideService guideService;

    @Autowired
    SysFileService sysFileService;

    @ApiOperation(value = "查询网上申报任务提交项目 第一步查看任务", notes = "必填guideId，非则无法查询;")
    @GetMapping("/rule")
    public R getCommitPage(QCommitDetail qCommitDetail) {
//        return R.ok(commitService.getCommitList(qCommitDetailPage));
        return R.ok(commitService.listCommitRuleTableInfo(qCommitDetail));
    }

    @ApiOperation(value = "查询此次提交需要的条目详情 第二步查看任务详情", notes = "查看任务的详情！比如需要提交什么类型的文件、图片、是否必须提交等")
    @GetMapping("/task")
    public R getCommitTask(@RequestParam Integer ruleId, @RequestParam Integer departmentId, @RequestParam Integer guideId) {
        Guide guide = guideService.getById(guideId);
        LambdaQueryWrapper<RuleDeptTask> ruleDeptTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ruleDeptTaskLambdaQueryWrapper.eq(RuleDeptTask::getRuleId, ruleId).eq(RuleDeptTask::getDepartmentId, departmentId).eq(RuleDeptTask::getRootRuleId, guide.getRootRuleId());
        RuleDeptTask ruleDeptTask = ruleDeptTaskService.getOne(ruleDeptTaskLambdaQueryWrapper);

        if (ObjectUtil.isNull(ruleDeptTask)) {
            return R.failed("未能查询到关联的任务！请检查提交内容！");
        } else {
            LambdaQueryWrapper<TaskCommit> taskCommitLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskCommitLambdaQueryWrapper.eq(TaskCommit::getTaskId, ruleDeptTask.getTaskId());
            List<TaskCommit> taskCommits = taskCommitService.list(taskCommitLambdaQueryWrapper);
            if (ObjectUtil.isNull(taskCommits) || taskCommits.isEmpty()) {
                return R.failed("不能查找到关联的任务详情！请查看详情是否正确更新！");
            } else {
                return R.ok(commitService.getTaskAndDetail(ruleId, departmentId, guideId, taskCommits));
            }
        }
//        return R.ok(commitService.getTaskCommitReportByTaskId(ruleId,departmentId,guideId,null));
    }

    @ApiOperation(value = "新增项目内容 第三步 1.保存项目（此时为草稿状态）", notes = "必须填写guide id、rule id和department id")
//    @Log("新增项目提交内容" )
    @PutMapping("save")
//    @PreAuthorize("hasAuthority('projectcommit_add')" )
    public R save(@RequestBody CommitReport commitReport) {
        LambdaQueryWrapper<CommitReport> commitReportLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commitReportLambdaQueryWrapper.eq(CommitReport::getRuleId, commitReport.getRuleId());
        commitReportLambdaQueryWrapper.eq(CommitReport::getGuideId, commitReport.getGuideId());
        commitReportLambdaQueryWrapper.eq(CommitReport::getDepartmentId, commitReport.getDepartmentId());
        List<CommitReport> list = commitReportService.list(commitReportLambdaQueryWrapper);

        if (ObjectUtil.isNull(list) || list.isEmpty()) {
            commitReportService.save(commitReport);
            JSONObject res = new JSONObject();
            res.set("reportId", commitReport.getReportId());
            return R.ok(res);
        } else {
            return R.failed("已经存在相同内容的提交！无法保存！");
        }
    }

    @ApiOperation(value = "新增（修改）项目提交内容条目 第三步 2.新增项目条目", notes = "新增项目提交内容")
    @PutMapping("/add")
    public R addCommitDetail(@RequestBody CommitDetail commitDetail) {
        if (commitReportService.checkStatusCommit(commitDetail.getReportId())) {
            // 已经提交
            return R.failed("当前提交的申请不能新增，原因：已提交、或通过不能新增、修改");
        } else {
            commitService.saveOrUpdate(commitDetail);
            return R.ok(commitDetail.getCommitId());
        }
    }

    @ApiOperation(value = "修改项目提交内容条目 第三步 3.修改项目条目", notes = "修改项目提交内容")
//    @Log("修改项目提交内容" )
    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('projectcommit_edit')" )
    public R updateById(@RequestBody CommitDetail commitDetail) {
        if (commitReportService.checkStatusCommit(commitDetail.getReportId())) {
            // 已经提交
            return R.failed("当前提交的申请不能修改，原因：已提交、或通过不能修改");
        } else {
            commitService.updateById(commitDetail);
            return R.ok();
        }
    }

    @ApiOperation(value = "清空项目提交内容的文件", notes = "清空项目提交内容的文件")
//    @Log("修改项目提交内容" )
    @PutMapping("/clear")
//    @PreAuthorize("hasAuthority('projectcommit_edit')" )
    public R updateById(@RequestBody CommitTemporaryId commitTemporaryId) {
        if (commitService.clearFileIds(commitTemporaryId.getCommitId())) {
            // 已经提交
            return R.ok("文件清空成功");
        } else {
            return R.failed();
        }
    }

    @ApiOperation(value = "通过id删除项目提交内容 第三步 4.删除项目条目", notes = "通过id删除项目提交内容")
//    @Log("通过id删除项目提交内容" )
    @DeleteMapping("/delete")
//    @PreAuthorize("hasAuthority('projectcommit_del')" )
    public R removeById(@RequestParam("commitInfoId") Integer commitDetailId) {
        if (commitReportService.checkStatusByDetailId(commitDetailId)) {
            // 已经提交
            return R.failed("当前提交的申请不能删除，原因：状态为已经提交！");
        } else {
            commitService.removeById(commitDetailId);
            return R.ok();
        }
    }

    @ApiOperation(value = "提交项目内容 第四步 提交项目（此时为提交状态，必须、类型检测）", notes = "此次提交的状态从0（草稿）变成1（提交），同时检测提交是否符合要求")
    @PutMapping("/submit")
//    @PreAuthorize("hasAuthority('projectcommit_add')" )
    public R submit(@RequestParam Integer commitReportId) {
        CommitReport byId = commitReportService.getById(commitReportId);
        if (ObjectUtil.isNull(byId)) {
            return R.failed("无法提交！不存在此内容！");
        } else {
            if (commitReportService.checkStatusCommit(commitReportId)) {
                return R.failed("此内容已经提交，无法重复提交");
            } else {
                //检测项目是否符合要求
                Integer flag = commitReportService.checkSubmit(commitReportId);
                if (Objects.equals(flag, CommitReportFailedType.ERROR_FILE_TYPE)) {
                    return R.failed("提交的申请中有的条目文件的类型不同，请修改！");
                } else if (Objects.equals(flag, CommitReportFailedType.MISSING_NECESSARY_DOCUMENTS)) {
                    return R.failed("提交的申请中缺少必要的条目，请检查是否满足要求！");
                } else if (Objects.equals(flag, CommitReportFailedType.SUBMIT_SUCCESS)) {
                    commitService.saveImagesIdsByReportId(commitReportId);
                    return R.ok("状态修改成功！");
                } else if (Objects.equals(flag, CommitReportFailedType.BEFORE_BEGIN_TIME)) {
                    return R.failed("当前状态无法保存！原因：任务网上申报未开始！");
                } else if (Objects.equals(flag, CommitReportFailedType.AFTER_END_TIME)) {
                    return R.failed("当前状态无法保存！原因：任务网上申报已经结束！");
                } else {
                    return R.failed("unknown error");
                }
            }
        }
    }

    @ApiOperation(value = "查询网上申报历史申报项 第五步 查看提交（弃用）", notes = "请保证ruleId和departmentId存在")
    @GetMapping("/report")
    @Deprecated
    public R getCommitReport(QCommitReport commit) {
//        List<CommitReport> commitReportList = commitService.getCommitReportList(commit);
        LambdaQueryWrapper<CommitReport> commitReportLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commitReportLambdaQueryWrapper.eq(CommitReport::getRuleId, commit.getRuleId()).eq(CommitReport::getDepartmentId, commit.getDepartmentId());
        List<CommitReport> commitReportList = commitReportService.list(commitReportLambdaQueryWrapper);

        if (ObjectUtil.isNotNull(commitReportList) || commitReportList.isEmpty()) {
            return R.ok(commitReportList);
        } else {
            return R.failed("找不到历史申请结果，可能是参数问题或者不存在！");
        }
    }


    @ApiOperation(value = "查询某一次的申请详情条目 第六步 查看提交详情", notes = "需要提供一个reportId可以从 第一步接口中获得")
    @GetMapping("/detail")
    public R getCommitReport(@RequestParam Integer reportId) {
        LambdaQueryWrapper<CommitDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommitDetail::getReportId, reportId);
        List<CommitDetail> list = commitService.list(queryWrapper);
        CommitReport byId = commitReportService.getById(reportId);

        CommitReportAndDetail commitReportAndDetail = new CommitReportAndDetail();
        commitReportAndDetail.setCommitDetailList(list);
        BeanUtils.copyProperties(byId, commitReportAndDetail);
        // 抽查
        LambdaQueryWrapper<CommitSpotCheck> spotQueryWrapper = new LambdaQueryWrapper<>();
        spotQueryWrapper.eq(CommitSpotCheck::getReportId, reportId);
        spotQueryWrapper.orderByDesc(CommitSpotCheck::getReviewTime);
        commitReportAndDetail.setSpotChecksList(commitSpotCheckService.list(spotQueryWrapper));
        // 设置加星
        LambdaQueryWrapper<CommitImgStar> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommitImgStar::getReportId, reportId);
        commitReportAndDetail.setCommitImgStarList(commitImgStarService.list(wrapper));

        if (ObjectUtil.isNotNull(list) || list.isEmpty()) {
            return R.ok(commitReportAndDetail);
        } else {
            return R.failed("找不到历史申请详情结果，可能是参数问题或者不存在！");
        }
    }

    @DeleteMapping("/delete/report")
    @ApiOperation(value = "删除一个申请项目 （仅供测试用）", notes = "")
    public R deleteReport(@RequestParam Integer reportId) {
        LambdaQueryWrapper<CommitDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommitDetail::getReportId, reportId);
        commitService.remove(queryWrapper);
        commitReportService.removeById(reportId);
        return R.ok();
    }


    @ApiOperation(value = "获取提交的图片（或星标图片）的文件ID集合", notes = "如果前面两个条件为空则获取所有图片、isStar默认为false则获取全部图片，true则为只获取加星")
    @GetMapping("/img")
    public R getStarImageFileIds(@RequestParam(required = false) Integer guideId, @RequestParam(required = false) Integer ruleId, @RequestParam(required = false, defaultValue = "false") Boolean isStar) {
        List<Integer> starImageFileIds = commitService.getStarImageFileIds(guideId, ruleId, isStar);
        if (ObjectUtil.isNull(starImageFileIds) || starImageFileIds.isEmpty()) {
            return R.failed("未能查到数据！");
        } else {
            return R.ok(starImageFileIds);
        }
    }

    // put 单独的 设置星标
    @PutMapping("/star/image")
    @ApiOperation(value = "通过单独的图片设置星标图（一个）")
    public R putStarImage(@RequestBody CommitImgStar commitImgStar) {
        commitImgStarService.saveOrUpdate(commitImgStar);
        return R.ok();
    }

    @GetMapping("/images")
    @ApiOperation(value = "获取上传的图片", notes = "图片获取来源已经提交的网上申报中的图片内容，不包括草稿中的图片，所有参数均可为空")
    public R getStarImages(@RequestParam(required = false) Integer guideId,
                           @RequestParam(required = false) Integer reportId,
                           @RequestParam(required = false) Integer ruleId,
                           @RequestParam(required = false) Integer commitDetailId,
                           @RequestParam(required = false) Integer isStar,
                           IPage page) {
        IPage<CommitImgStar> commitImgStars = commitImgStarService.imagesList(guideId, ruleId, reportId, commitDetailId, isStar, page);
        if (ObjectUtil.isNull(commitImgStars)) {
            return R.failed("未能找到图片");
        } else {
            return R.ok(commitImgStars);
        }
    }

    @GetMapping("/download/")
    @ApiModelProperty(value = "根据文件ids下载文件")
    public void downloadFiles(@RequestParam List<Integer> fileIds, HttpServletResponse httpServletResponse) {
        List<SysFile> sysFiles = new ArrayList<>(sysFileService.listByIds(fileIds));
        try {
            sysFileService.downloadStarFiles(sysFiles, httpServletResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download/images")
    @ApiModelProperty(value = "根据start image ids下载文件")
    public void downloadStarImagesByIds(@RequestParam List<Integer> ids, HttpServletResponse httpServletResponse) {
        Collection<CommitImgStar> commitImgStars = commitImgStarService.listByIds(ids);
        List<Integer> fileIds = commitImgStars.stream().map(CommitImgStar::getFileId).collect(Collectors.toList());
        List<SysFile> sysFiles = new ArrayList<>(sysFileService.listByIds(fileIds));
        try {
            sysFileService.downloadStarFiles(sysFiles, httpServletResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

