package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.swust.indigo.admin.entity.vo.TreeUtil;
import cn.swust.indigo.mce.entity.dto.RuleTreeCheckNode;
import cn.swust.indigo.mce.entity.dto.RuleTreeNode;
import cn.swust.indigo.mce.entity.po.*;
import cn.swust.indigo.mce.entity.vo.QEvaluationTableInfo;
import cn.swust.indigo.mce.mapper.EvaluationMapper;
import cn.swust.indigo.mce.mapper.EvaluationRulesMapper;
import cn.swust.indigo.mce.mapper.GuideMapper;
import cn.swust.indigo.mce.service.EvaluationRulesService;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import cn.swust.indigo.mce.service.TaskCommitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评判规则
 *
 * @author lhz
 * @date 2023-02-24 16:57:29
 */
@Service
@AllArgsConstructor
public class EvaluationRulesServiceImpl extends ServiceImpl<EvaluationRulesMapper, EvaluationRules> implements EvaluationRulesService {

    @Autowired
    private EvaluationMapper evaluationMapper;
    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private RuleDeptTaskService ruleDeptTaskService;
    @Autowired
    private TaskCommitService taskCommitService;

    @Override
    @Transactional
    public Boolean deleteRuleTreeById(Integer ruleId) {
        Evaluation evaluation = evaluationMapper.selectById(ruleId);
        List<Integer> list = new ArrayList<>();
        selectAllTreeNodeIds(list, ruleId);
        if(evaluation==null){
            int count = baseMapper.deleteBatchIds(list);
            return count>0?Boolean.TRUE : Boolean.FALSE;
        }
        boolean usedByGuide = isUsedByGuide(ruleId);
        if(usedByGuide){
            return Boolean.FALSE;
        }
        int count = baseMapper.deleteBatchIds(list);
        int deleteRuleRootId = evaluationMapper.deleteById(ruleId);
        return (count > 0&&deleteRuleRootId>0) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<EvaluationRules> getParentsNode(Integer ruleId) {
        List<EvaluationRules> list = new ArrayList<>();
        getParentNode(list, ruleId);
        return list;
    }

    @Override
    public boolean cpTree(RuleTreeNode tree, String ruleName, Integer rootId, List<RuleDeptTask> ruleDeptTaskList) {
        tree.setRuleName(ruleName);
        EvaluationRules root = cpNode(tree, 0, rootId, ruleDeptTaskList);
        for (Object node : tree.getChildren()) {
            RuleTreeNode ruleTreeNode = (RuleTreeNode) node;
            ruleTreeNode.setParentId(root.getRuleId());
            cpTree(ruleTreeNode, ruleTreeNode.getRuleName(), rootId, ruleDeptTaskList);
        }
        return false;
    }

    public int cpTree2(RuleTreeNode tree, String ruleName, Integer rootId,
            List<RuleDeptTask> ruleDeptTaskList,Integer id, List<EvaluationRules> newRuleTreeList) {
        tree.setRuleName(ruleName);
        EvaluationRules newRuleNode = cpNode2(tree, 0, rootId, ruleDeptTaskList,  id, newRuleTreeList);
        id++;
        for (Object node : tree.getChildren()) {
            RuleTreeNode ruleTreeNode = (RuleTreeNode) node;
            ruleTreeNode.setParentId(newRuleNode.getRuleId());
            if(ruleTreeNode.isChecked()){
                id = cpTree2(ruleTreeNode, ruleTreeNode.getRuleName(), rootId, ruleDeptTaskList, id, newRuleTreeList);
            }
        }
        return id;
    }

    @Override
    @Transactional
    public boolean cloneTree(RuleTreeNode tree, String ruleName, Integer srcRootId) {
        //获取所有 rule——dept——task
        List<RuleDeptTask> ruleDeptTaskList = ruleDeptTaskService.list(new QueryWrapper<RuleDeptTask>().eq("root_rule_id", srcRootId));
        tree.setRuleName(ruleName);
        EvaluationRules root = cpNode(tree, 0, 0, ruleDeptTaskList);
        root.setRootRuleId(root.getRuleId());
        root.setLevel(0);
        Evaluation evaluation = new Evaluation(root.getRootRuleId(), root.getRuleName());
        evaluationMapper.insert(evaluation);
        this.updateById(root);
        for (Object node : tree.getChildren()) {
            RuleTreeNode ruleTreeNode = (RuleTreeNode) node;
            ruleTreeNode.setParentId(root.getRuleId());
            cpTree(ruleTreeNode, ruleTreeNode.getRuleName(), root.getRuleId(), ruleDeptTaskList);
        }
        List<Integer> oldTaskId = new ArrayList<>();
        for (RuleDeptTask task : ruleDeptTaskList) {
            task.setRootRuleId(root.getRuleId());
            oldTaskId.add(task.getTaskId());
            task.setTaskId(null);
        }
        ruleDeptTaskService.saveBatch(ruleDeptTaskList);
        HashMap<Integer, Integer> taskIdMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < oldTaskId.size(); i++) {
            taskIdMap.put(oldTaskId.get(i), ruleDeptTaskList.get(i).getRuleId());
        }
        List<TaskCommit> taskCommitList = taskCommitService.list(new QueryWrapper<TaskCommit>().in("task_id", oldTaskId));

        for (TaskCommit taskCommit : taskCommitList) {
            Integer oldId = taskCommit.getTaskId();
            taskCommit.setTaskId(taskIdMap.get(oldId));
            taskCommit.setTaskCommitId(null);
        }
        taskCommitService.saveBatch(taskCommitList);
        return true;
    }

    @Override
    @Transactional
    public boolean cloneTree2(RuleTreeNode tree, String ruleName, Integer srcRootId, boolean isAll) {
        //获取所有 rule——dept——task
        List<RuleDeptTask> ruleDeptTaskList = ruleDeptTaskService.list(new QueryWrapper<RuleDeptTask>().eq("root_rule_id", srcRootId));
        tree.setRuleName(ruleName);
        EvaluationRules root = cpNode(tree, 0, 0, ruleDeptTaskList);
        root.setRootRuleId(root.getRuleId());
        root.setLevel(0);
        Evaluation evaluation = new Evaluation(root.getRootRuleId(), root.getRuleName());
        evaluationMapper.insert(evaluation);
        this.updateById(root);
        Integer id = root.getRuleId();
        id++;
        List<EvaluationRules> newRuleList = new ArrayList<>();
        for (Object node : tree.getChildren()) {
            RuleTreeNode ruleTreeNode = (RuleTreeNode) node;
            ruleTreeNode.setParentId(root.getRuleId());
            if(isAll){
                id = cpTree2(ruleTreeNode, ruleTreeNode.getRuleName(), root.getRuleId(), ruleDeptTaskList, id, newRuleList);
            }else if(ruleTreeNode.isChecked()){
                id = cpTree2(ruleTreeNode, ruleTreeNode.getRuleName(), root.getRuleId(), ruleDeptTaskList, id, newRuleList);
            }
        }
        if (newRuleList.size()>0) {
            this.saveBatch(newRuleList);
        }
        List<Integer> oldTaskId = new ArrayList<>();
        for (RuleDeptTask task : ruleDeptTaskList) {
            task.setRootRuleId(root.getRuleId());
            oldTaskId.add(task.getTaskId());
            task.setTaskId(null);
        }
        if (ruleDeptTaskList.size()>0) {
            ruleDeptTaskService.saveBatch(ruleDeptTaskList);
        }
        HashMap<Integer, Integer> taskIdMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < oldTaskId.size(); i++) {
            taskIdMap.put(oldTaskId.get(i), ruleDeptTaskList.get(i).getRuleId());
        }
        List<TaskCommit> taskCommitList = taskCommitService.list(new QueryWrapper<TaskCommit>().in("task_id", oldTaskId));

        for (TaskCommit taskCommit : taskCommitList) {
            Integer oldId = taskCommit.getTaskId();
            taskCommit.setTaskId(taskIdMap.get(oldId));
            taskCommit.setTaskCommitId(null);
        }
        if (taskCommitList.size()>0)
        taskCommitService.saveBatch(taskCommitList);
        return true;
    }

    @Override
    public IPage<QEvaluationTableInfo> pageTable(Page<QEvaluationTableInfo> page, Integer rootRuleId) {
        return baseMapper.tableAllRuleInfo(page, rootRuleId);
    }

    @Override
    public boolean saveEvaluationRules(EvaluationRules evaluationRules) {
        Integer parentId = evaluationRules.getParentId();
        if(parentId==null){
            return false;
        }
        EvaluationRules one = this.getOne(new LambdaQueryWrapper<EvaluationRules>().eq(EvaluationRules::getRuleId, parentId));
        if(one==null){
            return false;
        }
        evaluationRules.setLevel(one.getLevel()+1);
        Integer rootRuleId = evaluationRules.getRootRuleId();
        List<Guide> guideList = guideMapper.selectList(new LambdaQueryWrapper<Guide>().eq(Guide::getRootRuleId, rootRuleId));
        if(!guideList.isEmpty()){
            return false;
        }
        boolean save = this.save(evaluationRules);
        return save;
    }

    @Override
    public boolean updateByEvaluationId(EvaluationRules evaluationRules) {
        Integer rootRuleId = evaluationRules.getRootRuleId();
        List<Guide> guideList = guideMapper.selectList(new LambdaQueryWrapper<Guide>().eq(Guide::getRootRuleId, rootRuleId));
        if(!guideList.isEmpty() || evaluationRules.getParentId() == 0){
            return false;
        }
///
//        if (evaluationRules.getParentId().equals(0)){
//            Evaluation evaluation = new Evaluation();
//            evaluation.setRootRuleId(evaluationRules.getRuleId());
//            evaluation.setRuleName(evaluationRules.getRuleName());
//            evaluationMapper.updateById(evaluation);
//        }

        boolean result = this.updateById(evaluationRules);
        return result;
    }

    public EvaluationRules cpNode(RuleTreeNode treeNode, Integer parentId, Integer rootId, List<RuleDeptTask> ruleDeptTaskList) {
        EvaluationRules evaluationRules = new EvaluationRules();
        BeanUtil.copyProperties(treeNode, evaluationRules);
        Integer oldRuleId = treeNode.getRuleId();
        evaluationRules.setRuleId(null);
        evaluationRules.setRootRuleId(rootId);
        this.save(evaluationRules);
        Integer newRuleId = evaluationRules.getRuleId();
        for (RuleDeptTask task : ruleDeptTaskList) {
            if (task.getRuleId().equals(oldRuleId)) {
                task.setRuleId(newRuleId);
            }
        }
        return evaluationRules;
    }

    public EvaluationRules cpNode2(RuleTreeNode treeNode, Integer parentId,
            Integer rootId, List<RuleDeptTask> ruleDeptTaskList, int id,
     List<EvaluationRules> newRuleTreeList) {
        EvaluationRules evaluationRules = new EvaluationRules();
        BeanUtil.copyProperties(treeNode, evaluationRules);
        Integer oldRuleId = treeNode.getRuleId();
        evaluationRules.setRootRuleId(rootId);
        evaluationRules.setRuleId(id);
        Integer newRuleId = evaluationRules.getRuleId();
        for (RuleDeptTask task : ruleDeptTaskList) {
            if (task.getRuleId().equals(oldRuleId)) {
                task.setRuleId(newRuleId);
            }
        }
        newRuleTreeList.add(evaluationRules);
        return evaluationRules;
    }

    public void getParentNode(List<EvaluationRules> list, Integer ruleId) {
        EvaluationRules evaluationRules = baseMapper.selectById(ruleId);
        if (evaluationRules == null) {
            return;
        }
        Integer parentId = evaluationRules.getParentId();
        if (parentId == 0) {
            return;
        }
        EvaluationRules parentNode = baseMapper.selectById(parentId);
        list.add(parentNode);
        getParentNode(list, parentNode.getRuleId());
    }

    public void selectAllTreeNodeIds(List<Integer> ids, Integer id) {
        ids.add(id);
        EvaluationRules parent = baseMapper.selectById(id);
        if (parent == null) {
            return;
        }
        QueryWrapper<EvaluationRules> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parent.getRuleId());
        List<EvaluationRules> list = baseMapper.selectList(queryWrapper);
        if (list == null || list.size() == 0) {
            return;
        }
        for (EvaluationRules evaluationRules : list) {
            selectAllTreeNodeIds(ids, evaluationRules.getRuleId());
        }
    }

    @Override
    public List<RuleTreeNode> tree() {
        List<RuleTreeNode> ruleTreeNodes = getRuleTree(this.list(null), 0);
        return ruleTreeNodes;
    }


    @Override
    public RuleTreeNode tree(int rootId) {
        List<RuleTreeNode> ruleTreeNodes = getRuleTree(this.list(new QueryWrapper<EvaluationRules>().eq("root_rule_id", rootId)), 0);
        if (ruleTreeNodes != null && ruleTreeNodes.size() > 0) {
            return ruleTreeNodes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateById(EvaluationRules entity) {
        Integer rootRuleId = entity.getRootRuleId();
        boolean usedByGuide = isUsedByGuide(rootRuleId);
        if(usedByGuide){
            return false;
        }
        return super.updateById(entity);
    }

    public boolean isUsedByGuide(Integer rootRuleId) {
        LambdaQueryWrapper<Guide> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Guide::getRootRuleId, rootRuleId);
        Guide guide = guideMapper.selectOne(queryWrapper);
        if (BeanUtil.isNotEmpty(guide)) {
            return true;
        }
        return false;
    }

    private List<RuleTreeNode> getRuleTree(List<EvaluationRules> rules, Integer rootId) {
        List<RuleTreeNode> treeList = rules.stream()
                .filter(rule -> !rule.getRuleId().equals(rule.getParentId()))
                .sorted(Comparator.comparingInt(EvaluationRules::getSort))
                .map(rule -> {
                    RuleTreeNode node = new RuleTreeNode();
                    node.setRuleId(rule.getRuleId());
                    node.setId(rule.getRuleId());
                    node.setRootRuleId(rule.getRootRuleId());
                    node.setParentId(rule.getParentId());
                    node.setRuleName(rule.getRuleName());
                    node.setLevel(rule.getLevel());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(treeList, rootId);
    }
}
