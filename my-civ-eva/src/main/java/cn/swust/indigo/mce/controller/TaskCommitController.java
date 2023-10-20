package cn.swust.indigo.mce.controller;

import cn.swust.indigo.mce.entity.po.BatchSaveTaskCommit;
import cn.swust.indigo.mce.service.GuideService;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.TaskCommit;
import cn.swust.indigo.mce.service.TaskCommitService;
import cn.swust.indigo.mce.entity.vo.QTaskCommit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 网上申报任务提交要求管理
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task/commit")
@Api(value = "task-commit", tags = "网上申报任务提交要求管理")
public class TaskCommitController {

    private final TaskCommitService taskCommitService;
    private final RuleDeptTaskService ruleDeptTaskService;
    private final GuideService guideService;

    /**
     * 分页查询网上申报任务提交项
     *
     * @param page       分页对象
     * @param taskCommit 网上申报任务提交项
     * @return
     */
    @ApiOperation(value = "分页查询网上申报任务提交项", notes = "分页查询网上申报任务提交项")
    @GetMapping("/page")
    public R getTaskCommitPage(Page<TaskCommit> page, QTaskCommit taskCommit) {
        return R.ok(taskCommitService.page(page, CreateQuery.getQueryWrapper(taskCommit)));
    }

    /**
     * 通过id查询网上申报任务提交项
     *
     * @param taskCommitId id
     * @return R
     */
    @ApiOperation(value = "通过id查询网上申报任务提交项", notes = "通过id查询网上申报任务提交项")
    @GetMapping("/get")
    public R getById(@RequestParam("taskCommitId") Integer taskCommitId) {
        return R.ok(taskCommitService.getById(taskCommitId));
    }

    /**
     * 新增网上申报任务提交项
     *
     * @param taskCommit 网上申报任务提交项
     * @return R
     */
    @ApiOperation(value = "新增在线申报提交要求", notes = "新增在线申报提交要求")
//    @Log("新增在线申报提交要求")
    @PostMapping
//    @PreAuthorize("hasAuthority('taskcommit_add')")
    public R save(@RequestBody TaskCommit taskCommit) {
        Integer rootRuleId = ruleDeptTaskService.getById(taskCommit.getTaskId()).getRootRuleId();
        if(guideService.checkUseGuideCountGeTwo(rootRuleId)){
            return R.failed("该规则已被使用两次及以上，无法修改");
        }
        boolean flag = taskCommitService.save(taskCommit);
        return flag? R.ok(taskCommit.getTaskCommitId()) : R.failed("新增失败");
    }
    
    /**
     * 批量新增网上申报任务提交项
     *
     * @param taskCommit 网上申报任务提交项
     * @return R
     */
    @ApiOperation(value = "新增在线申报提交要求", notes = "新增在线申报提交要求")
//    @Log("新增在线申报提交要求")
    @PostMapping("/batch")
//    @PreAuthorize("hasAuthority('taskcommit_add')")
    public R saveBatch(@RequestBody BatchSaveTaskCommit taskCommit) {
        if(guideService.checkUseGuideCountGeTwo(taskCommit.getRootRuleId())){
            return R.failed("该规则已被使用两次及以上，无法修改");
        }
        return R.ok(taskCommitService.rootRuleBatchSave(taskCommit));
    }

    /**
     * 修改网上申报任务提交项
     *
     * @param taskCommit 网上申报任务提交项
     * @return R
     */
    @ApiOperation(value = "修改网上申报任务提交项", notes = "修改网上申报任务提交项")
//    @Log("修改在线申报提交要求")
    @PutMapping
//    @PreAuthorize("hasAuthority('taskcommit_edit')")
    public R updateById(@RequestBody TaskCommit taskCommit) {
        Integer rootRuleId = ruleDeptTaskService.getById(taskCommit.getTaskId()).getRootRuleId();
        if(guideService.checkUseGuideCountGeTwo(rootRuleId)){
            return R.failed("该规则已被使用两次及以上，无法修改");
        }
        return R.ok(taskCommitService.updateById(taskCommit));
    }


    /**
     * 通过id删除网上申报任务提交项
     *
     * @param taskCommitId
     * @return R
     */
    @ApiOperation(value = "通过id网上申报任务提交项", notes = "通过id网上申报任务提交项")
//    @Log("通过id删除在线申报提交要求")
    @DeleteMapping("/{taskCommitId}")
//    @PreAuthorize("hasAuthority('taskcommit_del')")
    public R removeById(@PathVariable Integer taskCommitId) {
        return R.ok(taskCommitService.removeById(taskCommitId));
    }

}
