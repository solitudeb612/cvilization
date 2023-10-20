package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.mce.mapper.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

/**
 * 指南
 *
 * @author lhz
 * @date 2023-02-24 16:57:45
 */
@Service @AllArgsConstructor public class GuideServiceImpl extends ServiceImpl<GuideMapper, Guide>
        implements GuideService {

    @Autowired
    GuideMapper guideMapper;

    @Override
    public boolean updateState(Integer guideId, Integer status) {
        return guideMapper.updateStatus(guideId, status);
    }

    @Override
    public boolean checkUseGuideCountGeTwo(Integer rootRuleId) {
        LambdaQueryWrapper<Guide> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Guide::getRootRuleId, rootRuleId).ge(Guide::getStatus, 1);
        Integer num = guideMapper.selectCount(wrapper);
        return num >= 2 ;
    }
}
