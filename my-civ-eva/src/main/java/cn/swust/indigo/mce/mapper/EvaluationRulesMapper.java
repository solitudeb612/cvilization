package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.EvaluationRules;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfo;
import cn.swust.indigo.mce.entity.vo.QEvaluationTableInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评判规则 Mapper 接口
 *
 * @author lhz
 * @date 2023-02-24 16:57:29
 */
@Mapper
public interface EvaluationRulesMapper extends BaseMapper<EvaluationRules> {
    List<CommitRuleTableInfo> getAllRuleInfo(@Param("list") List<Integer> ruleList);

    List<QEvaluationTableInfo> listAllInfoRules(@Param("list") List<Integer> ruleList, @Param("rootRuleId") Integer rootRuleId);
    Page<QEvaluationTableInfo> tableAllRuleInfo(IPage<QEvaluationTableInfo> page, @Param("rootRuleId") Integer rootRuleId);
}
