package cn.swust.indigo.mce.service;

import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.BatchSaveTaskCommit;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.TaskCommit;

/**
 * 在线申报提交要求
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
public interface TaskCommitService extends IService<TaskCommit> {
    boolean rootRuleBatchSave(BatchSaveTaskCommit taskCommit);
}
