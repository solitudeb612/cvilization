package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.po.Evaluation;
import cn.swust.indigo.mce.entity.po.EvaluationRules;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.vo.EvaluationUseCountVo;
import cn.swust.indigo.mce.mapper.EvaluationMapper;
import cn.swust.indigo.mce.mapper.EvaluationRulesMapper;
import cn.swust.indigo.mce.mapper.GuideMapper;
import cn.swust.indigo.mce.service.EvaluationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation>implements EvaluationService {
    @Autowired
    private EvaluationRulesMapper evaluationRulesMapper;

    @Autowired
    private GuideMapper guideMapper;
    @Override
    @Transactional
    public boolean updateById(Evaluation entity) {
        // 修改evaluation_rules表
        EvaluationRules evaluationRules = new EvaluationRules();
        evaluationRules.setRuleId(entity.getRootRuleId()); // 设置主键ID
        evaluationRules.setRuleName(entity.getRuleName());
        evaluationRulesMapper.updateById(evaluationRules);

        // 修改evaluation表
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean deleteByEvaluationId(Integer rootRuleId) {
        List<Guide> guideList = guideMapper.selectList(new LambdaQueryWrapper<Guide>().eq(Guide::getRootRuleId, rootRuleId));
        if(!guideList.isEmpty()){
            return false;
        }
        boolean removeById = this.removeById(rootRuleId);
        if (!removeById){
            return false;
        }
        int delete = evaluationRulesMapper.delete(new LambdaQueryWrapper<EvaluationRules>()
                .eq(EvaluationRules::getRootRuleId, rootRuleId));

        return true;
    }

    @Override
    public List<EvaluationUseCountVo> listUsed() {

        return this.baseMapper.listUsed();
    }
}
