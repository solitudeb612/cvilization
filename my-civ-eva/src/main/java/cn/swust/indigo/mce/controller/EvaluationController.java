package cn.swust.indigo.mce.controller;

import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.mce.entity.dto.RuleTreeCheckNode;
import cn.swust.indigo.mce.entity.po.Evaluation;
import cn.swust.indigo.mce.entity.vo.QEvaluationTableInfo;
import cn.swust.indigo.mce.service.EvaluationService;
import cn.swust.indigo.mce.entity.dto.RuleTreeNode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.mce.entity.po.EvaluationRules;
import cn.swust.indigo.mce.service.EvaluationRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 评判规则
 *
 * @author lhz
 * @date 2023-02-24 16:57:29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/evaluation")
@Api(value = "evaluation-rules", tags = "网上申报评测体系及规则管理")
public class EvaluationController {

    private final EvaluationRulesService evaluationRulesService;
    private final EvaluationService evaluationService;


    @ApiOperation(value = "获取所有网上申报评测体系", notes = "获取所有网上申报评测体系")
    @GetMapping("/all")
    public R getAllEvaluation() {
        return R.ok(evaluationService.listUsed());
    }

    @ApiOperation(value = "分页网上申报评测体系", notes = "分页所有评测体系")
    @GetMapping("/page")
    public R getEvaluation(Page<Evaluation> page) {
        return R.ok(evaluationService.page(page, null));
    }

    @ApiOperation(value = "更新网上申报评测体系名", notes = "更新网上申报评测体系名")
    @PutMapping()
    public R updateEvaluationName(@Valid @RequestBody Evaluation evaluation) {
        // TODO zhangjia 测试功能是否正确 只修改了evaluation没有改evaluation rule  能修改。
        return R.ok(evaluationService.updateById(evaluation));
    }
    //todo: 评价体系级联删除,使用过不能删除，子规则不能新增，删除

    @ApiOperation(value = "删除评价体系",notes = "删除评价体系")
    @DeleteMapping
    public R deleteByEvaluationId(@RequestParam("rootRule")Integer rootRuleId){
        boolean evaluationId = evaluationService.deleteByEvaluationId(rootRuleId);
        if(!evaluationId){
            return R.failed(false);
        }
        return R.ok(true);
    }

    /**
     *
     * @param page
     * @param rootRuleId
     * @return
     */
    //todo: 删除level
    @ApiOperation(value = "分页查询网上申报规则-4级结构", notes = "分页查询网上申报规则-4级结构")
    @GetMapping("/rule/table")
    @Cacheable(value = CacheConstants.RULES_DETAILS)
    public R getEvaluationRulesPage(Page<QEvaluationTableInfo> page, @RequestParam("rootRuleId") Integer rootRuleId) {
        return R.ok(evaluationRulesService.pageTable(page, rootRuleId));
    }

    /**
     * 通过id查询评判规则
     * @param rootId id
     * @return R
     */
    @ApiOperation(value = "通过根id查询评判规则树", notes = "通过根id查询评判规则树")
    @GetMapping("/rule/tree")
    public R<RuleTreeNode> treeByRootId(@RequestParam("rootId") Integer rootId) {
        RuleTreeNode tree = getTreeByRootId(rootId);
        if (tree != null) {
            return R.ok(tree);
        }
        return R.failed("未找到对于的规则树");
    }

    private RuleTreeNode getTreeByRootId(Integer rootId) {
        RuleTreeNode ruleTreeNode = evaluationRulesService.tree(rootId);
        return ruleTreeNode;
    }

    @ApiOperation(value = "通过id查询网上申报评测规则", notes = "通过id查询网上申报评测规则")
    @GetMapping("/rule/get")
    public R<EvaluationRules> getByRuleId(@RequestParam("ruleId") Integer ruleId) {
        return R.ok(evaluationRulesService.getById(ruleId));
    }

    @ApiOperation(value = "拷贝根节点树", notes = "拷贝根节点树--拷贝rule  rule-dept")
    @GetMapping("/rule/tree/cp")
    public R<Integer> copyTree(@RequestParam("rootId") Integer rootId, @RequestParam("ruleName") String ruleName) {
        RuleTreeNode tree = getTreeByRootId(rootId);

        if (tree != null) {
            evaluationRulesService.cloneTree2(tree, ruleName, rootId, true);
//            evaluationRulesService.cloneTree(tree, ruleName, rootId);
            return R.ok(tree.getRuleId());
        }
        return R.failed("未找到对于的规则树");
    }

    @ApiOperation(value = "选择拷贝根节点树", notes = "选择拷贝根节点树--拷贝rule  rule-dept")
    @PostMapping("/rule/tree/cp/choose")
    public R<Integer> copyChooseTree(@RequestBody RuleTreeNode tree) {
        String ruleName = tree.getRuleName();
        Integer rootId = tree.getRootRuleId();
        if (tree != null) {
            evaluationRulesService.cloneTree2(tree, ruleName, rootId, false);
//            evaluationRulesService.cloneTree(tree, ruleName, rootId);
            return R.ok(tree.getRuleId());
        }
        return R.failed("未找到对于的规则树");
    }

    /**
     * 新增评判规则
     * @param evaluationRules 评判规则
     * @return R
     */
    @ApiOperation(value = "新增网上申报评测规则", notes = "新增网上申报评测规则")
    @Log("新增网上申报评测规则")
    @PostMapping("/rule")
    @CacheEvict(value = CacheConstants.RULES_DETAILS, allEntries = true)
//    @PreAuthorize("hasAuthority('evaluationrules_add')" )
    public R<Object> save(@RequestBody EvaluationRules evaluationRules) {
        boolean save = evaluationRulesService.saveEvaluationRules(evaluationRules);
        if (save) {
            return R.ok(evaluationRules.getRuleId());
        }
        return R.failed(false);
    }

    /**
     * 修改评判规则
     * @param evaluationRules 评判规则
     * @return R
     */
    @ApiOperation(value = "修改网上申报评测规则", notes = "修改网上申报评测规则")
    @Log("修改网上申报评测规则")
    @PutMapping("/rule")
    @CacheEvict(value = CacheConstants.RULES_DETAILS, allEntries = true)
//    @PreAuthorize("hasAuthority('evaluationrules_edit')")
    public R<Boolean> updateById(@RequestBody EvaluationRules evaluationRules) {
        boolean update =evaluationRulesService.updateByEvaluationId(evaluationRules);
        if(update){
            return R.ok();
        }else{
            return R.failed("该规则已经被使用或该节点为根节点！");
        }
    }


    /**
     * 通过id删除评判规则
     * @param ruleId
     * @return R
     */
    @ApiOperation(value = "通过id删除网上申报评测规则", notes = "通过id删除网上申报评测规则")
    @Log("通过id删除网上申报评测规则")
    @DeleteMapping("/rule/delete")
    @CacheEvict(value = CacheConstants.RULES_DETAILS, allEntries = true)
//    @PreAuthorize("hasAuthority('evaluationrules_del')" )
    public R<Boolean> removeById(@RequestParam("ruleId") Integer ruleId) {
        return R.ok(evaluationRulesService.deleteRuleTreeById(ruleId));
    }

    /**
     * 通过id查询父节点
     * @param ruleId
     * @return R
     */
    @ApiOperation(value = "通过id查询网上申报评测规则父节点", notes = "通过id查询网上申报评测规则父节点")
    @Log("通过id查询网上申报评测规则父节点")
    @GetMapping("/rule/parents")
//    @PreAuthorize("hasAuthority('evaluationrules_del')" )
    public R getParentsNode(@RequestParam("ruleId") Integer ruleId) {
        return R.ok(evaluationRulesService.getParentsNode(ruleId));
    }
}
