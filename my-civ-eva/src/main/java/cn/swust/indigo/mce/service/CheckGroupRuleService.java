package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;

import java.util.List;

/**
 * 自查组部门规则设置
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
public interface CheckGroupRuleService extends IService<CheckGroupRule> {
    List<CheckGroupCriteria> getRuleDetails(int groupRuleId);

    Boolean updateById(CheckGroupCriteria checkGroupCriteria);
}
