package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.mce.entity.po.CommitImgStar;
import cn.swust.indigo.mce.mapper.CommitImgStarMapper;
import cn.swust.indigo.mce.service.ICommitImgStarService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 申报内容详情 主要是图片  服务实现类
 * </p>
 *
 * @author binaryCat
 * @since 2023-04-01
 */
@Service
public class CommitImgStarServiceImpl extends ServiceImpl<CommitImgStarMapper, CommitImgStar> implements ICommitImgStarService {

    @Override
    public IPage<CommitImgStar> imagesList(Integer guideId, Integer ruleId, Integer reportId, Integer commitDetailId, Integer isStar, IPage page) {
        LambdaQueryWrapper<CommitImgStar> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(guideId), CommitImgStar::getGuideId, guideId);
        queryWrapper.eq(ObjectUtil.isNotNull(ruleId), CommitImgStar::getRuleId, ruleId);
        queryWrapper.eq(ObjectUtil.isNotNull(reportId), CommitImgStar::getReportId, reportId);
        queryWrapper.eq(ObjectUtil.isNotNull(commitDetailId), CommitImgStar::getCommitDetailId, commitDetailId);
        queryWrapper.eq(ObjectUtil.isNotNull(isStar), CommitImgStar::getStar, isStar);
        return this.page(page,queryWrapper);
    }
}
