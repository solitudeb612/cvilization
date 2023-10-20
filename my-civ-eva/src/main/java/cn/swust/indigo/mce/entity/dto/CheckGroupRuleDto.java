package cn.swust.indigo.mce.entity.dto;

import cn.swust.indigo.mce.entity.po.CheckGroup;
import cn.swust.indigo.mce.entity.po.EvaluationRules;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckGroupRuleDto {
    private CheckGroup checkGroup;
    private List<EvaluationRules> evaluationRulesList;
}
