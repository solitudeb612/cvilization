package cn.swust.indigo.mce.service;

import cn.swust.indigo.admin.entity.dto.TreeNode;
import cn.swust.indigo.mce.entity.vo.QGuide;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.Guide;
import org.apache.ibatis.annotations.Update;

/**
 * 指南
 *
 * @author lhz
 * @date 2023-02-24 16:57:45
 */
public interface GuideService extends IService<Guide> {
    boolean updateState(Integer guideId, Integer status);

    /**
     * 检查依据 ROOT RULE ID 的 GUIDE 数量是否大于2
     * @param rootRuleId
     * @return true 大于 2
     */
    boolean checkUseGuideCountGeTwo(Integer rootRuleId);
}
