package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.po.BatchSaveTaskCommit;
import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.TaskCommit;
import cn.swust.indigo.mce.mapper.TaskCommitMapper;
import cn.swust.indigo.mce.service.TaskCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线申报提交要求
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
@Service
@AllArgsConstructor
public class TaskCommitServiceImpl extends ServiceImpl<TaskCommitMapper, TaskCommit> implements TaskCommitService {
    @Autowired
    private RuleDeptTaskService ruleDeptTaskService;

    private TaskCommitMapper taskCommitMapper;

    @Override
    public boolean rootRuleBatchSave(BatchSaveTaskCommit taskCommit) {
        List<RuleDeptTask> ruleDeptTaskList = ruleDeptTaskService.list(new QueryWrapper<RuleDeptTask>().eq("root_rule_id", taskCommit.getRootRuleId()));
        List<TaskCommit> batchSaveList = new ArrayList<>();
        for (RuleDeptTask task : ruleDeptTaskList) {
            TaskCommit commit = new TaskCommit();
            commit.setTaskId(task.getTaskId());
            commit.setCommitName(taskCommit.getCommitName());
            commit.setMemo(taskCommit.getMemo());
            commit.setType(taskCommit.getType());
            commit.setIsRequired(taskCommit.getIsRequired());
            batchSaveList.add(commit);
        }
        return this.saveBatch(batchSaveList);
    }

    @Override
    public IPage<TaskCommit> page(IPage<TaskCommit> page, Wrapper<TaskCommit> queryWrapper) {
        if(taskCommitMapper.selectList(queryWrapper).size() == 0){
            return null;
        }
        return super.page(page, queryWrapper);
    }


}
