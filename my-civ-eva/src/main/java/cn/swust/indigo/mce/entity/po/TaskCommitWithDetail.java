package cn.swust.indigo.mce.entity.po;

import lombok.Data;

/**
 * @author Binary Tree Cat
 */
@Data
public class TaskCommitWithDetail extends TaskCommit {

    private Integer detailID;

    private CommitDetail commitDetail;
}
