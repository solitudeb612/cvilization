package cn.swust.indigo.mce.controller;

import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.vo.TaskCheckCommit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.SelfCheck;
import cn.swust.indigo.mce.service.SelfCheckService;
import cn.swust.indigo.mce.entity.vo.QSelfCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 自查通知
 *
 * @author lhz
 * @date 2023-03-18 10:14:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/check")
@Api(value = "self_check", tags = "实地考察-活动管理")
public class SelfCheckController {
    private final SelfCheckService selfCheckService;

    /**
     * 分页查询
     *
     * @param selfCheck 自查通知
     * @return
     */
    @ApiOperation(value = "分页查询-admin", notes = "分页查询")
    @GetMapping("/list")
    public R getSelfCheckList(QSelfCheck selfCheck) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(selfCheck);
        queryWrapper.orderByDesc("check_id");
        if(selfCheck.getYear()!=null) {
            queryWrapper.eq("year(create_time)", selfCheck.getYear());
        }
        return selfCheckService.list(queryWrapper).size()>0? R.ok(selfCheckService.list(queryWrapper)): R.failed(null);
    }

    /**
     * 分页查询
     *
     * @param selfCheck 自查通知
     * @return
     */
    @ApiOperation(value = "分页查询-user", notes = "分页查询")
    @GetMapping("/user/list")
    public R getUserSelfCheckList(QSelfCheck selfCheck) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(selfCheck);
        queryWrapper.ne("status",0);
        queryWrapper.orderByDesc("check_id");
        if(selfCheck.getYear()!=null) {
            queryWrapper.eq("year(create_time)", selfCheck.getYear());
        }
        List<SelfCheck> list = selfCheckService.list(queryWrapper);
        for(int i=0;i<list.size();i++){
            SelfCheck selfCheck1 = selfCheckService.calculateNum(list.get(i));
            list.set(i,selfCheck1);
        }
        return R.ok(list);
//        return selfCheckService.list(queryWrapper).size()>0? R.ok(selfCheckService.list(queryWrapper)): R.failed(null);
    }

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param selfCheck 自查通知
     * @return
     */
    @ApiOperation(value = "分页查询-admin", notes = "分页查询")
    @GetMapping("/page")
    public R getSelfCheckPage(Page<SelfCheck> page, QSelfCheck selfCheck) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(selfCheck);
        queryWrapper.orderByDesc("check_id");
        if(selfCheck.getYear()!=null) {
            queryWrapper.eq("year(create_time)", selfCheck.getYear());
        }
        return selfCheckService.page(page, queryWrapper).getSize()>0? R.ok(selfCheckService.page(page, queryWrapper)): R.failed(null);
    }

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param selfCheck 自查通知
     * @return
     */
    @ApiOperation(value = "分页查询-user", notes = "分页查询")
    @GetMapping("/user/page")
    public R getUserSelfCheckPage(Page<SelfCheck> page, QSelfCheck selfCheck) {
        QueryWrapper queryWrapper = CreateQuery.getQueryWrapper(selfCheck);
        queryWrapper.ne("status",0);
        queryWrapper.orderByDesc("check_id");
        if(selfCheck.getYear()!=null) {
            queryWrapper.eq("year(create_time)", selfCheck.getYear());
        }
        return selfCheckService.page(page, queryWrapper).getSize()>0? R.ok(selfCheckService.page(page, queryWrapper)): R.failed(null);
    }

    /**
     * 通过id查询自查通知
     * @param checkId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get")
    public R getById(@RequestParam("checkId") Integer checkId) {
        return R.ok(selfCheckService.getById(checkId));
    }

    /**
     * 新增自查通知
     *
     * @param selfCheck 自查通知
     * @return R
     */
    @ApiOperation(value = "新增自查通知", notes = "新增自查通知")
    @Log("新增自查通知")
    @PostMapping
//    @PreAuthorize("hasAuthority('selfcheck_add')" )
    public R save(@RequestBody SelfCheck selfCheck) {
        return R.ok(selfCheckService.save(selfCheck));
    }

    /**
     * 修改自查通知
     *
     * @param selfCheck 自查通知
     * @return R
     */
    @ApiOperation(value = "修改自查通知", notes = "修改自查通知")
    @Log("修改自查通知")
    @PutMapping
//    @PreAuthorize("hasAuthority('selfcheck_edit')" )
    public R updateById(@RequestBody SelfCheck selfCheck) {
        return R.ok(selfCheckService.updateById(selfCheck));
    }


    /**
     * 通过id删除自查通知
     *
     * @param checkId
     * @return R
     */
    @ApiOperation(value = "通过id删除自查通知", notes = "通过id删除自查通知")
    @Log("通过id删除自查通知")
    @DeleteMapping
//    @PreAuthorize("hasAuthority('selfcheck_del')" )
    public R removeById(@RequestParam("checkId") Integer checkId) {
        return R.ok(selfCheckService.removeById(checkId));
    }

    /**
     * 发布活动
     * @param checkId 活动id
     * @return R
     */
    @ApiOperation(value = "发布实地考察活动")
    @Log("发布网上申报指南")
    @PutMapping("/pub")
//    @PreAuthorize("hasAuthority('guide_edit')")
    public R pubSelfCheck(@RequestParam Integer checkId) {
        SelfCheck selfCheck = selfCheckService.getById(checkId);
        if(selfCheck==null || selfCheck.getStatus()!=0){
            return R.failed("活动不存在或活动状态不为草稿");
        }
        return R.ok(selfCheckService.updateState(checkId, 1));
    }

    /**
     * 撤销发布指南
     * @param checkId 活动id
     * @return R
     */
    @ApiOperation(value = "撤销发布实地考察活动", notes = "撤销发布实地考察活动")
    @Log("撤销发布实地考察活动")
    @PutMapping("/draft")
//    @PreAuthorize("hasAuthority('guide_edit')")
    public R unPubSelfCheck(@RequestParam Integer checkId) {
        SelfCheck selfCheck = selfCheckService.getById(checkId);
        if(selfCheck == null || selfCheck.getStatus() == 0){
            return R.failed("活动不存在或活动状态已经是草稿");
        }
        return R.ok(selfCheckService.updateState(checkId, 0));
    }


}
