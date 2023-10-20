package cn.swust.indigo.mce.controller;

import cn.swust.indigo.mce.entity.vo.TaskCheckCommit;
import cn.swust.indigo.mce.mapper.RuleDeptTaskMapper;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.service.GuideService;
import cn.swust.indigo.mce.entity.vo.QGuide;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 网上申报指南管理
 *
 * @author lhz
 * @date 2023-02-24 16:57:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/guide")
@Api(value = "guide", tags = "网上申报任务管理")
public class GuideController{
    private final GuideService guideService;
    private RuleDeptTaskService ruleDeptTaskService;

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param guide 指南
     * @return
     */
    @ApiOperation(value = "分页查询网上申报指南-admin", notes = "分页查询网上申报指南-admin")
    @GetMapping("/page")
    public R getGuidePage(Page<Guide> page, QGuide guide) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(guide);
        queryWrapper.orderByDesc("guide_id");
        if(guide.getYear()!=null) {
            queryWrapper.eq("year(create_time)", guide.getYear());
        }
        return guideService.page(page, queryWrapper).getSize()>0? R.ok(guideService.page(page, queryWrapper)):R.ok(null);
    }

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param guide 指南
     * @return
     */
    @ApiOperation(value = "分页查询网上申报指南-user", notes = "分页查询网上申报指南-user")
    @GetMapping("/user/page")
    public R getUserGuidePage(Page<Guide> page, QGuide guide) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(guide);
        queryWrapper.ne("status",0);
        queryWrapper.orderByDesc("guide_id");
        if(guide.getYear()!=null) {
            queryWrapper.eq("year(create_time)", guide.getYear());
        }
        return guideService.page(page, queryWrapper).getSize()>0? R.ok(guideService.page(page, queryWrapper)):R.ok(null);
    }

    /**
     * 查询
     *
     * @param guide 指南
     * @return
     */
    @ApiOperation(value = "查询所有网上申报指南", notes = "查询所有网上申报指南")
    @GetMapping("/all")
    public R getAllGuide(QGuide guide) {
        return R.ok(guideService.list(CreateQuery.getQueryWrapper(guide)));
    }


    /**
     * 通过id查询指南
     *
     * @param guideId id
     * @return R
     */
    @ApiOperation(value = "通过id查询网上申报指南", notes = "通过id查询网上申报指南")
    @GetMapping("/get")
    public R getById(@RequestParam("guideId") Integer guideId) {
        return R.ok(guideService.getById(guideId));
    }

    /**
     * 新增指南
     *
     * @param guide 指南
     * @return R
     */
    @ApiOperation(value = "新增网上申报指南", notes = "新增网上申报指南")
    @Log("新增网上申报指南")
    @PostMapping
//    @PreAuthorize("hasAuthority('guide_add')")
    public R save(@RequestBody Guide guide) {
        boolean flag = guideService.save(guide);
        return flag? R.ok(guide.getGuideId()) : R.failed("新增失败");
    }

    /**
     * 修改指南
     *
     * @param guide 指南
     * @return R
     */
    @ApiOperation(value = "修改网上申报指南", notes = "修改网上申报指南")
    @Log("修改网上申报指南")
    @PutMapping
//    @PreAuthorize("hasAuthority('guide_edit')")
    public R updateById(@RequestBody Guide guide) {
        Guide myGuide = guideService.getById(guide.getGuideId());
        if(myGuide==null || myGuide.getStatus()!=0){
            return R.failed("指南不存在或指南状态不为初稿");
        }
        return R.ok(guideService.updateById(guide));
    }


    /**
     * 通过id删除指南
     *
     * @param guideId
     * @return R
     */
    @ApiOperation(value = "通过id删除网上申报指南", notes = "通过id删除网上申报指南")
    @Log("通过id删除网上申报指南")
    @DeleteMapping
//    @PreAuthorize("hasAuthority('guide_del')")
    public R removeById(@RequestParam("guideId") Integer guideId) {
        Guide myGuide = guideService.getById(guideId);
        if(myGuide==null || myGuide.getStatus()!=0){
            return R.failed("指南不存在或指南状态不为初稿");
        }
        return R.ok(guideService.removeById(guideId));
    }

    /**
     * 发布指南
     *
     * @param guideId 指南id
     * @param isMandatory  0为强制修改, 1为检查
     * @return R
     */
    @ApiOperation(value = "发布网上申报指南", notes = "发布网上申报指南, isMandatory: 0为强制修改, 1为检查")
    @Log("发布网上申报指南")
    @PutMapping("/pub")
//    @PreAuthorize("hasAuthority('guide_edit')")
    public R pubGuide(@RequestParam Integer guideId, @RequestParam("isMandatory") Integer isMandatory) {
        Guide myGuide = guideService.getById(guideId);
        Integer rootRuleId = myGuide.getRootRuleId();
        if(isMandatory == 1){
            List<TaskCheckCommit> taskCheckCommits = ruleDeptTaskService.selectTaskCommits(rootRuleId);
            List<TaskCheckCommit> taskCheckCommitList = new ArrayList<>();
            for(TaskCheckCommit taskCheckCommit : taskCheckCommits){
                if(taskCheckCommit.getCommitCount() == 0){
                    taskCheckCommitList.add(taskCheckCommit);
                }
            }
            if(taskCheckCommitList.size() != 0){
                return R.failed(taskCheckCommitList,"存在部门任务内没有任务提交详情");
            }
        }
        if(myGuide==null || myGuide.getStatus()!=0){
            return R.failed("指南不存在或指南状态不为草稿");
        }
        return R.ok(guideService.updateState(guideId, 1));
    }

    /**
     * 撤销发布指南
     *
     * @param guideId 指南id
     * @return R
     */
    @ApiOperation(value = "撤销发布网上申报指南", notes = "撤销发布网上申报指南")
    @Log("撤销发布网上申报指南")
    @PutMapping("/draft")
//    @PreAuthorize("hasAuthority('guide_edit')")
    public R unPubGuide(@RequestParam Integer guideId) {
        Guide myGuide = guideService.getById(guideId);
        if(myGuide == null || myGuide.getStatus() == 0){
            return R.failed("指南不存在或指南状态已经是草稿");
        }
        return R.ok(guideService.updateState(guideId, 0));
    }

}
