package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.dto.RuleTreeCheckNode;
import cn.swust.indigo.mce.entity.dto.RuleTreeNode;
import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.entity.vo.QEvaluationTableInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.EvaluationRules;

import java.util.List;

/**
 * 评判规则
 *
 * @author lhz
 * @date 2023-02-24 16:57:29
 */
public interface EvaluationRulesService extends IService<EvaluationRules> {
    List<RuleTreeNode> tree();
    RuleTreeNode tree(int rootId);
    Boolean deleteRuleTreeById(Integer ruleId);

    List<EvaluationRules> getParentsNode(Integer ruleId);
    boolean cpTree(RuleTreeNode tree, String ruleName, Integer rootId,  List<RuleDeptTask> ruleDeptTaskList);

    boolean cloneTree(RuleTreeNode tree, String ruleName, Integer srcRootId);
    boolean cloneTree2(RuleTreeNode tree, String ruleName, Integer srcRootId, boolean isAll);

    IPage<QEvaluationTableInfo> pageTable(Page<QEvaluationTableInfo> page, Integer rootRuleId);

    boolean saveEvaluationRules(EvaluationRules evaluationRules);

    boolean updateByEvaluationId(EvaluationRules evaluationRules);
}
