package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自查组部门规则设置 Mapper 接口
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
@Mapper
public interface CheckGroupRuleMapper extends BaseMapper<CheckGroupRule> {
    List<CheckGroupRule> checkDeptRule(Integer checkId, Integer DeptId);


    List<CheckGroupCriteria> getRuleDetails(@Param("group_rule_id")int groupRuleId);

    Boolean updateById(@Param("group_rule_id") CheckGroupCriteria checkGroupCriteria);
}
