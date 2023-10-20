package cn.swust.indigo.mce.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.CheckCommit;
import cn.swust.indigo.mce.service.CheckCommitService;
import cn.swust.indigo.mce.entity.vo.QCheckCommit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 自查报告提交内容
 *
 * @author lhz
 * @date 2023-03-18 10:33:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/check/commit")
@Api(value = "check_commit", tags = "实地考察-自查报告提交内容管理")
public class CheckCommitController {

    private final CheckCommitService checkCommitService;

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param checkCommit 自查报告提交内容
     * @return
     */
    @Deprecated
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getCheckCommitPage(Page page, QCheckCommit checkCommit) {
        return R.ok(checkCommitService.commitPage(page, checkCommit));
    }

    /**
     * 通过id查询自查报告提交内容
     *
     * @param checkCommitId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get")
    public R getById(@RequestParam("checkCommitId") Integer checkCommitId) {
        return R.ok(checkCommitService.getById(checkCommitId));
    }

    /**
     * 新增自查报告提交内容
     *
     * @param checkCommit 自查报告提交内容
     * @return R
     */
    @ApiOperation(value = "新增自查报告提交内容", notes = "新增自查报告提交内容")
//    @Log("新增自查报告提交内容" )
    @PostMapping
//    @PreAuthorize("hasAuthority('checkcommit_add')" )
    public R save(@RequestBody CheckCommit checkCommit) {
        return checkCommitService.saveCommit(checkCommit)? R.ok(checkCommit.getCheckCommitId()):R.failed("操作成功");
    }

    /**
     * 修改自查报告提交内容
     *
     * @param checkCommit 自查报告提交内容
     * @return R
     */
    @ApiOperation(value = "修改自查报告提交内容", notes = "修改自查报告提交内容")
//    @Log("修改自查报告提交内容" )
    @PutMapping
//    @PreAuthorize("hasAuthority('checkcommit_edit')" )
    public R updateById(@RequestBody CheckCommit checkCommit) {
        return R.ok(checkCommitService.updateById(checkCommit));
    }


    /**
     * 通过id删除自查报告提交内容
     *
     * @param checkCommitId
     * @return R
     */
    @Deprecated
    @ApiOperation(value = "通过id删除自查报告提交内容", notes = "通过id删除自查报告提交内容")
//    @Log("通过id删除自查报告提交内容" )
    @DeleteMapping("/{checkCommitId}")
//    @PreAuthorize("hasAuthority('checkcommit_del')" )
    public R removeById(@PathVariable Integer checkCommitId) {
        return R.ok(checkCommitService.removeById(checkCommitId));
    }

}
