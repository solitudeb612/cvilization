package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.CheckProblem;
import cn.swust.indigo.mce.entity.vo.RepairInfo;
import cn.swust.indigo.mce.service.CheckProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (CheckProblem)表控制层
 *
 * @author makejava
 * @since 2023-03-25 16:03:22
 */
@RestController
@RequestMapping("problem")
@Api(value = "problem", tags = "实地考察-检查提出问题管理")
public class CheckProblemController {

    @Autowired
    private CheckProblemService checkProblemService;

    @ApiOperation(value = "问题的分页查询", notes = "分页的参数包括内容：问题名称，问题发生地址，问题类型，当前页码，页数大小")
    @GetMapping("/page")
    public R getCheckProblemPage(@RequestParam(required = false) String name, @RequestParam(required = false) String address,@RequestParam(required = false) Integer type, @RequestParam Integer index, @RequestParam Integer size) {
        IPage<CheckProblem> checkProblemPage = checkProblemService.getCheckProblemPage(new Page<CheckProblem>(index, size), address, name, type);
        return R.ok(checkProblemPage);
    }

    @ApiOperation(value = "通过ID获取检查提出的问题")
    @GetMapping("/get")
    public R getCheckProblem(@RequestParam Integer checkProblemId) {
//        CheckProblem byId = checkProblemService.getById(checkProblemId);
        CheckProblem byId = checkProblemService.allInfo(checkProblemId);
        if (ObjectUtil.isNotNull(byId)) {
            return R.ok(byId);
        } else {
            return R.failed("此问题不存在");
        }
    }

    @ApiOperation(value = "新增一个问题")
    @PostMapping
    public R postCheckProblem(@RequestBody CheckProblem checkProblem) {
        return checkProblemService.save(checkProblem)? R.ok(checkProblem.getCheckProblemId()) : R.failed("操作失败");
    }

    @ApiOperation(value = "根据ID删除一个问题")
    @DeleteMapping("/delete")
    public R deleteCheckProblem(@RequestParam Integer checkProblemId) {
        return R.ok(checkProblemService.removeById(checkProblemId));
    }

    @ApiOperation(value = "更新一个问题")
    @PutMapping
    public R updateCheckProblem(@RequestBody CheckProblem checkProblem) {
        return R.ok(checkProblemService.updateById(checkProblem));
    }

    @ApiOperation("维修信息填写")
    @PostMapping("/repair")
    @Deprecated
    public R repairProblemsByList(@RequestBody List<CheckProblem> checkProblems) {
        checkProblemService.repairCheckProblemsByList(checkProblems);
        return R.ok();
    }

    @ApiOperation("维修信息填写")
    @PostMapping("/repair/problems")
    public R repairProblems(@RequestBody List<RepairInfo> repairInfos) {
        return checkProblemService.addRepairInfo(repairInfos) ?
                R.ok("更新信息成功") :
                R.failed("更新失败！可能是数据不正确或者后端错误！请稍等！");
    }
}

