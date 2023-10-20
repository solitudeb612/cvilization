package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.Evaluation;
import cn.swust.indigo.mce.entity.vo.EvaluationUseCountVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface EvaluationService extends IService<Evaluation> {
    boolean deleteByEvaluationId(Integer rootRuleId);

    List<EvaluationUseCountVo> listUsed();
}
