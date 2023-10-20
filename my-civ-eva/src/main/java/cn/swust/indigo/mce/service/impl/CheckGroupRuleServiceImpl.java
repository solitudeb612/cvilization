package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;
import cn.swust.indigo.mce.mapper.CheckGroupRuleMapper;
import cn.swust.indigo.mce.service.CheckGroupRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 自查组部门规则设置
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
@Service
@AllArgsConstructor
public class CheckGroupRuleServiceImpl extends ServiceImpl<CheckGroupRuleMapper, CheckGroupRule> implements CheckGroupRuleService {

    @Autowired
    private CheckGroupRuleMapper checkGroupRuleMapper;


    @Override
    public List<CheckGroupCriteria> getRuleDetails(int groupRuleId) {
        return checkGroupRuleMapper.getRuleDetails(groupRuleId);
    }

    @Override
    public Boolean updateById(CheckGroupCriteria checkGroupCriteria) {
        return checkGroupRuleMapper.updateById(checkGroupCriteria);
    }
}
