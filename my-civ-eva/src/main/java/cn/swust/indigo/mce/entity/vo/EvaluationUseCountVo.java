package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.Evaluation;
import lombok.Data;

@Data
public class EvaluationUseCountVo extends Evaluation {
    Integer usedCount = 0;
    boolean used;

    public Integer getUseCount() {
        if (usedCount == null) {
            usedCount = 0;
        }
        return usedCount;
    }

    public boolean isUsed() {
        if (usedCount == null) {
            usedCount = 0;
        }
        return usedCount > 0 ? true:false;
    }
}
