package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.CheckCommit;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;

import cn.swust.indigo.mce.entity.po.CheckProblem;
import lombok.Data;

import java.util.List;

@Data
public class CheckCommitAllInfo {
    private CheckGroupRule checkGroupRule;
    private CheckCommit checkCommit;
    private boolean commitFlag = false;

    private CheckProblem checkProblem;

    public CheckCommitAllInfo(CheckGroupRule checkGroupRule, CheckCommit checkCommit, boolean commitFlag) {
        this.checkGroupRule = checkGroupRule;
        this.checkCommit = checkCommit;
        this.commitFlag = commitFlag;
    }

    public CheckCommitAllInfo(CheckGroupRule checkGroupRule, CheckCommit checkCommit, boolean commitFlag, CheckProblem checkProblem) {
        this.checkGroupRule = checkGroupRule;
        this.checkCommit = checkCommit;
        this.commitFlag = commitFlag;
        this.checkProblem = checkProblem;
    }
}
